package com.bunker.bkframework.clientapi.link;

import java.util.LinkedList;
import java.util.List;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.Network;
import com.bunker.bkframework.clientapi.link.NetLink.OnResultListener;
import com.bunker.bkframework.clientapi.transaction.TransactionManager;

/**
 * @author bunker89
 *
 */
public class Chainer<SendDataType, ReceiveDataType> implements OnResultListener {
	private List<NetLink<SendDataType, ReceiveDataType>> mChains = new LinkedList<>();
	private Network<SendDataType, ReceiveDataType> mNetwork;
	private boolean mConnectionOriented;
	private boolean mDummyHandling = false;
	private TransactionManager mTransactionManager;

	private NetLink<SendDataType, ReceiveDataType> dummy = new NetLink<SendDataType, ReceiveDataType>() {

		@Override
		public void receive(PeerConnection<SendDataType> b, ReceiveDataType data, int seq) {
		}

		@Override
		public void chainning(PeerConnection<SendDataType> b, int seq) {
			mDummyHandling = true;
		}
	};

	public Chainer() {
		this(false);
	}

	public Chainer(boolean connectionOriented) {
		mConnectionOriented = connectionOriented;
		mTransactionManager = new TransactionManager();
	}

	public Chainer(Chainer<SendDataType, ReceiveDataType> parent) {
		mConnectionOriented = false;
		mTransactionManager = parent.getTransactionManager();
	}

	public void startNet(Network<SendDataType, ReceiveDataType> network) {
		mNetwork = network;
		if (mChains.isEmpty())
			mChains.add(dummy);
		setLink();
		network.start();
	}

	synchronized public void addChain(NetLink<SendDataType, ReceiveDataType> chain) {
		mChains.add(chain);

		synchronized (mChains) {
			if (mDummyHandling) {
				mDummyHandling = false;
				AsyncDeamon.getInstance().addTask(new AsyncRun() {

					@Override
					public void run() {
						setNextChain();
					}

					@Override
					public String err() {
						return null;
					}
				});
			}			
		}
	}

	@Override
	public void result(boolean result) {
		if (result)
			setNextChain();

		else {
			synchronized (mChains) {
				for (NetLink<SendDataType, ReceiveDataType> chain : mChains) {
					chain.result(false);
				}
			}
		}
	}

	private NetLink<SendDataType, ReceiveDataType> setLink() {
		NetLink<SendDataType, ReceiveDataType> chain = mChains.remove(0);
		chain.setOnResultListener(this);
		chain.setMainChain();
		mNetwork.changeHandle(chain);
		return chain;
	}

	private void setNextChain() {
		synchronized (mChains) {

			if (mChains.size() > 0) {
				nextLink(setLink());
			} else if (mConnectionOriented) {
				dummy.chainning(mNetwork.getPeerConnection(), mNetwork.getNextSequence());
			} else {
				mNetwork.getPeerConnection().closePeer();			
			}
		}
	}

	private void nextLink(NetLink<SendDataType, ReceiveDataType> link) {
		if (mTransactionManager.isTransactioning())
			mTransactionManager.addTransaction(link.toTransaction());

		link.chainning(mNetwork.getPeerConnection(), mNetwork.getNextSequence());
	}

	private TransactionManager getTransactionManager() {
		return mTransactionManager;
	}

	public int getRemaingedLinkCount() {
		return mChains.size();
	}
}