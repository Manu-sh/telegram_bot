package telegrambot.tgcore;

import org.json.JSONObject;


public class InputVenueMessageContent extends TGobject {

    public Float latitude, longitude;
    public String title, address, foursquare_id, foursquare_type;

    InputVenueMessageContent() { super(); }
    public InputVenueMessageContent(JSONObject json) { super(json); }

    @Override
    public void postInit(JSONObject json) {
        super.jsonSet(json);
        selfInit(false, "latitude", "longitude", "title", "address");
        selfInit(true, "foursquare_id", "foursquare_type");
    }

}
