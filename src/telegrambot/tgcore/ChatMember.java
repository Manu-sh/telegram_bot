package telegrambot.tgcore;

import org.json.JSONObject;
import telegrambot.tgcore.core.TGobject;

public class ChatMember extends TGobject {

	public User user;
	public String status;
	public Long until_date;
	public Boolean can_be_edited, can_change_info, can_post_messages, can_edit_messages,
			can_delete_messages, can_invite_users, can_restrict_members, can_pin_messages,
			can_promote_members, can_send_messages, can_send_media_messages, can_send_other_messages,
			can_add_web_page_previews;


	ChatMember() {
		super();
	}
	public ChatMember(JSONObject json) {
		super(json);
	}

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "user", "status");
		selfInit(true, "until_date", "can_be_edited", "can_change_info", "can_post_messages", "can_edit_messages",
				"can_delete_messages", "can_invite_users", "can_restrict_members", "can_pin_messages", "can_promote_members",
				"can_send_messages", "can_send_media_messages", "can_send_other_messages", "can_add_web_page_previews");
	}

}
