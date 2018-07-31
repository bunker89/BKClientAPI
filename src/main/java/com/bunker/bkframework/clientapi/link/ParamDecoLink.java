package com.bunker.bkframework.clientapi.link;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.NetLink.OnLinkResultListener;

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
public class ParamDecoLink implements JSONAdapter {
	private JSONAdapterBase mOrigin;
	private JSONObject mWorkingParam = new JSONObject();

	public ParamDecoLink(JSONAdapterBase origin) {
		mOrigin = origin;
	}
	
	public ParamDecoLink addWorkingParam(String from, String srcKey, String destKey) {
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

	@SuppressWarnings("rawtypes")
	@Override
	public void setLink(NetLink link) {
		mOrigin.setLink(link);		
	}

	@Override
	public void setResultAs(String as) {
		mOrigin.setResultAs(as);
	}

	@Override
	public String getResultAs() {
		return mOrigin.getResultAs();
	}

	@Override
	public JSONArray getResultParam() {
		return mOrigin.getResultParam();
	}

	@Override
	public String getLinkResultKey() {
		return mOrigin.getLinkResultKey();
	}

	@Override
	public OnLinkResultListener getLinkResultListener() {
		return mOrigin.getLinkResultListener();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public NetLink getLink() {
		return mOrigin.getLink();
	}

	@Override
	public void setOnLinkResultListener(OnLinkResultListener listener, String key) {
		mOrigin.setOnLinkResultListener(listener, key);
	}

	@Override
	public void broken() {
		mOrigin.broken();
	}
}
