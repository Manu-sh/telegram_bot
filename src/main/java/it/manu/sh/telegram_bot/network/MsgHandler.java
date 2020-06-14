package it.manu.sh.telegram_bot.network;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.JsonUtils;
import it.manu.sh.telegram_bot.tgcore.Update;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

public class MsgHandler implements Iterable<Update> {

    private class MsgHandlerIterator implements Iterator<Update> {

        @Override public boolean hasNext() {
            sync();
            return !empty();
        }

        @Override public Update next() {
            if (!hasNext()) throw new NoSuchElementException();
            return pop();
        }
    }

    // never used only to avoid null check every time
    private final static Queue<Update> NOT_NULL = new LinkedList<>();

    private URI bot_uri;
    private String bot_url;
    private long offset;
    private Queue<Update> updates;

    public MsgHandler(String bot_base_url) {
        try {
            this.updates = NOT_NULL;
            this.bot_uri = new URI(bot_base_url);
            this.bot_url = this.bot_uri.toASCIIString();
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    public void sync() {

        if (!empty()) return;

        try {

            String buf = TelegramMethod.create()
                    .baseUrl(bot_url)
                    .method("getUpdates")
                    .field("limit", "100")
                    .field("offset", offset)
                    .get();

            if ((this.updates = JsonUtils.optQueue(Update::new, new JSONObject(buf).getJSONArray("result"))) == null)
                this.updates = NOT_NULL;
        } catch (NetworkError e) {
            return;
        } catch (InvalidUrlQueryException|URISyntaxException e) {
            throw new RuntimeException(e.getCause());
        }

    }

    public Update pop() {
        if (updates.isEmpty())
            return null;

        Update ret = updates.remove();
        offset = ret.update_id + 1;
        return ret;
    }

    public String url()                { return bot_url;                              }
    public boolean empty()             { return updates.isEmpty();                    }
    public int length()                { return updates.size();                       }
    public Update peek()               { return empty() ? null : updates.peek();      }
    public Iterator<Update> iterator() { return new MsgHandlerIterator();             }

}