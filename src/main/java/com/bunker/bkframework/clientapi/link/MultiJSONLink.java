package com.bunker.bkframework.clientapi.link;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MultiJSONLink extends JSONAdapter {
	private List<JSONAdapter> mJSONs = new LinkedList<>();

	@Override
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		json.put(WorkConstants.WORKING, WorkConstants.MULTI_JSON_WORKING);

		for (JSONAdapter j : mJSONs) {
			array.put(j.getJson());
		}
		json.put(WorkConstants.MULTI_JSON_WORKING_ARRAY, array);
		return json;
	}

	@Override
	public void receiveJSON(boolean result, JSONObject json) {
		if (result) {
			JSONArray array = json.getJSONArray(WorkConstants.MULTI_JSON_RESULT_ARRAY);
			System.out.println(array);			
		}
	}
	
	public void addChain(JSONAdapter link) {
		mJSONs.add(link);
	}
}