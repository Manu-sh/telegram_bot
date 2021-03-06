package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class KeyboardButton extends TGobject {

	public String text;
	public Boolean request_contact, request_location;

	KeyboardButton() { super(); }
	public KeyboardButton(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "text");
		selfInit(true,  "request_contact", "request_location");
	}

}
