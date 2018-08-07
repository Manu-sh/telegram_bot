package telegrambot.tgcore;

import org.json.JSONObject;

public class InlineQuery extends TGobject {
	
	public String id, query, offset;
	public User from;
	public Location location;

	InlineQuery() { super(); }
	public InlineQuery(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "id", "query", "offset");
		selfInit(true, "from", "location");
	}

}
