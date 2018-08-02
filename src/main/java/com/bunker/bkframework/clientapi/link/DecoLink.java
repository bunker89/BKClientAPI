package com.bunker.bkframework.clientapi.link;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.NetLink.OnLinkResultListener;

public abstract class DecoLink implements JSONAdapter {
	private JSONAdapter mOrigin;

	DecoLink(JSONAdapter origin) {
		mOrigin = origin;
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
	
	@Override
	public void linkResult(boolean result) {
		mOrigin.linkResult(result);
	}
}
