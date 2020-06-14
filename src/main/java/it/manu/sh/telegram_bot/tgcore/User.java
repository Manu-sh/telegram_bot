package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.network.InvalidUrlQueryException;
import it.manu.sh.telegram_bot.network.NetworkError;
import it.manu.sh.telegram_bot.network.TelegramMethod;
import it.manu.sh.telegram_bot.tgcore.core.BotCfg;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class User extends TGobject {

	public Long id;
	public String first_name, last_name, username, language_code;
	public Boolean is_bot;

	User() { super(); }
	public User(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "id", "first_name", "is_bot");
		selfInit(true, "last_name", "username", "language_code");
	}


	@Override
	public int hashCode() {
		return id.intValue();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		if (((User)o).id == null || id == null) return false;
		return id.compareTo(((User)o).id.longValue()) == 0;
	}

	public ArrayList<UserProfilePhotos> getUserProfilePhotos(int offset, int limit) throws NetworkError {

		try {
			return TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("getUserProfilePhotos")
					.field("user_id", this.id)
					.field("offset",  offset)
					.field("limit",   limit)
					.commonArraryBuilder(UserProfilePhotos::new);
		} catch (URISyntaxException |InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

}

