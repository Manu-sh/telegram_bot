package telegrambot.tgcore;

import org.json.JSONObject;

public class PhotoSize extends TGobject {

	public String file_id;
	public Long width, height, file_size;

	PhotoSize() { super(); }
	public PhotoSize(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "file_id", "width", "height");
		selfInit(true, "file_size");
		/*
		file_id   = json.getString("file_id");
		width     = json.getLong("width");
		height    = json.getLong("height");
		file_size = json.optInt("file_size");
		*/
	}

}

