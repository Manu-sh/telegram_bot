package telegrambot.tgcore;

import org.json.JSONObject;

public class Location extends TGobject {

	public Double longitude, latitude;

	Location() { super(); }
	public Location(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "longitude", "latitude");
	}

}
