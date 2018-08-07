package telegrambot.tgcore;

import org.json.JSONObject;

public class CallbackQuery extends TGobject {

	public String id, chat_instance, inline_message_id, data, game_short_name;
	public User from;
	public Message message;

	CallbackQuery() { super(); }
	public CallbackQuery(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "id", "chat_instance", "from");
		selfInit(true, "inline_message_id", "data", "game_short_name", "message");

	}

}
