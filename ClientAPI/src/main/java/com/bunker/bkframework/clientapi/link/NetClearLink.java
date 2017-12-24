package com.bunker.bkframework.clientapi.link;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.TrueNetwork;

public class NetClearLink extends NetLink {
	private Chainer mChainer;
	public NetClearLink(Chainer chainer) {
		mChainer = chainer;
	}

	@Override
	public void receive(PeerConnection b, byte[] data, int seq) {
	}
	
	@Override
	public void chainning(PeerConnection b, int seq) {
		mChainer.startNet(new TrueNetwork());
		result(true);
	}
}
