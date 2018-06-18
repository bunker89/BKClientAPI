package com.bunker.bkframework.clientapi.link;

import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.NetLink.OnLinkResultListener;

public abstract class JSONAdapter {
	private OnLinkResultListener mResult;
	private String mResultKey;

	@SuppressWarnings("rawtypes")
	private NetLink mLink;
	public abstract JSONObject getJson();
	public abstract void receiveJSON(boolean result, JSONObject json);
	
	public void setLink(@SuppressWarnings("rawtypes") NetLink link) {
		mLink = link;
		mLink.setOnLinkResultListener(mResult, mResultKey);
	}

	@SuppressWarnings("rawtypes")
	public NetLink getLink() {
		return mLink;
	}

	public void setOnLinkResultListener(OnLinkResultListener listener, String key) {
		mResult = listener;
		mResultKey = key;
		if (mLink != null) {
			mLink.setOnLinkResultListener(listener, key);
		}
	}
	
	public void broken() {
		
	}
}