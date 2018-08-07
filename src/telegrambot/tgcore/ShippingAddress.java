package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.TGobject;


public class ShippingAddress extends TGobject {

	public String country_code, state, city, street_line1, street_line2, post_code;

	ShippingAddress() { super(); }
	public ShippingAddress(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "country_code", "state", "city", "street_line1", "street_line2", "post_code");
	}

}
