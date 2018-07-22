package com.bunker.bkframework.clientapi;

import java.util.HashMap;
import java.util.Map;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.newframework.Logger;

public class TrueNetwork<SendDataType, ReceiveDataTye> implements Network<SendDataType, ReceiveDataTye>, PeerConnection<SendDataType> {
	private NetHandle<SendDataType, ReceiveDataTye> mHandle;
	private boolean mIsStarted;
	private Map<String, Object> mEnv = new HashMap<>();

	@Override
	public void start() {
		mIsStarted = true;
		if (mHandle != null)
			mHandle.chainning(this, 0);
	}

	@Override
	public void changeHandle(NetHandle<SendDataType, ReceiveDataTye> handle) {
		mHandle = handle;
		if (mIsStarted) {
			handle.chainning(this, 0);
		}
	}

	@Override
	public int getNextSequence() {
		return 0;
	}

	@Override
	public PeerConnection<SendDataType> getPeerConnection() {
		return this;
	}

	@Override
	public void closePeer() {
		mIsStarted = false;
		Logger.logging("TrueNetwork", "connection closed");
	}

	@Override
	public Map<String, Object> getEnviroment() {
		return mEnv;
	}

	@Override
	public void sendToPeer(SendDataType arg0, int arg1) {
		Logger.logging("TrueNetwork", arg0.toString());
	}
	
	public void sendToBusiness(ReceiveDataTye data) {
		mHandle.receive(this, data, 0);
	}
}
