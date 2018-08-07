package telegrambot.tgcore;

import org.json.JSONObject;

public class SuccessfulPayment extends TGobject {

	public String currency, invoice_payload, shipping_option_id, telegram_payment_charge_id, provider_payment_charge_id;
	public Long total_amount;
	public OrderInfo order_info;

	SuccessfulPayment() { super(); }
	public SuccessfulPayment(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "currency", "invoice_payload", "telegram_payment_charge_id", "provider_payment_charge_id", "total_amount");
		selfInit(true,  "order_info");
	}

}
