package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class Audio extends TGobject {

	public String file_id, performer, title, mime_type;
	public Long duration, file_size;

	Audio() { super(); }
	public Audio(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "file_id", "duration");
		selfInit(true,  "performer", "title", "mime_type", "file_size");
	}

}

