package com.bunker.bkframework.clientapi;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.bunker.bkframework.business.Business;
import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.sec.SSLSecureFactory;
import com.bunker.bkframework.sec.SecureFactory;

/**
 * 
 * Copyright 2016~ by bunker Corp.,
 * All rights reserved.
 *
 * @author Young soo Ahn <bunker.ys89@gmail.com>
 * 2016. 11. 9.
 *
 *
 */
public class SSLNIONetwork implements Business<ByteBuffer>, HandShakeCallback, Network {
	private NetHandle mHandle;
	private int mSeq = 1000;
	private PeerConnection mConnector;
	private Thread mThread;
	private PeerNIOClient mClient;
	private boolean mHandshaked = false;

	public SSLNIONetwork(NetHandle handle, String url, int port) {
		this("client.keystore", handle, url, port);
	}

	public SSLNIONetwork(String keyPath, NetHandle handle, String url, int port) {
		this(new SSLSecureFactory("client.keystore", "server", "client.keystore", "server", "client", 1), handle, url, port);
	}

	public SSLNIONetwork(SecureFactory<ByteBuffer> secFac, NetHandle handle, String url, int port) {
		mHandle = handle;
		mClient = new PeerNIOClient(secFac,
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
		mHandle.receive(connector, data, sequence);
	}

	@Override
	public void removeBusinessData(PeerConnection connector) {
		mHandle.broken();
	}

	@Override
	public void handshaked() {
		mHandshaked = true;
		mHandle.chainning(mConnector, getNextSequence());
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
	synchronized public int getNextSequence() {
		return mSeq++;
	}

	@Override
	public PeerConnection getPeerConnection() {
		if (!mHandshaked) {
			try {
				throw new IOException("ssl not handshaked");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return mConnector;
	}
	
	public void setWriteBufferSizeKB(int size) {
		mClient.setWriterBufferSize(size);
	}
}