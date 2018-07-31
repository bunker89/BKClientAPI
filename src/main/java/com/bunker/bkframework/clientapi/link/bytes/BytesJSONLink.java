package com.bunker.bkframework.clientapi.link.bytes;

import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.JSONBridgeLink;
import com.bunker.bkframework.clientapi.link.JSONAdapter;

public class BytesJSONLink extends JSONBridgeLink<byte[], byte[]> {

	public BytesJSONLink(JSONAdapter adapter) {
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