package com.bunker.bkframework.clientapi.link.bytes;

import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.JSONAdapter;
import com.bunker.bkframework.clientapi.link.JSONBridgeLink;

public class BytesJSONBridgeLink extends JSONBridgeLink<byte[], byte[]> {

	public BytesJSONBridgeLink(JSONAdapter adapter) {
		super(adapter);
	}

	@Override
	protected JSONObject parseJSON(byte[] data) {
		return new JSONObject(new String(data));
	}

	@Override
	protected byte[] jsonToPeerData(JSONObject json) {
		return json.toString().getBytes();
	}
}