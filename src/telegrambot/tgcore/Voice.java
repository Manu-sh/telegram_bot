package telegrambot.tgcore;

import org.json.JSONObject;

public class Voice extends TGobject {

	public String file_id, mime_type;
	public Long duration, file_size;

	Voice() { super(); }
	public Voice(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "file_id", "duration");
		selfInit(true,  "file_size", "mime_type");
	}

}

