package com.bunker.bkframework.clientapi.link;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.NetHandle;


public class SingleLink<SendDataType, ReceiveDataType> implements NetHandle<SendDataType, ReceiveDataType> {
	private NetLink<SendDataType, ReceiveDataType> mLink;
	
	public SingleLink(NetLink<SendDataType, ReceiveDataType> link) {
		mLink = link;
	}

	@Override
	public void receive(PeerConnection<SendDataType> b, ReceiveDataType data, int seq) {
		mLink.receive(b, data, seq);
		b.closePeer();
	}

	@Override
	public void chainning(PeerConnection<SendDataType> b, int seq) {
		mLink.setMainChain();
		mLink.chainning(b, seq);
	}

	@Override
	public void broken() {
		mLink.broken();
	}
}