package com.bunker.bkframework.clientapi.link;

import org.json.JSONObject;

public interface JSONLink {
	public int getWork();
	public JSONObject getJson();
	public void receiveJSON(boolean result, JSONObject json);
}