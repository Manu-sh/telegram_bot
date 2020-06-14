package it.manu.sh.telegram_bot.stickerbot;

import it.manu.sh.telegram_bot.tgcore.core.BotCfg;

import java.io.File;
import java.util.regex.Pattern;

public final class BotUtils {


    public final static String bot_commands  = "Hi, i'm " + BotCfg.bot_name + " and i'm under development, _i can handle only stickers set that i had created_ and i support following commands:\n/help\n/new _sticker_set_name_\n/add _sticker_set_name_\n/abort\n/done";

    private final static Pattern strip_suffix_reg = Pattern.compile("\\.(?=[^\\.]+$)");

    // linux only
    public static String webp2png(String path) {

        String path_nsfx = strip_suffix_reg.split(path)[0];

        if (path_nsfx.isEmpty() || !(new File(path)).exists())
            throw new IllegalArgumentException("invalid path: \"" + path + "\"");

        try {

            Runtime.getRuntime().exec(
                    String.format("dwebp %s.webp -o %s.png", path_nsfx, path_nsfx)
            ).waitFor();

            return path_nsfx + ".png";

        } catch (Exception e) { throw new RuntimeException(e); }

    }

}
