package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class Sticker extends TGobject {

	public String file_id, emoji, set_name;
	public Long width, height, file_size;
	public PhotoSize thumb;
	public MaskPosition mask_position; // TODO test

	Sticker() { super(); }
	public Sticker(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "file_id", "width", "height");
		selfInit(true,  "emoji", "file_size", "thumb", "set_name", "mask_position");
	}

}