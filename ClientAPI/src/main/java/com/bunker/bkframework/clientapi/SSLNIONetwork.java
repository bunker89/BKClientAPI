package com.bunker.bkframework.clientapi;
import java.io.IOException;
import java.nio.ByteBuffer;

import com.bunker.bkframework.business.PeerConnection;
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
public class SSLNIONetwork extends NIONetwork implements HandShakeCallback {
	private PeerNIOClient mClient;
	private boolean mHandshaked = false;
	private SecureFactory<ByteBuffer> mSecFac;
	private PeerConnection mConnection;

	public SSLNIONetwork(SecureFactory<ByteBuffer> secFac, NetHandle handle, String url, int port) {
		super(handle, url, port);
		mSecFac = secFac;
	}

	@Override
	public void established(PeerConnection b) {
		mConnection = b;
	}

	@Override
	public void handshaked() {
		mHandshaked = true;
		super.established(mConnection);
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
		return super.getPeerConnection();
	}

	public void setWriteBufferSizeKB(int size) {
		mClient.setWriterBufferSize(size);
	}

	@Override
	protected PeerNIOClient createPeer() {
		mClient = new PeerNIOClient(mSecFac,
				this,
				getUrl(),
				getPort());
		mClient.setHandshakeCallback(this);
		return mClient;
	}
}