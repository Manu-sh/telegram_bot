package it.manu.sh.telegram_bot.tgcore.core;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.json.JSONObject;

abstract public class TGobject {

	// boolean isEmpty;
	public JSONObject json;

	public TGobject() { }

	public TGobject(JSONObject json) {
		this.json = json;
		postInit(json);
	}

	abstract public void postInit(JSONObject json);

	public void jsonSet(JSONObject json) {
		if (json == null) throw new SelfInitException("JSONObject is null");
		this.json = json;
	}

	public void selfInit(boolean areOpt, String... fieldsnames) {
		for (String fieldname : fieldsnames)
			JsonUtils.reflectInit(this, fieldname, areOpt);
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

}
