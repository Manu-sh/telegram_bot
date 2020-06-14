package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.network.InvalidUrlQueryException;
import it.manu.sh.telegram_bot.network.NetworkError;
import it.manu.sh.telegram_bot.network.TelegramMethod;
import it.manu.sh.telegram_bot.tgcore.core.BotCfg;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

import java.net.URISyntaxException;

public class File extends TGobject {

	public String file_id, file_path;
	public Long file_size;

	File() { super(); }
	public File(JSONObject json) { super(json); }

	@Override
	public void postInit(JSONObject json) {
		super.jsonSet(json);
		selfInit(false, "file_id");
		selfInit(true, "file_path", "file_size");
	}

	public void tryGetMoreFileInfo() throws NetworkError {

		try {

			if (this.file_size != null && this.file_path != null)
				return;

			File f = TelegramMethod.create()
					.baseUrl(BotCfg.bot_url)
					.method("getFile")
					.field("file_id", this.file_id)
					.commonObjectBuilder(File::new);

			this.file_path = f.file_path;
			this.file_size = f.file_size;

		} catch (URISyntaxException |InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

	public void download(java.io.File file, boolean overwrite) throws NetworkError {

		try {

			tryGetMoreFileInfo();

			TelegramMethod.create()
					.baseUrl(BotCfg.bot_url_files + "/" + file_path)
					.field("file_id", this.file_id)
					.get(file, overwrite);

		} catch (URISyntaxException |InvalidUrlQueryException e) {
			throw new RuntimeException(e);
		}

	}

}
