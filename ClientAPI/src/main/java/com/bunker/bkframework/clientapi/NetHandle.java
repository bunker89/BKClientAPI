package com.bunker.bkframework.clientapi;


import com.bunker.bkframework.business.PeerConnection;

public interface NetHandle {
	public void receive(PeerConnection b, byte []data);
	public void chainning(PeerConnection b, int seq);
	public void broken();
}