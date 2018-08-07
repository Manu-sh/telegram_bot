package telegrambot.network;

/** The request is malformed or the server response is 400 HttpURLConnection.HTTP_BAD_REQUEST */
@SuppressWarnings("serial")
public class InvalidUrlQueryException extends Exception {

    /** @param msg the error msg */
    public InvalidUrlQueryException(String msg) {
        super(msg);
    }

}