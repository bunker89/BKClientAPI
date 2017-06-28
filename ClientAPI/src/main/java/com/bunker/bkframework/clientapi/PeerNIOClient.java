package com.bunker.bkframework.clientapi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousCloseException;
import java.nio.channels.SocketChannel;

import com.bunker.bkframework.business.Business;
import com.bunker.bkframework.business.BusinessPeer;
import com.bunker.bkframework.business.ByteBufferBusinessConnector;
import com.bunker.bkframework.newframework.Constants;
import com.bunker.bkframework.newframework.FixedSizeByteBufferPacketFactory;
import com.bunker.bkframework.newframework.LifeCycle;
import com.bunker.bkframework.newframework.Logger;
import com.bunker.bkframework.newframework.NIOWriter;
import com.bunker.bkframework.newframework.Peer;
import com.bunker.bkframework.newframework.PeerLife;
import com.bunker.bkframework.newframework.Resource;
import com.bunker.bkframework.newframework.Writer;
import com.bunker.bkframework.sec.SecureFactory;

/**
 * 서버가 아닌 클라이언트에서 사용할 클라이언트 api
 * Copyright 2016~ by bunker Corp.,
 * All rights reserved.
 *
 * @author Young soo Ahn <bunker.ys89@gmail.com>
 * 2016. 6. 28.
 *
 *
 */
public class PeerNIOClient extends BusinessPeer<ByteBuffer> implements LifeCycle, Resource<ByteBuffer> {
	private SocketChannel channel;
	private String mInetAddr;
	private int mPort;
	private HandShakeCallback mCallback;
	private String mName = "Unknown";
	private int mSize;

	public PeerNIOClient(Business<ByteBuffer> business, String inetAddr, int port) {
		this(null, business, inetAddr, port);
	}

	public PeerNIOClient(SecureFactory<ByteBuffer> secFactory, Business<ByteBuffer> business, String inetAddr, int port) {
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
					int current = channel.read(b);
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
			life.life();
			b.clear();
		}
	}

	private boolean startNetwork() {
		try {
			InetSocketAddress hostAddress = new InetSocketAddress(mInetAddr, mPort);
			channel = SocketChannel.open(hostAddress);
			channel.configureBlocking(true);
			Writer<ByteBuffer> writer = new NIOWriter(channel, null);
			if (mSize > 0)
				writer.setWriteBufferSize(mSize);
			System.out.println(mSize + "," + channel.socket().getSendBufferSize());
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
	 * 리소스의 디스트로이
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
}