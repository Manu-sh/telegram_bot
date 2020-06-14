package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class ForceReply extends TGobject {

	public Boolean selective, force_reply;

	ForceReply() { super(); }
	public ForceReply(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "force_reply");
		selfInit(true,  "selective");
	}

}
