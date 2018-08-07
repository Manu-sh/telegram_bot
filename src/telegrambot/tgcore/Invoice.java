package telegrambot.tgcore;

import org.json.JSONObject;

public class Invoice extends TGobject {

	public String title, description, start_parameter, currency;
	public Long total_amount; // price of US$ 1.45 pass amount = 145.

	Invoice() { super(); }
	public Invoice(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "title", "description", "start_parameter", "currency", "total_amount");
	}

}

