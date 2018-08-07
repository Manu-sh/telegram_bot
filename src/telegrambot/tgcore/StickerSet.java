package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.JsonUtils;
import telegrambot.tgcore.core.TGobject;

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
