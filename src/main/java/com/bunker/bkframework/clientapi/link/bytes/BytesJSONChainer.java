package com.bunker.bkframework.clientapi.link.bytes;

import com.bunker.bkframework.clientapi.link.Chainer;
import com.bunker.bkframework.clientapi.link.JSONAdapter;

public class BytesJSONChainer extends Chainer<byte[], byte[]> {

	public BytesJSONChainer(boolean connectionOriented) {
		super(connectionOriented);
	}

	public BytesJSONChainer(Chainer<byte[], byte[]> parent) {
		super(parent);
	}

	public void addChain(JSONAdapter json) {
		BytesJSONBridgeLink link = new BytesJSONBridgeLink(json);
		json.setLink(link);
		super.addChain(link);
	}

	public void addStaticChain(JSONAdapter json) {
		super.addStaticChain(new BytesJSONBridgeLink(json));
	}
}