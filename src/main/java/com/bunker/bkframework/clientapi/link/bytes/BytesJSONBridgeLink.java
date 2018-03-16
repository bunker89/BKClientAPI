package com.bunker.bkframework.clientapi.link.bytes;

import org.json.JSONException;
import org.json.JSONObject;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.link.JSONAdapter;
import com.bunker.bkframework.clientapi.link.NetLink;

public class BytesJSONBridgeLink extends NetLink<byte[], byte[]>{
	private JSONAdapter mAdapter;
	private OnLinkResultListener mResult;

	public BytesJSONBridgeLink(JSONAdapter adapter) {
		mAdapter = adapter;
	}
	
	@Override
	final public void receive(PeerConnection<byte[]> peerConnection, byte[] bytes, int i) {
		try {
			JSONObject json = new JSONObject(new String(bytes));
			boolean result = json.getBoolean("result");
			if (result)
				mAdapter.receiveJSON(result, json);
			result(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setOnLinkResultListener(OnLinkResultListener listener, String key) {
		mResult = listener;
	}

	@Override
	final public void chainning(PeerConnection<byte[]> peerConnection, int index) {
		JSONObject json = mAdapter.getJson();
		peerConnection.sendToPeer(json.toString().getBytes(), index);
	}

	@Override
	protected void linkResult(boolean result, String key) {
		if (mResult != null) {
			mResult.result(result, key, mAdapter);
		}
	}
}