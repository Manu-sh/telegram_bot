package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.JsonUtils;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

import java.util.ArrayList;

public class Game extends TGobject {

	public String title, description, text;
	public Animation animation;
	public ArrayList<PhotoSize> photo;
	public ArrayList<MessageEntity> text_entities;

	Game() { super(); }
	public Game(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "title", "description", "text");
		selfInit(true,  "animation");
		photo	      = JsonUtils.getArrayList(PhotoSize::new, json.optJSONArray("photo"));
		text_entities = JsonUtils.optArrayList(MessageEntity::new, json.optJSONArray("text_entities"));
	}


}

