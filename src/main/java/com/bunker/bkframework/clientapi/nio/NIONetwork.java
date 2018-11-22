package com.bunker.bkframework.clientapi.nio;

import java.nio.ByteBuffer;

import com.bunker.bkframework.clientapi.ClientBusiness;
import com.bunker.bkframework.clientapi.NetHandle;
import com.bunker.bkframework.newframework.Logger;
import com.bunker.bkframework.clientapi.link.bytes.BytesChainer;

public class NIONetwork extends ClientBusiness<ByteBuffer, byte[], byte[]> {
	private Thread mThread;
	private PeerNIOClient mClient;
	private String mUrl;
	private int mPort;
	public static final ThreadGroup mBKClientThreads = new ThreadGroup("BKClient-TG");
	private boolean mDeactiveThreadLimit = false;
	private int mActiveThreadThreshold = 10;
	private String mName;

	public NIONetwork(String name, NetHandle<byte[], byte[]> handle, String url, int port) {
		super(handle);
		mName = name;
		mUrl = url;
		mPort = port;
	}
	
	public void setThreadLimit(boolean bool) {
		mDeactiveThreadLimit = bool;
	}
	
	public void setActiveThreadLimit(int limit) {
		mActiveThreadThreshold = limit;
	}

	@Override
	synchronized public void start() {
		if (mThread != null && mThread.isAlive())
			mThread.interrupt();

		mClient = createPeer();
		mThread = new Thread(mBKClientThreads, mClient);
		mThread.setName(mName);
		mThread.start();
		//만들어진 경로에 대한 로깅이 필요함.
		if (!mDeactiveThreadLimit && mBKClientThreads.activeCount() > mActiveThreadThreshold) {
			Thread[] threads = new Thread[mBKClientThreads.activeCount()];
			Thread.enumerate(threads);
			
			String names = "";
			for (Thread th : threads) {
				names += th.getName() + ",";
			}
			Logger.err("NIONetwork", "active thread overflow names:" + names);
		}
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
	
	public BytesChainer getSerialChainer(boolean oriented) {
		BytesChainer chainer = new BytesChainer();
		return chainer;
	}
}
