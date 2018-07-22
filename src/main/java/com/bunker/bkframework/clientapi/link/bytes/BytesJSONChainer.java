package com.bunker.bkframework.clientapi.link.bytes;

import com.bunker.bkframework.clientapi.link.Chainer;
import com.bunker.bkframework.clientapi.link.JSONAdapter;
import com.bunker.bkframework.clientapi.link.JSONAdapterChainer;

public class BytesJSONChainer extends JSONAdapterChainer<byte[], byte[]> {
	public BytesJSONChainer() {
		super();
	}

	public BytesJSONChainer(boolean connectionOriented) {
		super(connectionOriented);
	}

	public BytesJSONChainer(Chainer<byte[], byte[]> parent) {
		super(parent);
	}

	public void addChain(JSONAdapter json) {
		BytesJSONLink link = new BytesJSONLink(json);
		super.addChain(link);
	}

	public void addStaticChain(JSONAdapter json) {
		super.addStaticChain(new BytesJSONLink(json));
	}
}