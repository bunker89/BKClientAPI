package com.bunker.bkframework.clientapi.link.bytes;

import org.json.JSONException;
import org.json.JSONObject;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.link.JSONAdapter;
import com.bunker.bkframework.clientapi.link.NetLink;

public class BytesJSONBridgeLink extends NetLink<byte[], byte[]>{
	private JSONAdapter mAdapter;

	public BytesJSONBridgeLink(JSONAdapter adapter) {
		mAdapter = adapter;
		mAdapter.setLink(this);
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
	final public void chainning(PeerConnection<byte[]> peerConnection, int index) {
		JSONObject json = mAdapter.getJson();
		peerConnection.sendToPeer(json.toString().getBytes(), index);
	}
}