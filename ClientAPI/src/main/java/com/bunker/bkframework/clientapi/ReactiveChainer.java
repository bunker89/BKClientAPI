package com.bunker.bkframework.clientapi;

import java.util.LinkedList;
import java.util.List;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.HandleChain.OnResultListener;

public class ReactiveChainer implements OnResultListener, NetHandle {
	private List<HandleChain> mChains = new LinkedList<>();
	private Network mNetwork;
	private boolean mConnectionOriented = false;
	private boolean mDummyHandling = false;
	private HandleChain dummy = new HandleChain() {

		@Override
		public void receive(PeerConnection b, byte[] data, int seq) {
		}

		@Override
		public void chainning(PeerConnection b, int seq) {
			mDummyHandling = true;
		}
	};

	public ReactiveChainer() {
	}

	public ReactiveChainer(boolean connectionOriented) {
		mConnectionOriented = connectionOriented;
	}

	public void startNet(Network network) {
		mNetwork = network;
		if (mChains.isEmpty())
			mChains.add(dummy);
		setChain();
		network.start();
	}

	synchronized public void addChain(HandleChain chain) {
		mChains.add(chain);
		if (mDummyHandling) {
			mDummyHandling = false;
			setNextChain();
		}
	}

	@Override
	public void result(boolean result) {
		if (result)
			setNextChain();

		else {
			synchronized (mChains) {
				for (HandleChain chain : mChains) {
					chain.result(false);
				}
			}
		}
	}

	private HandleChain setChain() {
		HandleChain chain = mChains.remove(0);
		chain.setOnResultListener(this);
		chain.setMainChain();
		mNetwork.changeHandle(chain);
		return chain;
	}

	private void setNextChain() {
		if (mChains.size() > 0) {
			HandleChain chain = setChain();
			chain.chainning(mNetwork.getPeerConnection(), mNetwork.getNextSequence());
		} else if (mConnectionOriented) {
			dummy.chainning(mNetwork.getPeerConnection(), mNetwork.getNextSequence());
		} else {
			mNetwork.getPeerConnection().closePeer();			
		}
	}

	@Override
	public void receive(PeerConnection b, byte[] data, int seq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chainning(PeerConnection b, int seq) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void broken() {
		// TODO Auto-generated method stub
		
	}
}