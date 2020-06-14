package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.JsonUtils;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

import java.util.ArrayList;

public class StickerSet extends TGobject {


    public String name, title;
    public Boolean contains_masks;
    public ArrayList<Sticker> stickers;

    StickerSet() { super(); }
    public StickerSet(JSONObject json) { super(json); }

    @Override
    public void postInit(JSONObject json) {
        super.jsonSet(json);
        selfInit(false, "name", "title", "contains_masks");
        stickers = JsonUtils.optArrayList(Sticker::new, json.optJSONArray("stickers"));
    }

}
