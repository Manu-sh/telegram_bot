package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.TGobject;

public class ChosenInlineResult extends TGobject {
	
	public String result_id, query, inline_message_id;
	public User from;
	public Location location;

	ChosenInlineResult() { super(); }
	public ChosenInlineResult(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "result_id", "query", "from");
		selfInit(true, "from", "inline_message_id", "location");
	}


}
