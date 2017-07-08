package com.bunker.bkframework.clientapi;

import java.nio.ByteBuffer;

import com.bunker.bkframework.business.Business;
import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.NetHandle;
import com.bunker.bkframework.clientapi.Network;
import com.bunker.bkframework.clientapi.PeerNIOClient;

public class NIONetwork implements Network, Business<ByteBuffer> {
	private NetHandle mHandle;
	private int mSeq = 1000;
	private PeerConnection mConnector;
	private Thread mThread;
	private PeerNIOClient mClient;

	public NIONetwork(NetHandle handle, String url, int port) {
		mHandle = handle;
		mClient = new PeerNIOClient(this,
				url,
				port);
	}

	@Override
	public void established(PeerConnection b) {
		mConnector = b;
		mHandle.chainning(b, mSeq++);
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
}
