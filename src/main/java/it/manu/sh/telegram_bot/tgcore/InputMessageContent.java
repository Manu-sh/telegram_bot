package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class InputMessageContent extends TGobject {

    public String message_text, parse_mode;
    public Boolean disable_web_page_preview;

    InputMessageContent() { super(); }
    public InputMessageContent(JSONObject json) { super(json); }

    @Override
    public void postInit(JSONObject json) {
        super.jsonSet(json);
        selfInit(false, "message_text");
        selfInit(true, "parse_mode", "disable_web_page_preview");
    }

}