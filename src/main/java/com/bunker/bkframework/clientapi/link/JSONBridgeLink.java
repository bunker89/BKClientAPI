package com.bunker.bkframework.clientapi.link;

import org.json.JSONException;
import org.json.JSONObject;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.link.NetLink.OnLinkResultListener;

public abstract class JSONBridgeLink<SendDataType, ReceiveDataType> extends NetLink<SendDataType, ReceiveDataType> implements OnLinkResultListener {
	private JSONAdapter mAdapter;
	private	OnLinkResultListener mListener;

	public JSONBridgeLink(JSONAdapter adapter) {
		mAdapter = adapter;
		mAdapter.setLink(this);
	}

	@Override
	public void setOnLinkResultListener(OnLinkResultListener listener, String key) {
		mListener = listener;
		super.setOnLinkResultListener(this, key);
	}

	@Override
	public void result(boolean result, String key, Object link) {
		if (mListener != null) {
			mListener.result(result, key, mAdapter);
		}
	}
	
	@Override
	public void broken() {
		super.broken();
		mAdapter.broken();
	}

	@Override
	final public void receive(PeerConnection<SendDataType> peerConnection, ReceiveDataType data, int i) {
		try {
			JSONObject json = parseJSON(data);
			boolean result = json.getBoolean(WorkConstants.WORKING_RESULT);
			if (result)
				mAdapter.receiveJSON(result, json);
			result(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	final public void chainning(PeerConnection<SendDataType> peerConnection, int index) {
		JSONObject json = mAdapter.getJson();
		peerConnection.sendToPeer(jsonToPeerData(json), index);
	}

	protected abstract JSONObject parseJSON(ReceiveDataType data);
	protected abstract SendDataType jsonToPeerData(JSONObject json);
}