package com.bunker.bkframework.clientapi.link;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.NetLink.OnLinkResultListener;

public abstract class JSONAdapterBase implements JSONAdapter {
	private OnLinkResultListener mResult;
	private String mResultKey;
	private String mAs;
	private JSONArray mResultParam;

	@SuppressWarnings("rawtypes")
	private NetLink mLink;

	@Override
	public void setLink(@SuppressWarnings("rawtypes") NetLink link) {
		mLink = link;
		mLink.setOnLinkResultListener(mResult, mResultKey);
	}

	@Override
	public void setResultAs(String as) {
		mAs = as;
	}

	@Override
	public String getResultAs() {
		return mAs;
	}

	@Override
	public JSONArray getResultParam() {
		return mResultParam;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public NetLink getLink() {
		return mLink;
	}

	@Override
	public void setOnLinkResultListener(OnLinkResultListener listener, String key) {
		mResult = listener;
		mResultKey = key;
		if (mLink != null) {
			mLink.setOnLinkResultListener(listener, key);
		}
	}

	@Override
	public void broken() {
	}

	@Override
	public void linkResult(boolean result) {
		if (mResult != null) {
			mResult.result(result, mResultKey, this);
		}
	}
}