package it.manu.sh.telegram_bot.network;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.JsonUtils;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

// TODO use toURL() ?

public class TelegramMethod implements ITelegramMethod {

    private final static Pattern word_regex = Pattern.compile("\\w*");

    private URI uri;
    private HashMap<String,String> fields;
    private HashMap<String, File> files;
    private String method_name;

    // private constructor
    private TelegramMethod() {}

    // factory method
    public static TelegramMethod create() {
        return new TelegramMethod();
    }

    @Override
    public TelegramMethod baseUrl(String bot_url) throws URISyntaxException {
        this.fields = new HashMap<>();
        this.files  = new HashMap<>();
        this.uri    = new URI(bot_url);
        return this;
    }

    @Override
    public TelegramMethod method(String method_name) throws URISyntaxException {

        if (this.method_name != null)
            throw new URISyntaxException("double invokation", "");

        if (!word_regex.matcher(method_name).matches())
            throw new URISyntaxException("invalid method name", "");

        this.method_name = method_name;
        return this;
    }

    @Override
    public TelegramMethod field(String key, String value) throws URISyntaxException {

        // handle method() never called
        assert(this.method_name != null);

        if (!word_regex.matcher(method_name).matches())
            throw new URISyntaxException("invalid key name", "");

        fields.put(key, value);
        return this;
    }

    public TelegramMethod field(String key, Object value) throws URISyntaxException {
        return field(key, value+"");
    }

    @Override
    public TelegramMethod file(String key, File value) throws URISyntaxException {

        // handle method() never called
        assert(this.method_name != null);

        if (!word_regex.matcher(method_name).matches())
            throw new URISyntaxException("invalid key name", "");

        files.put(key, value);
        return this;
    }

    private final String assemblyAsUrlQuery() {

        if (!files.isEmpty()) throw new IllegalArgumentException("use post() to send files as multipart-form-data");

        /* StringBuffer is synchronized, StringBuilder not
        but i want the best performance so i synchronized it by my self */

        StringBuilder cat = new StringBuilder();
        String tmp = this.uri.toString();

        // is not an url query (don't add the trailing slash and return immediately)
        if (this.method_name == null)
            return tmp;

        cat.append(tmp + (tmp.endsWith("/") ? "" : "/"));
        cat.append(this.method_name + (fields.isEmpty() ? "" : "?"));

        if (fields.size() >= 1) {

            Lock lock = new ReentrantLock();

            fields.entrySet().parallelStream().forEach(
                   fields.size() == 1 ?
                    (e)-> {
                       lock.lock();
                       try {
                           // PERCENT ENCODING
                           cat.append(e.getKey() + "=" + URLEncoder.encode(e.getValue(), "UTF-8"));
                       } catch (UnsupportedEncodingException ee) {
                           throw new RuntimeException(ee);
                       } finally {
                           lock.unlock();
                       }
                   } : (e)-> {
                       lock.lock();
                       try {
                           // PERCENT ENCODING
                           cat.append(e.getKey() + "=" + URLEncoder.encode(e.getValue(), "UTF-8") + "&");
                       } catch (UnsupportedEncodingException ee) {
                           throw new RuntimeException(ee);
                       } finally {
                           lock.unlock();
                       }
                   }
            );

           // ok it's a little shitty
           if (cat.charAt(cat.length() - 1) == '&')
               cat.setLength(cat.length() - 1);

        }

        return cat.toString();
    }

    @Override
    public String get() throws InvalidUrlQueryException, NetworkError {
        return Utils.getHtmlBody( assemblyAsUrlQuery() );
    }

    @Override
    public void get(File file, boolean overwrite) throws InvalidUrlQueryException, NetworkError {

        try {
            Utils.getHtmlBody(assemblyAsUrlQuery(), file, overwrite);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    @Override
    public void get(OutputStream os) throws InvalidUrlQueryException, NetworkError {
        Utils.getHtmlBody(assemblyAsUrlQuery(), os);
    }


    // TODO
    @Override
    public String post() throws InvalidUrlQueryException, NetworkError {
        // don't send percent-encoded fields when we make a post request
        return Multipart.multipartFormData(this.uri.toString(), method_name, fields, files);
    }


    // other useful methods, TODO exceptions

    // commonObjectBuilder(User::new)
    public <T extends TGobject> T commonObjectBuilder(JsonUtils.ConstructorII<T> c) throws InvalidUrlQueryException, NetworkError {
        return c.create(new JSONObject(this.get()).getJSONObject("result"));
    }

    // commonArraryBuilder(User::new)
    public <T extends TGobject> ArrayList<T> commonArraryBuilder(JsonUtils.ConstructorII<T> c) throws InvalidUrlQueryException, NetworkError {
        JSONObject json = new JSONObject(this.get());
        return JsonUtils.getArrayList(c, json.optJSONArray("result"));
    }

    // commonObjectBuilder(Long.class)
    public <T extends Object> T commonObjectBuilder(Class<T> cl) throws InvalidUrlQueryException, NetworkError {

            if (cl == Long.class)
                return (T)new Long(new JSONObject(this.get()).getLong("result"));

            if (cl == Integer.class)
                return (T)new Integer(new JSONObject(this.get()).getInt("result"));

            if (cl == Double.class)
                return (T)new Double(new JSONObject(this.get()).getDouble("result"));

            if (cl == Boolean.class)
                return (T)new Boolean(new JSONObject(this.get()).getBoolean("result"));

            if (cl == String.class)
                return (T)new JSONObject(this.get()).getString("result");

            throw new RuntimeException("invalid type: "+cl.getName());
    }

    public String toURL() {
        return assemblyAsUrlQuery();
    }

}