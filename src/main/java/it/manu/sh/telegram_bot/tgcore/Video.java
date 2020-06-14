package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class Video extends TGobject {

	public String file_id, mime_type;
	public Long width, height, duration, file_size;
	public PhotoSize thumb;

	Video() { super(); }
	public Video(JSONObject json) { super(json); }
	
	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "file_id", "width", "height");
		selfInit(true,  "file_size", "mime_type", "thumb");
	}

}

