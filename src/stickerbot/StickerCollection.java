package stickerbot;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import telegrambot.network.InvalidUrlQueryException;
import telegrambot.network.NetworkError;
import telegrambot.network.TelegramMethod;
import telegrambot.tgcore.BotCfg;
import telegrambot.tgcore.Sticker;
import telegrambot.tgcore.Update;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

// can't use file_id
public class StickerCollection extends StickerCollectionAbs {

    private final class CSticker {

        private final String png_fpath;
        private final Sticker sticker;

        private CSticker(Sticker s, String png_fpath) {
            this.sticker = s;
            this.png_fpath = png_fpath;
        }

        @Override public String toString() {
            return ReflectionToStringBuilder.toString(this);
        }
    }

    private final ExecutorService exec = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    private final List<CSticker> stickers = Collections.synchronizedList(new ArrayList<>());
    private final Path tmp_directory;

    public StickerCollection(Rid rid, String sticker_set_name, boolean is_new) throws NetworkError {

        super(rid, sticker_set_name, is_new);

        try {
            this.tmp_directory = Files.createTempDirectory("stickers_", new FileAttribute[]{});
            System.out.println("MY TEMP DIR: " + tmp_directory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void fetchSticker(Update u) throws NetworkError {

        exec.submit(
            ()-> {

                String file_id;
                File file = null;

                if (!rid.equals(u))
                    return null;  // Callable<Void>

                try { file_id = "" + u.message.sticker.file_id; }
                catch (NullPointerException e) { return null; }

                System.out.println(getClass().getName() + " " + u.message.sticker);

                try {

                    telegrambot.tgcore.File tgf = TelegramMethod.create()
                            .baseUrl(BotCfg.bot_url)
                            .method("getFile")
                            .field("file_id", file_id)
                            .commonObjectBuilder(telegrambot.tgcore.File::new);

                    assert(tgf != null);

                    // TODO delete tmp_directory
                    file = File.createTempFile("sticker_", ".webp", tmp_directory.toFile());
                    System.out.println("DBG: " + file.toPath());

                    // download .webp file
                    TelegramMethod.create()
                            .baseUrl(BotCfg.bot_url_files + "/" + tgf.file_path)
                            .get(file, true); // overwrite the previous tmp empty file

                    // convert .webp to png and delete .webp
                    stickers.add(new CSticker(u.message.sticker, BotUtils.webp2png(file.getAbsolutePath())));

                } catch (InvalidUrlQueryException |IOException | URISyntaxException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (file != null) file.delete();
                }

                return null;
            }
        );

    }

    @Override
    public void publish() throws NetworkError {

        AtomicReference<NetworkError> networkError = new AtomicReference<>(null);

        try {
            exec.shutdown();
            exec.awaitTermination(2L, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.getStackTrace();
            throw new RuntimeException(e);
        }

        if (stickers.size() < 1)
            return;

        {
            CSticker png = stickers.get(0);

            // send .png file to tgserver
            File png_file = new File(png.png_fpath);

            try {

                // TODO BUG toUrl

                TelegramMethod.create()
                        .baseUrl(BotCfg.bot_url)
                        .method(this.is_new ? "createNewStickerSet" : "addStickerToSet")
                        .field("user_id", rid.uid())
                        .field("name", sticker_set_name)
                        .field("title", sticker_set_name)
                        .field("emojis", png.sticker.emoji)
                        .file("png_sticker", png_file)
                        .post();

            } catch (InvalidUrlQueryException|URISyntaxException e) {
                throw new RuntimeException(e);
            } finally {
                png_file.delete();
            }

            this.is_new = false;
        }

        stickers.parallelStream().skip(1).forEach(

                (s)-> {

                    // an error occurred, return immediately
                    if (networkError.get() != null)
                        return;

                    final File png_file = new File(s.png_fpath);

                    try {

                        TelegramMethod.create()
                                .baseUrl(BotCfg.bot_url)
                                .method("addStickerToSet")
                                .field("user_id", rid.uid())
                                .field("name", sticker_set_name)
                                .field("title", sticker_set_name)
                                .field("emojis", s.sticker.emoji)
                                .file("png_sticker", png_file)
                                .post();

                    } catch (NetworkError e) {
                        networkError.set(e);
                    } catch (InvalidUrlQueryException | URISyntaxException e) {
                        e.printStackTrace();
                        System.out.println(s.sticker);
                        throw new RuntimeException(e);
                    } finally {
                        png_file.delete();
                    }

                }
        );

        if (networkError.get() != null)
            throw networkError.get();

        try {

            TelegramMethod
                    .create()
                    .baseUrl(BotCfg.bot_url)
                    .method("sendMessage")
                    .field("chat_id", rid.cid())
                    .field("text", "https://telegram.me/addstickers/" + this.sticker_set_name)
                    .get();

        } catch (InvalidUrlQueryException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

    }

}