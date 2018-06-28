package com.bunker.bkframework.clientapi.link;

import com.bunker.bkframework.clientapi.Network;

public interface Chainable<SendDataType, ReceiveDataType> {
	public void addChain(NetLink<SendDataType, ReceiveDataType> chain);
	public void startNet(Network<SendDataType, ReceiveDataType> network);
}
