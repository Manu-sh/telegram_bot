package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class ChatPhoto extends TGobject {

	public String small_file_id, big_file_id;

	ChatPhoto() { super(); }
	public ChatPhoto(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "small_file_id", "big_file_id");
	}

}

