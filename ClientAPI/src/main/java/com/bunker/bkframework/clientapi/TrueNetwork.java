package com.bunker.bkframework.clientapi;

import java.util.HashMap;
import java.util.Map;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.newframework.Logger;

public class TrueNetwork implements Network, PeerConnection {
	private NetHandle mHandle;
	private boolean mIsStarted;
	private Map<String, Object> mEnv = new HashMap<>();

	@Override
	public void start() {
		mIsStarted = true;
		if (mHandle != null)
			mHandle.chainning(this, 0);
	}

	@Override
	public void changeHandle(NetHandle handle) {
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
	public PeerConnection getPeerConnection() {
		return this;
	}

	@Override
	public void closePeer() {
		mIsStarted = false;
	}

	@Override
	public Map<String, Object> getEnviroment() {
		return mEnv;
	}

	@Override
	public void sendToPeer(byte[] arg0, int arg1) {
		Logger.logging("TrueNetwork", new String(arg0));
	}
}
