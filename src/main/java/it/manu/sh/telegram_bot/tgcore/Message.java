package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.network.InvalidUrlQueryException;
import it.manu.sh.telegram_bot.network.NetworkError;
import it.manu.sh.telegram_bot.network.TelegramMethod;
import it.manu.sh.telegram_bot.tgcore.core.BotCfg;
import it.manu.sh.telegram_bot.tgcore.core.JsonUtils;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class Message extends TGobject {

	public String text, caption, new_chat_title, forward_signature, media_group_id, author_signature, connected_website;
	public Long message_id, date, forward_from_message_id, forward_date, edit_date, migrate_to_chat_id, migrate_from_chat_id;
	public User from, forward_from, new_chat_member, left_chat_member;
	public Chat chat, forward_from_chat;
	public Message reply_to_message, pinned_message;
	public Audio audio;
	public Document document;
	public Sticker sticker;
	public Game game;
	public Video video;
	public Contact contact;
	public Invoice invoice;
	public SuccessfulPayment successful_payment;
	public Voice voice;

	public VideoNote video_note;
	public Location location;
	public Venue venue;

	public ArrayList<MessageEntity> entities, caption_entities;
	public ArrayList<PhotoSize> photo, new_chat_photo;
	public ArrayList<User> new_chat_members;
	public Boolean delete_chat_photo, group_chat_created, supergroup_chat_created, channel_chat_created;

	Message() { super(); }
	public Message(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {

		super.jsonSet(json);
		selfInit(false, "message_id", "date", "chat");

		selfInit(true,  "forward_from_message_id", "forward_date", "edit_date", "migrate_to_chat_id",
				"migrate_from_chat_id", "text", "caption", "new_chat_title", "delete_chat_photo", "group_chat_created",
				"supergroup_chat_created", "sticker", "channel_chat_created", "from", "forward_from", "forward_from_chat", "reply_to_message",
				"audio", "document", "game","video", "contact", "new_chat_member", "left_chat_member", "pinned_message",
				"invoice", "successful_payment", "forward_signature", "media_group_id", "author_signature", "voice", "video_note", "location",
				"venue", "connected_website"
		);

		photo			 = JsonUtils.optArrayList(PhotoSize::new, json.optJSONArray("photo"));
		entities		 = JsonUtils.optArrayList(MessageEntity::new, json.optJSONArray("entities"));
		caption_entities = JsonUtils.optArrayList(MessageEntity::new, json.optJSONArray("caption_entities"));
		new_chat_photo	 = JsonUtils.optArrayList(PhotoSize::new, json.optJSONArray("new_chat_photo"));
		new_chat_members = JsonUtils.optArrayList(User::new, json.optJSONArray("user"));
	}


	public void forwardMessage(Long chat_id, boolean disable_notification) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("forwardMessage")
					.field("chat_id", chat_id)
					.field("from_chat_id", this.chat.id)
					.field("message_id", this.message_id)
					.field("disable_notification", disable_notification)
					.get();
		} catch (URISyntaxException|InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void reply(String text) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("sendMessage")
					.field("chat_id", this.chat.id)
					.field("text", text)
					.field("reply_to_message_id", this.message_id)
					.get();
		} catch (URISyntaxException|InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	/* Send Markdown or HTML, if you want Telegram apps to show bold, italic,
	fixed-width text or inline URLs in your bot's message. */

	public void reply(String parse_mode, String text) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("sendMessage")
					.field("chat_id", this.chat.id)
					.field("parse_mode", parse_mode)
					.field("reply_to_message_id", this.message_id)
					.field("text", text)
					.get();
		} catch (URISyntaxException|InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void pinChatMessage(boolean disable_notification) throws NetworkError {

		try {
			TelegramMethod.create()
				.baseUrl(BotCfg.bot_url)
				.method("pinChatMessage")
				.field("chat_id", this.chat.id)
				.field("message_id", this.message_id)
				.field("disable_notification", disable_notification)
				.get();
		} catch (URISyntaxException|InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void deleteMessage() throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("deleteMessage")
					.field("chat_id", this.chat.id)
					.field("message_id", this.message_id)
					.get();
		} catch (URISyntaxException|InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void editMessage(String text) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("editMessageText")
					.field("chat_id", this.chat.id)
					.field("message_id", this.message_id)
					.field("text", text)
					.get();
		} catch (URISyntaxException|InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	/* Date when the user will be unbanned, unix time.
	If user is banned for more than 366 days or less than 30 seconds
	from the current time they are considered to be banned forever */

	public void kickChatMember(int until_date) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("kickChatMember")
					.field("chat_id", this.chat.id)
					.field("user_id", this.from.id)
					.field("until_date", until_date)
					.get();
		} catch (URISyntaxException|InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}


	public void restrictChatMember(
			boolean can_send_messages,
			boolean can_send_media_messages,
			boolean can_send_other_messages,
			boolean can_add_web_page_previews,
			int until_date) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("restrictChatMember")
					.field("chat_id", this.chat.id)
					.field("user_id", this.from.id)
					.field("can_send_messages", can_send_messages)
					.field("can_send_media_messages", can_send_media_messages)
					.field("can_send_other_messages", can_send_other_messages)
					.field("can_add_web_page_previews", can_add_web_page_previews)
					.field("until_date", until_date)
					.get();
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void promoteChatMember(
			boolean can_change_info,
			boolean can_post_messages,
			boolean can_edit_messages,
			boolean can_delete_messages,
			boolean can_invite_users,
			boolean can_restrict_members,
			boolean can_pin_messages,
			boolean can_promote_members) throws NetworkError {

		try {
			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("restrictChatMember")
					.field("chat_id", this.chat.id)
					.field("user_id", this.from.id)
					.field("can_change_info",      can_change_info)
					.field("can_post_messages",    can_post_messages)
					.field("can_edit_messages",    can_edit_messages)
					.field("can_delete_messages",  can_delete_messages)
					.field("can_invite_users",     can_invite_users)
					.field("can_restrict_members", can_restrict_members)
					.field("can_pin_messages",     can_pin_messages)
					.field("can_promote_members",  can_promote_members)
					.get();
		} catch (URISyntaxException | InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}


	public ChatMember getChatMember() throws NetworkError {

		try {
			return TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("getChatMember")
					.field("chat_id", this.chat.id)
					.field("user_id", this.from.id)
					.commonObjectBuilder(ChatMember::new);
		} catch (URISyntaxException|InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}


}

