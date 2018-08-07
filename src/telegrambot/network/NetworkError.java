package telegrambot.network;

import java.io.IOException;

public class NetworkError extends Exception {

    public NetworkError(IOException e) {
        super(e.getMessage());
    }

}
