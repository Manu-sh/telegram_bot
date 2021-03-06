package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class ReplyKeyboardRemove extends TGobject {

	public Boolean remove_keyboard, selective;

	ReplyKeyboardRemove() { super(); }
	public ReplyKeyboardRemove(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "remove_keyboard");
		selfInit(true, "selective");
	}

}
