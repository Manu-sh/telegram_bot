package telegrambot.tgcore.core;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

//
public class BotCfg {

    public final static String bot_token;
    public final static String bot_name;

    static {

        final String cfg_path = System.getProperty("user.home") + File.separator + "telegram_bot.cfg";

        try {

            // anonymous subclass
            Properties bot_cfg = new Properties() {

                @Override
                public String getProperty(String s) {
                    String value = super.getProperty(s);

                    if (value == null || value.isEmpty())
                        throw new ExceptionInInitializerError("field \"" + s + "\" expected into file \"" + cfg_path + "\"," +
                                " but the value found is \"" + value + "\"");

                    return value;
                }
            };

            bot_cfg.load(new FileInputStream(cfg_path));

            // if these fields are null launch a detailed exception ?
            bot_token = bot_cfg.getProperty("bot_token");
            bot_name  = bot_cfg.getProperty("bot_name");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public final static String api_domain = "https://api.telegram.org";
    public final static String bot_url = api_domain + "/" + bot_token;
    public final static String bot_url_files = api_domain + "/file" + "/" + bot_token;

}