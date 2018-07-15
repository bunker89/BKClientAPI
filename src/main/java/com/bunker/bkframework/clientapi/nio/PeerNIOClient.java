package com.bunker.bkframework.clientapi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.SocketChannel;

import com.bunker.bkframework.business.Business;
import com.bunker.bkframework.business.BusinessPeer;
import com.bunker.bkframework.clientapi.HandShakeCallback;
import com.bunker.bkframework.newframework.Constants;
import com.bunker.bkframework.newframework.LifeCycle;
import com.bunker.bkframework.newframework.Logger;
import com.bunker.bkframework.newframework.Peer;
import com.bunker.bkframework.newframework.PeerLife;
import com.bunker.bkframework.newframework.Resource;
import com.bunker.bkframework.newframework.Writer;
import com.bunker.bkframework.nio.ByteBufferBusinessConnector;
import com.bunker.bkframework.nio.FixedSizeByteBufferPacketFactory;
import com.bunker.bkframework.nio.NIOWriter;
import com.bunker.bkframework.sec.SecureFactory;

/**
 * 占쏙옙占쏙옙占쏙옙 占싣댐옙 클占쏙옙占싱억옙트占쏙옙占쏙옙 占쏙옙占쏙옙占� 클占쏙옙占싱억옙트 api
 * Copyright 2016~ by bunker Corp.,
 * All rights reserved.
 *
 * @author Young soo Ahn <bunker.ys89@gmail.com>
 * 2016. 6. 28.
 *
 *
 */
public class PeerNIOClient extends BusinessPeer<ByteBuffer, byte[], byte[]> implements LifeCycle, Resource<ByteBuffer> {
	private SocketChannel mChannel;
	private String mInetAddr;
	private int mPort;
	private HandShakeCallback mCallback;
	private String mName = "Unknown";
	private int mSize;

	public PeerNIOClient(Business<ByteBuffer, byte[], byte[]> business, String inetAddr, int port) {
		this(null, business, inetAddr, port);
	}

	public PeerNIOClient(SecureFactory<ByteBuffer> secFactory, Business<ByteBuffer, byte[], byte[]> business, String inetAddr, int port) {
		super(new FixedSizeByteBufferPacketFactory(), secFactory, new ByteBufferBusinessConnector(business));
		setLifeCycle(this);
		mInetAddr = inetAddr;
		mPort = port;
	}

	public void setHandshakeCallback(HandShakeCallback callback) {
		mCallback = callback;
	}

	public void write(ByteBuffer b) {
		mWriter.write(b, null);
	}

	public void write(ByteBuffer b, Integer seq) {
		mWriter.write(b, seq);
	}

	@Override
	public void manageLife(PeerLife life) {
		if (!startNetwork())
			return;
		networkInited(this);
		while (true) {
			ByteBuffer b = getReadBuffer();
			try {
				int readSize =  0;
				while (readSize != Constants.PACKET_DEFAULT_TOTAL_SIZE) {
					int current = mChannel.read(b);
					readSize += current;
					if (current < 0) {
						close();
						return;
					}
				}
				b.flip();
				dispatch(b);
			} catch (AsynchronousCloseException e) {
				Logger.logging("PeerNIOClient", "peer async closed:" + mName);
			} catch (IOException e) {
				Logger.logging("PeerNIOClient", "connection closed:" + mName);
				return;
			}
			try {
				life.life();
			} catch (Exception e) {
				e.printStackTrace();
			}
			b.clear();
		}
	}

	private boolean startNetwork() {
		try {
			InetSocketAddress hostAddress = new InetSocketAddress(mInetAddr, mPort);
			mChannel = SocketChannel.open(hostAddress);
			mChannel.configureBlocking(true);
			Writer<ByteBuffer> writer = new NIOWriter(mChannel, null);
			if (mSize > 0)
				writer.setWriteBufferSize(mSize);
			setWriter(writer);
		} catch (Exception e) {
			e.printStackTrace();
			close();
			return false;
		}
		return true;
	}

	public void setName(String name) {
		mName = name;
	}

	@Override
	public Peer<ByteBuffer> getPeer() {
		return this;
	}

	@Override
	public final ByteBuffer getReadBuffer() {
		ByteBuffer b = ByteBuffer.allocate(Constants.PACKET_DEFAULT_TOTAL_SIZE);
		return b;
	}

	/**
	 * 占쏙옙占쌀쏙옙占쏙옙 占쏙옙트占쏙옙占쏙옙
	 */
	@Override
	public void destroy() {
	}

	@Override
	public void handShaked() {
		super.handShaked();
		if (mCallback != null)
			mCallback.handshaked();
	}

	public void setWriterBufferSize(int sizeKB) {
		mSize = sizeKB;
	}

	public SocketChannel getSocket() {
		return mChannel;
	}

	@Override
	public String getClientHostInfo() {
		return null;
	}
}