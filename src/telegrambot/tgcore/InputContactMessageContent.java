package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.TGobject;

public class InputContactMessageContent extends TGobject {

    public String phone_number, first_name, last_name, vcard;

    InputContactMessageContent() { super(); }
    public InputContactMessageContent(JSONObject json) { super(json); }

    @Override
    public void postInit(JSONObject json) {
        super.jsonSet(json);
        selfInit(false, "phone_number", "phone_number");
        selfInit(true, "last_name", "vcard");
    }

}
