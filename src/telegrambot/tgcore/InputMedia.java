package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.TGobject;

public class InputMedia extends TGobject {


    public String type, media, caption, parse_mode;

    InputMedia() { super(); }
    public InputMedia(JSONObject json) { super(json); }

    @Override
    public void postInit(JSONObject json) {
        super.jsonSet(json);
        selfInit(false, "type", "media");
        selfInit(true, "caption", "parse_mode");
    }

}
