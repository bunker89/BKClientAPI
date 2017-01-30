package com.bunker.bkframework.clientapi;

import com.bunker.bkframework.business.PeerConnection;

public interface Network {
	public void start();
	public void changeHandle(NetHandle handle);
	public int getNextSequence();
	public PeerConnection getPeerConnection();
}
