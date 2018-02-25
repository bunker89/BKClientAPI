package com.bunker.bkframework.clientapi.link;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.Network;

public class ChainerLink<MainSend, MainReceive, SubSend, SubReceive> extends NetLink<MainSend, MainReceive> {
	private Chainer<SubSend, SubReceive> mSubChainer;
	private Network<SubSend, SubReceive> mSubNetwork;
	private PeerConnection<MainSend> mParentConnection;

	private NetLink<SubSend, SubReceive> mFinishLink = new NetLink<SubSend, SubReceive>() {
		
		@Override
		public void receive(PeerConnection<SubSend> b, SubReceive data, int seq) {
		}
		
		@Override
		public void chainning(PeerConnection<SubSend> b, int seq) {
			ChainerLink.this.result(true);
		}
	};

	private NetLink<SubSend, SubReceive> mInitLink = new NetLink<SubSend, SubReceive>() {
		
		@Override
		public void receive(PeerConnection<SubSend> b, SubReceive data, int seq) {
		}
		
		@Override
		public void chainning(PeerConnection<SubSend> b, int seq) {
			b.getEnviroment().putAll(mParentConnection.getEnviroment());
			mSubChainer.startNet(mSubNetwork);
		}
	};

	public ChainerLink(Chainer<SubSend, SubReceive> subChainer, Network<SubSend, SubReceive> subNetwork) {
		mSubChainer = subChainer;
		mSubNetwork = subNetwork;

		mSubNetwork.changeHandle(mInitLink);
		mSubChainer.addChain(mFinishLink);
	}

	@Override
	public void receive(PeerConnection<MainSend> b, MainReceive data, int seq) {
	}

	@Override
	public void chainning(PeerConnection<MainSend> b, int seq) {
		mParentConnection = b;
		mSubNetwork.start();
	}
}