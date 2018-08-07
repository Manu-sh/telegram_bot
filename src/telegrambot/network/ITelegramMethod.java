package telegrambot.network;

import java.io.File;
import java.io.OutputStream;
import java.net.URISyntaxException;

public interface ITelegramMethod {
    ITelegramMethod baseUrl(String bot_url)         throws URISyntaxException;
    ITelegramMethod method(String method_name)      throws URISyntaxException;
    ITelegramMethod field(String key, String value) throws URISyntaxException;
    ITelegramMethod file(String key, File value)    throws URISyntaxException;
    String get()                                    throws InvalidUrlQueryException, NetworkError;
    void get(File file, boolean overwrite)          throws InvalidUrlQueryException, NetworkError;
    void get(OutputStream ps)                       throws InvalidUrlQueryException, NetworkError;
    String post()                                   throws InvalidUrlQueryException, NetworkError;
}