package com.bunker.bkframework.clientapi.link.bytes;

import com.bunker.bkframework.clientapi.link.Chainer;
import com.bunker.bkframework.clientapi.link.JSONAdapterChainer;
import com.bunker.bkframework.clientapi.link.JSONAdapter;

public class BytesChainer extends JSONAdapterChainer<byte[], byte[]> {
	public BytesChainer() {
		super();
	}

	public BytesChainer(boolean connectionOriented) {
		super(connectionOriented);
	}

	public BytesChainer(Chainer<byte[], byte[]> parent) {
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