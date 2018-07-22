package com.bunker.bkframework.clientapi.link;

import org.json.JSONObject;

public class KeyConvertLink extends JSONAdapter {
	private KeyConvertBuilder mConvertBuilder;
	private String mConvertWorking;
	
	public KeyConvertLink(String convertWorking, KeyConvertBuilder convertBuilder) {
		mConvertWorking = convertWorking;
		mConvertBuilder = convertBuilder;
	}

	@Override
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		json.put(WorkConstants.WORKING, mConvertWorking);
		json.put(WorkConstants.KEY_CONVERT_ARRAY, mConvertBuilder.build());
		return json;
	}

	@Override
	public void receiveJSON(boolean result, JSONObject json) {
	}
}