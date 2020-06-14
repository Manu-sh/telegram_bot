package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class Document extends TGobject {

	public String file_id, file_name, mime_type;
	public PhotoSize thumb;
	public Long file_size;

	Document() { super(); }
	public Document(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "file_id");
		selfInit(true,  "file_name", "mime_type", "file_size", "thumb");
	}

}

