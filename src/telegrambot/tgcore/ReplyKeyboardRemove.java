package telegrambot.tgcore;

import org.json.JSONObject;

public class ReplyKeyboardRemove extends TGobject { 

	public Boolean remove_keyboard, selective;

	ReplyKeyboardRemove() { super(); }
	public ReplyKeyboardRemove(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "remove_keyboard");
		selfInit(true, "selective");
	}

}
