package com.bunker.bkframework.clientapi.link;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class MultiJSONLink extends JSONAdapter {
	private List<JSONAdapter> mJSONs = new LinkedList<>();
	private Object mWorkingKey = null;

	public MultiJSONLink(Object workingKey) {
		mWorkingKey = workingKey;
	}
	
	@Override
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		json.put("working", mWorkingKey);

		for (JSONAdapter j : mJSONs) {
			array.put(j.getJson());
		}
		json.put("working_array", array);
		return json;
	}

	@Override
	public void receiveJSON(boolean result, JSONObject json) {
		if (result) {
			JSONArray array = json.getJSONArray("result_array");
			System.out.println(array);			
		}
	}
	
	public void addChain(JSONAdapter link) {
		mJSONs.add(link);
	}
}