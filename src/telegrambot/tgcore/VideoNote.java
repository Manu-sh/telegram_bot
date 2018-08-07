package telegrambot.tgcore;
import org.json.JSONObject;

// TODO test !!!
public class VideoNote extends Voice {
	public PhotoSize thumb;

	VideoNote() { super(); }
	public VideoNote(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.postInit(json);
		selfInit(true, "thumb");
	}

}

