package com.bunker.bkframework.clientapi.link;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.NetLink.OnLinkResultListener;

/**
 * JSON work is can be chained.
 * 
 * (Working as a) - (Working as b) - (Working) - (Working as c)
 * 
 * Each working can get parameter from executed working;
 * 
 * @author 광수
 *
 */
public class MultiJSONLink extends JSONAdapterBase {
	private List<JSONAdapter> mJSONs = new LinkedList<>();

	@Override
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		JSONArray array = new JSONArray();
		json.put(WorkConstants.WORKING, WorkConstants.MULTI_WORKING);

		for (JSONAdapter j : mJSONs) {
			JSONObject adapterJSON = j.getJson();
			if (j.getResultAs() != null) {
				adapterJSON.put(WorkConstants.WORKING_RESULT_AS, j.getResultAs());
			}
			array.put(adapterJSON);
		}
		json.put(WorkConstants.MULTI_WORKING_ARRAY, array);
		return json;
	}

	@Override
	public void receiveJSON(boolean result, JSONObject json) {
		if (result) {
			JSONArray array = json.getJSONArray(WorkConstants.MULTI_WORKING_RESULT_ARRAY);
			for (int i = 0; i < array.length(); i++) {
				JSONObject linkJSON = array.getJSONObject(i);
				boolean linkBool = linkJSON.getBoolean(WorkConstants.WORKING_RESULT);
				JSONAdapter adapter = mJSONs.remove(0);
				adapter.receiveJSON(linkBool, linkJSON);
				adapter.linkResult(linkBool);
			}
		}
	}

	public void addChain(JSONAdapter link) {
		mJSONs.add(link);
	}

	public void addChain(JSONAdapter link, String resultAs) {
		link.setResultAs(resultAs);
		addChain(link);
	}
}