package com.bunker.bkframework.clientapi;


import com.bunker.bkframework.business.PeerConnection;

public interface NetHandle<SendDataType, ReceiveDataType> {
	public void receive(PeerConnection<SendDataType> b, ReceiveDataType data, int seq);
	public void chainning(PeerConnection<SendDataType> b, int seq);
	public void broken();
}