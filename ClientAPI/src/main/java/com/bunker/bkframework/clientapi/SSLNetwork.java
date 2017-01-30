package com.bunker.bkframework.clientapi;
import java.nio.ByteBuffer;

import com.bunker.bkframework.business.Business;
import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.sec.SSLSecureFactory;

/**
 * Connection Less
 * 
 * Copyright 2016~ by bunker Corp.,
 * All rights reserved.
 *
 * @author Young soo Ahn <bunker.ys89@gmail.com>
 * 2016. 11. 9.
 *
 *
 */
public class SSLNetwork implements Business<ByteBuffer>, HandShakeCallback, Network {
	private NetHandle mHandle;
	private int mSeq = 1000;
	private PeerConnection mConnector;
	private Thread mThread;
	private PeerNIOClient mClient;

	public SSLNetwork(NetHandle handle, String url, int port) {
		mHandle = handle;
		mClient = new PeerNIOClient(new SSLSecureFactory("client.keystore", "server", "client.keystore", "server", "client", 1),
				this,
				url,
				port);
		mClient.setHandshakeCallback(this);
	}

	@Override
	public void established(PeerConnection b) {
		mConnector = b;
	}

	@Override
	public void receive(PeerConnection connector, byte[] data, int sequence) {
		mHandle.receive(connector, data);
	}

	@Override
	public void removeBusinessData(PeerConnection connector) {
		mHandle.broken();
	}

	@Override
	public void handshaked() {
//		System.out.println("handshake");
		mHandle.chainning(mConnector, mSeq++);
	}

	@Override
	public void start() {
		mThread = new Thread(mClient);
		mThread.start();
	}

	@Override
	public void changeHandle(NetHandle handle) {
		mHandle = handle;
	}

	@Override
	public int getNextSequence() {
		return mSeq++;
	}

	@Override
	public PeerConnection getPeerConnection() {
		return mConnector;
	}
}