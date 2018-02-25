package com.bunker.bkframework.clientapi.link;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.TrueNetwork;

public class NetClearLink<SendDataType, ReceiveDataType> extends NetLink<SendDataType, ReceiveDataType> {
	private Chainer<SendDataType, ReceiveDataType> mChainer;
	public NetClearLink(Chainer<SendDataType, ReceiveDataType> chainer) {
		mChainer = chainer;
	}

	@Override
	public void receive(PeerConnection<SendDataType> b, ReceiveDataType data, int seq) {
	}
	
	@Override
	public void chainning(PeerConnection<SendDataType> b, int seq) {
		mChainer.startNet(new TrueNetwork<SendDataType, ReceiveDataType>());
		result(true);
	}
}
