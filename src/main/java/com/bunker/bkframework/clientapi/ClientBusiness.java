package com.bunker.bkframework.clientapi;

import com.bunker.bkframework.business.Business;
import com.bunker.bkframework.business.PeerConnection;

public abstract class ClientBusiness <PacketType, SendDataType, ReceiveDataType> implements Network<SendDataType, ReceiveDataType>
, Business<PacketType, SendDataType, ReceiveDataType> {
	private NetHandle<SendDataType, ReceiveDataType> mHandle;
	private int mSeq = 1000;
	private PeerConnection<SendDataType> mConnector;
	
	protected ClientBusiness() {
	}

	public ClientBusiness(NetHandle<SendDataType, ReceiveDataType> handle) {
		mHandle = handle;
	}

	@Override
	public void established(PeerConnection<SendDataType> b) {
		mConnector = b;
		mHandle.chainning(b, mSeq++);
	}

	@Override
	public void receive(PeerConnection<SendDataType> connector, ReceiveDataType data, int sequence) {
		mHandle.receive(connector, data, sequence);
	}

	@Override
	public void removeBusinessData(PeerConnection<SendDataType> connector) {
		if (mHandle != null)
			mHandle.broken();
		mHandle = null;
	}

	@Override
	public void changeHandle(NetHandle<SendDataType, ReceiveDataType> handle) {
		mHandle = handle;
	}

	@Override
	public int getNextSequence() {
		return mSeq++;
	}

	@Override
	public PeerConnection<SendDataType> getPeerConnection() {
		return mConnector;
	}
}
