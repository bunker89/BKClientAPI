package com.bunker.bkframework.clientapi.link;

import com.bunker.bkframework.clientapi.link.bytes.BytesJSONBridgeLink;

public class JSONAdapterChainer<SendDataType, ReceiveDataType> extends Chainer<SendDataType, ReceiveDataType> {
	public JSONAdapterChainer() {
		super();
	}

	public JSONAdapterChainer(boolean connectionOriented) {
		super(connectionOriented);
	}

	public JSONAdapterChainer(Chainer<SendDataType, ReceiveDataType> parent) {
		super(parent);
	}
}