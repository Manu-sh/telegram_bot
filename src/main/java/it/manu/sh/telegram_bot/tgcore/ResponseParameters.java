package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class ResponseParameters extends TGobject {

	public Long migrate_to_chat_id, retry_after;

	ResponseParameters() { super(); }
	public ResponseParameters(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(true, "migrate_to_chat_id", "retry_after");
	}

}
