package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.JsonUtils;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

import java.util.ArrayList;

public class UserProfilePhotos extends TGobject {

	public Long total_count;
	public ArrayList<PhotoSize> photos;

	UserProfilePhotos() { super(); }
	public UserProfilePhotos(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "total_count");
		photos	      = JsonUtils.getArrayList(PhotoSize::new, json.optJSONArray("photos"));
	}

}

