package it.manu.sh.telegram_bot.stickerbot;

import it.manu.sh.telegram_bot.network.InvalidUrlQueryException;
import it.manu.sh.telegram_bot.network.NetworkError;
import it.manu.sh.telegram_bot.network.TelegramMethod;
import it.manu.sh.telegram_bot.tgcore.core.BotCfg;
import it.manu.sh.telegram_bot.tgcore.Update;

import java.net.URISyntaxException;

public abstract class StickerCollectionAbs {

    protected final String sticker_set_name;
    protected final Rid rid;
    protected boolean is_new;

    public StickerCollectionAbs(Rid rid, String sticker_set_name, boolean is_new) throws NetworkError {

        this.rid    = rid;
        this.sticker_set_name = sticker_set_name + "_by_" + BotCfg.bot_name;

        try {

            TelegramMethod.create()
                    .baseUrl(BotCfg.bot_url)
                    .method("getStickerSet")
                    .field("name", this.sticker_set_name)
                    .get();

            this.is_new = false;

        } catch (InvalidUrlQueryException|URISyntaxException e) {
            this.is_new = true;
        }

        if (is_new != this.is_new)
            throw new RuntimeException(getClass().getName()+": sticker pack invalid action");

    }

    public abstract void fetchSticker(Update u) throws NetworkError;
    public abstract void publish() throws NetworkError;
}
