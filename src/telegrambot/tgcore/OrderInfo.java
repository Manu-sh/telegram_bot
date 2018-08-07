package telegrambot.tgcore;

import org.json.JSONObject;

public class OrderInfo extends TGobject {

	public String name, phone_number, email;
	public ShippingAddress shipping_address;

	OrderInfo() { super(); }
	public OrderInfo(JSONObject json) { super(json); }
	
	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(true, "name", "phone_number", "email", "shipping_address");
	}

}
