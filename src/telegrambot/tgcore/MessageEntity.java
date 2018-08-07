package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.TGobject;

public class MessageEntity extends TGobject {

	public String type, url;
	public Long offset, length;
	public User user;
 
	MessageEntity() { super(); }
	public MessageEntity(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "type", "offset", "length");
		selfInit(true,  "url", "user");
	}

}

