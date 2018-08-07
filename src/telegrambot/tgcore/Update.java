package telegrambot.tgcore;

import org.json.JSONObject;

public class Update extends TGobject {

	public Long update_id;
	public Message message, edited_message, channel_post, edited_channel_post;
	public InlineQuery inline_query;
	public ChosenInlineResult chosen_inline_result;
	public CallbackQuery callback_query;
	public ShippingQuery shipping_query;
	public PreCheckoutQuery pre_checkout_query;

	Update() { super(); }
	public Update(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "update_id");
		selfInit(true, "message", "edited_message", "channel_post", "edited_channel_post", "inline_query", "chosen_inline_result",
			       "callback_query", "shipping_query", "pre_checkout_query");
	}

	@Override
	public int hashCode() {
		return update_id.intValue();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		if (((Update)o).update_id == null || update_id == null) return false;
		return update_id.compareTo(((Update)o).update_id.longValue()) == 0;
	}

	public String getMessageText() {
		try { return message.text; }
	       	catch (NullPointerException e) { return null; }
	}

}
