package telegrambot.tgcore;

import org.json.JSONObject;

public class Animation extends TGobject {

	public String file_id, file_name, mime_type;
	public Long file_size;
	public PhotoSize thumb;

	Animation() { super(); }
	public Animation(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "file_id");
		selfInit(true, "thumb", "file_size", "mime_type", "first_name");
	}


}

