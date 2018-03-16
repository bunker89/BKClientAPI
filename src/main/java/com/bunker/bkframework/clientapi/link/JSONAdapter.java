package com.bunker.bkframework.clientapi.link;

import org.json.JSONObject;

public abstract class JSONAdapter {

	@SuppressWarnings("rawtypes")
	private NetLink mLink;
	public abstract JSONObject getJson();
	public abstract void receiveJSON(boolean result, JSONObject json);
	
	public void setLink(@SuppressWarnings("rawtypes") NetLink link) {
		mLink = link;
	}

	@SuppressWarnings("rawtypes")
	public NetLink getLink() {
		return mLink;
	}
}