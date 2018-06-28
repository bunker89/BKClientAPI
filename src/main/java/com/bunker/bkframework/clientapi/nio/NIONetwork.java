package com.bunker.bkframework.clientapi.nio;

import java.nio.ByteBuffer;

import com.bunker.bkframework.clientapi.ClientBusiness;
import com.bunker.bkframework.clientapi.NetHandle;

public class NIONetwork extends ClientBusiness<ByteBuffer, byte[], byte[]> {
	private Thread mThread;
	private PeerNIOClient mClient;
	private String mUrl;
	private int mPort;
	
	protected NIONetwork() {
	}

	public NIONetwork(NetHandle<byte[], byte[]> handle, String url, int port) {
		super(handle);
		mUrl = url;
		mPort = port;
	}

	@Override
	synchronized public void start() {
		if (mThread != null)
			return;

		mClient = createPeer();
		mThread = new Thread(mClient);
		mThread.start();
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
