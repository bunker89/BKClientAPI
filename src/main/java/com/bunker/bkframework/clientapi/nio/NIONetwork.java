package com.bunker.bkframework.clientapi.nio;

import java.nio.ByteBuffer;

import com.bunker.bkframework.business.Business;
import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.NetHandle;
import com.bunker.bkframework.clientapi.Network;

public class NIONetwork implements Network<byte[], byte[]>, Business<ByteBuffer, byte[], byte[]> {
	private NetHandle<byte[], byte[]> mHandle;
	private int mSeq = 1000;
	private PeerConnection<byte[]> mConnector;
	private Thread mThread;
	private PeerNIOClient mClient;
	private String mUrl;
	private int mPort;
	
	protected NIONetwork() {
		
	}

	public NIONetwork(NetHandle<byte[], byte[]> handle, String url, int port) {
		mHandle = handle;
		mUrl = url;
		mPort = port;
	}

	@Override
	public void established(PeerConnection<byte[]> b) {
		mConnector = b;
		mHandle.chainning(b, mSeq++);
	}

	@Override
	public void receive(PeerConnection<byte[]> connector, byte[] data, int sequence) {
		mHandle.receive(connector, data, sequence);
	}

	@Override
	public void removeBusinessData(PeerConnection<byte[]> connector) {
		mHandle.broken();
	}

	@Override
	synchronized public void start() {
		if (mThread != null)
			return;

		mClient = createPeer();
		mThread = new Thread(mClient);
		mThread.start();
	}

	@Override
	public void changeHandle(NetHandle<byte[], byte[]> handle) {
		mHandle = handle;
	}

	@Override
	public int getNextSequence() {
		return mSeq++;
	}

	@Override
	public PeerConnection<byte[]> getPeerConnection() {
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

	public String getUrl() {
		return mUrl;
	}

	public int getPort() {
		return mPort;
	}
}
