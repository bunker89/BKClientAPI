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
	private NetHandle mHandle;
	private PeerNIOClient mClient;
	private boolean mHandshaked = false;
	private SecureFactory<ByteBuffer> mSecFac;
	private String mUrl;
	private int mPort;

	public SSLNIONetwork(SecureFactory<ByteBuffer> secFac, NetHandle handle, String url, int port) {
		mHandle = handle;
		mSecFac = secFac;
		mUrl = url;
		mPort = port;
	}

	@Override
	public void established(PeerConnection b) {
		setPeerConnection(b);
	}

	@Override
	public void handshaked() {
		mHandshaked = true;
		mHandle.chainning(super.getPeerConnection(), getNextSequence());
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

	protected PeerNIOClient createPeer() {
		mClient = new PeerNIOClient(mSecFac,
				this,
				mUrl,
				mPort);
		mClient.setHandshakeCallback(this);
		return mClient;
	}
}