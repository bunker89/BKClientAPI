package com.bunker.bkframework.clientapi.link;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * {"bk-work-param":
 *   {"(fromAs)":[],
 *    "(fromAs)":[]
 *   }
 * }
 * 
 * @author ys89
 *
 */
public class WorkingParamLink extends JSONAdapter {
	private JSONAdapter mOrigin;
	private JSONObject mWorkingParam = new JSONObject();

	public WorkingParamLink(JSONAdapter origin) {
		mOrigin = origin;
	}
	
	public WorkingParamLink addWorkingParam(String from, String srcKey, String destKey) {
		JSONArray array;
		if (!mWorkingParam.has(from)) {
			array = new JSONArray();
			mWorkingParam.put(from, array);
		} else {
			array = mWorkingParam.getJSONArray(from);
		}
		
		JSONObject json = new JSONObject();
		json.put(srcKey, destKey);
		array.put(json);
		return this;
	}

	@Override
	public JSONObject getJson() {
		JSONObject json = mOrigin.getJson();
		if (mWorkingParam.length() > 0)
			json.put(WorkConstants.WORKING_PARAM_JSON, mWorkingParam);
		return json;
	}

	@Override
	public void receiveJSON(boolean result, JSONObject json) {
		mOrigin.receiveJSON(result, json);
	}
}
