package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.TGobject;

public class PreCheckoutQuery extends TGobject {
	
	public String id, currency, invoice_payload, shipping_option_id;
	public Long total_amount;
	public User from;
	public OrderInfo order_info;

	PreCheckoutQuery() { super(); }
	public PreCheckoutQuery(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "id", "currency", "invoice_payload" );
		selfInit(true,  "from", "shipping_option_id", "order_info");
	}

}
