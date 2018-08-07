package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.TGobject;

public class InputLocationMessageContent extends TGobject {

    public Float latitude, longitude;
    public Integer live_period;

    InputLocationMessageContent() { super(); }
    public InputLocationMessageContent(JSONObject json) { super(json); }

    @Override
    public void postInit(JSONObject json) {
        super.jsonSet(json);
        selfInit(false, "latitude", "longitude");
        selfInit(true, "live_period");
    }

}
