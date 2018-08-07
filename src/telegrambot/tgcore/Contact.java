package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.network.InvalidUrlQueryException;
import telegrambot.network.NetworkError;
import telegrambot.network.TelegramMethod;

import java.net.URISyntaxException;

public class Contact extends TGobject {

	public String phone_number, first_name, last_name, vcard;
	public Long user_id;

	Contact() { super(); }
	public Contact(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "first_name", "phone_number");
		selfInit(true,  "last_name", "user_id", "vcard");
	}

	public void sendContact(Long chat_id) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("sendContact")
					.field("phone_number", this.phone_number)
					.field("first_name",   this.first_name)
					.field("last_name",    this.last_name == null ? "" : this.last_name)
					.field("vcard",    	   this.vcard == null ? "" : this.vcard)
					.get();
		} catch (URISyntaxException |InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

}

