package com.bunker.bkframework.clientapi.http;

import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.JSONAdapter;
import com.bunker.bkframework.clientapi.link.JSONBridgeLink;

public class HttpJSONLink extends JSONBridgeLink<String, String> {

	public HttpJSONLink(JSONAdapter adapter) {
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
