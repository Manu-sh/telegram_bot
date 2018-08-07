package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.JsonUtils;
import telegrambot.tgcore.core.TGobject;

import java.util.ArrayList;

public class ReplyKeyboardMarkup extends TGobject {

	// TODO test
	public ArrayList<ArrayList<KeyboardButton>> keyboard;

	public Boolean resize_keyboard, one_time_keyboard, selective;

	ReplyKeyboardMarkup() { super(); }
	public ReplyKeyboardMarkup(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(true,  "resize_keyboard", "one_time_keyboard", "selective");
		keyboard = JsonUtils.optArrayListOfArrayList(KeyboardButton::new, json.optJSONArray("keyboard"));
		// keyboard = JsonUtils.getArrayList(KeyboardButton::new, json.optJSONArray("keyboard"));
	}

}
