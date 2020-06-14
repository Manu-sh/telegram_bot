package it.manu.sh.telegram_bot.network;

import java.io.IOException;

public class NetworkError extends Exception {

    public NetworkError(IOException e) {
        super(e.getMessage());
    }

}
