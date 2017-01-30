package com.bunker.bkframework.clientapi;

import java.util.LinkedList;
import java.util.List;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.HandleChain.OnResultListener;

public class Chainer implements OnResultListener {
	private List<HandleChain> mChains = new LinkedList<>();
	private Network mNetwork;
	private boolean mIsAlive = false;
	private final Object resultMutex = new Object();
	private HandleChain dummy = new HandleChain() {

		@Override
		public void receive(PeerConnection b, byte[] data) {
		}

		@Override
		public void chainning(PeerConnection b, int seq) {
		}
	};

	public Chainer() {
	}

	public Chainer(boolean alive) {
		mIsAlive = alive;
	}

	public void startNet(Network network) {
		mNetwork = network;
		setChain();
		network.start();
	}

	public void addChain(HandleChain chain) {
		mChains.add(chain);
	}

	@Override
	public void result(boolean result) {
		if (result)
			setNextChain();

		else {
			synchronized (resultMutex) {
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
		} else if (!mIsAlive) {
			mNetwork.changeHandle(dummy);
			mNetwork.getPeerConnection().closePeer();
		}
	}
}