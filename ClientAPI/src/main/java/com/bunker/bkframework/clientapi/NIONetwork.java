package com.bunker.bkframework.clientapi;

import java.nio.ByteBuffer;

import com.bunker.bkframework.business.Business;
import com.bunker.bkframework.business.PeerConnection;

public class NIONetwork implements Network, Business<ByteBuffer> {
	private NetHandle mHandle;
	private int mSeq = 1000;
	private PeerConnection mConnector;
	private Thread mThread;
	private PeerNIOClient mClient;
	private String mUrl;
	private int mPort;
	
	protected NIONetwork() {
		
	}

	public NIONetwork(NetHandle handle, String url, int port) {
		mHandle = handle;
		mUrl = url;
		mPort = port;
	}

	@Override
	public void established(PeerConnection b) {
		mConnector = b;
		mHandle.chainning(b, mSeq++);
	}

	protected void setPeerConnection(PeerConnection p) {
		mConnector = p;
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
	public void start() {
		mClient = createPeer();
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

	public void setWriteBufferSize(int sizeKB) {
		mClient.setWriterBufferSize(sizeKB);
	}

	protected PeerNIOClient createPeer() {
		return new PeerNIOClient(this,
				mUrl,
				mPort);
		
	}
}
