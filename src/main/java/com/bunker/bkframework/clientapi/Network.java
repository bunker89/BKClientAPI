package com.bunker.bkframework.clientapi;

import com.bunker.bkframework.business.PeerConnection;

public interface Network<SendDataType, ReceiveDataType> {
	/**
	 * start method is must can reentrant.
	 */
	public void start();
	public void changeHandle(NetHandle<SendDataType, ReceiveDataType> handle);
	public int getNextSequence();
	public PeerConnection<SendDataType> getPeerConnection();
}