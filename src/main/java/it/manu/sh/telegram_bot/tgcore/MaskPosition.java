package it.manu.sh.telegram_bot.tgcore;

import org.json.JSONObject;
import it.manu.sh.telegram_bot.tgcore.core.TGobject;

public class MaskPosition extends TGobject {

    public String point;
    public Double x_shift, y_shift, scale;

    MaskPosition() { super(); }
    public MaskPosition(JSONObject json) { super(json); }

    @Override
    public void postInit(JSONObject json) {
        super.jsonSet(json);
        selfInit(false, "point", "x_shift", "y_shift", "scale");
    }

}
