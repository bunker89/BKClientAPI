package com.bunker.bkframework.clientapi.link;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.NetLink.OnLinkResultListener;

public interface JSONAdapter {
	public JSONObject getJson();
	public void receiveJSON(boolean result, JSONObject json);

	public void setLink(@SuppressWarnings("rawtypes") NetLink link);

	public void setResultAs(String as);

	public String getResultAs();

	public JSONArray getResultParam();

	public void linkResult(boolean result);
	
	@SuppressWarnings("rawtypes")
	public NetLink getLink();

	public void setOnLinkResultListener(OnLinkResultListener listener, String key);

	public void broken();
}
