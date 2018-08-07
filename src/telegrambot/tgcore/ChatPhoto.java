package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.TGobject;

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

