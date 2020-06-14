package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class Venue extends TGobject {

	public Location location;
	public String title, address, foursquare_id;

	Venue() { super(); }
	public Venue(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "title", "address");
		selfInit(true,  "location", "foursquare_id");
	}

}

