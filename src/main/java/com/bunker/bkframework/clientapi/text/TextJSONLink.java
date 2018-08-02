package com.bunker.bkframework.clientapi.text;

import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.JSONBridgeLink;
import com.bunker.bkframework.clientapi.link.JSONAdapter;

public class TextJSONLink extends JSONBridgeLink<String, String> {

	public TextJSONLink(JSONAdapter adapter) {
		super(adapter);
	}

	@Override
	protected JSONObject parseJSON(String data) {
		return null;
	}

	@Override
	protected String jsonToPeerData(JSONObject json) {
		return null;
	}

}
