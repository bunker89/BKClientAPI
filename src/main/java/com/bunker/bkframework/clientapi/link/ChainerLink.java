package com.bunker.bkframework.clientapi.link;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.Network;

public class ChainerLink extends NetLink {
	private Chainer mSubChainer;
	private Network mSubNetwork;
	private PeerConnection mParentConnection;

	private NetLink mFinishLink = new NetLink() {
		
		@Override
		public void receive(PeerConnection b, byte[] data, int seq) {
		}
		
		@Override
		public void chainning(PeerConnection b, int seq) {
			ChainerLink.this.result(true);
		}
	};

	private NetLink mInitLink = new NetLink() {
		
		@Override
		public void receive(PeerConnection b, byte[] data, int seq) {
		}
		
		@Override
		public void chainning(PeerConnection b, int seq) {
			b.getEnviroment().putAll(mParentConnection.getEnviroment());
			mSubChainer.startNet(mSubNetwork);
		}
	};

	public ChainerLink(Chainer subChainer, Network network) {
		mSubChainer = subChainer;
		mSubNetwork = network;

		mSubNetwork.changeHandle(mInitLink);
		mSubChainer.addChain(mFinishLink);
	}

	@Override
	public void receive(PeerConnection b, byte[] data, int seq) {
	}

	@Override
	public void chainning(PeerConnection b, int seq) {
		mParentConnection = b;
		mSubNetwork.start();
	}
}