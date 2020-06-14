package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;

public class InputMediaVideo extends InputMedia {

        public Integer width, height, duration;
        public Boolean supports_streaming;

        InputMediaVideo() { super(); }
        public InputMediaVideo(JSONObject json) { super(json); }

        @Override
        public void postInit(JSONObject json) {
            super.jsonSet(json);
            selfInit(true, "width", "height", "duration", "supports_streaming");
        }

}
