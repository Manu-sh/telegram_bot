package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class ShippingQuery extends TGobject {

	public String id, invoice_payload;
	public ShippingAddress shipping_address;
	public User from;

	ShippingQuery() { super(); }
	ShippingQuery(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "id", "invoice_payload");
		selfInit(true,  "shipping_address", "from");
	}

}
