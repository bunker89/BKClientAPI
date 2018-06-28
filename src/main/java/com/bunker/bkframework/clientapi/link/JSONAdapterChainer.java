package com.bunker.bkframework.clientapi.link;

public abstract class JSONAdapterChainer<SendDataType, ReceiveDataType> extends Chainer<SendDataType, ReceiveDataType> {
	public JSONAdapterChainer() {
		super();
	}

	public JSONAdapterChainer(boolean connectionOriented) {
		super(connectionOriented);
	}

	public JSONAdapterChainer(Chainer<SendDataType, ReceiveDataType> parent) {
		super(parent);
	}
	
	public abstract void addChain(JSONAdapter adapter);
}