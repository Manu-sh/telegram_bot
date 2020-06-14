package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.network.InvalidUrlQueryException;
import it.manu.sh.telegram_bot.network.NetworkError;
import it.manu.sh.telegram_bot.network.TelegramMethod;
import it.manu.sh.telegram_bot.tgcore.core.BotCfg;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class Chat extends TGobject {

	public Long id;
	public String type, title, username, first_name, last_name, description, invite_link, sticker_set_name;
	public Boolean all_members_are_administrators, can_set_sticker_set;
	public ChatPhoto photo;
	public Message pinned_message;

	Chat() { super(); }
	public Chat(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "id", "type");
		selfInit(true, "title", "first_name", "last_name", "username", "description", "invite_link",
				"all_members_are_administrators", "photo", "pinned_message", "sticker_set_name", "can_set_sticker_set");
	}


	@Override
	public int hashCode() {
		return id.intValue();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (getClass() != o.getClass()) return false;
		if (((Chat)o).id == null || id == null) return false;
		return id.compareTo(((Chat)o).id.longValue()) == 0;
	}

	public void sendMessage(String text) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("sendMessage")
					.field("chat_id", this.id)
					.field("text", text)
					.get();
		} catch (URISyntaxException|InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}
	}

	public void sendMessage(String parse_mode, String text) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("sendMessage")
					.field("chat_id", this.id)
					.field("parse_mode", parse_mode)
					.field("text", text)
					.get();
		} catch (URISyntaxException|InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}
	}

	public void unpinChatMessage() throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("unpinChatMessage")
					.field("chat_id", id)
					.get();
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void setChatDescription(String description) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("setChatDescription")
					.field("chat_id", id)
					.field("description", description)
					.get();
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void setChatTitle(String title) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("setChatTitle")
					.field("chat_id", id)
					.field("title", title)
					.get();
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void setChatPhoto(java.io.File photo) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("setChatPhoto")
					.field("chat_id", id)
					.file("photo", photo)
					.post();
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void deleteChatPhoto() throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.baseUrl(BotCfg.bot_url)
					.method("deleteChatPhoto")
					.field("chat_id", id)
					.get();
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void leaveChat() throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("leaveChat")
					.field("chat_id", id)
					.get();
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public ArrayList<ChatMember> getChatAdministrators() throws NetworkError {

		try {
			return TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("getChatAdministrators")
					.field("chat_id", id)
					.commonArraryBuilder(ChatMember::new);
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public Long getChatMembersCount() throws NetworkError {

		try {
			return TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("getChatMembersCount")
					.field("chat_id", id)
					.commonObjectBuilder(Long.class);
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public String exportChatInviteLink() throws NetworkError {

		try {
			return TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("exportChatInviteLink")
					.field("chat_id", id)
					.commonObjectBuilder(String.class);
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}


}

