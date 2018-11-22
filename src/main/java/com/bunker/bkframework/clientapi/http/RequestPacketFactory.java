package com.bunker.bkframework.clientapi.http;

import java.io.OutputStream;

import com.bunker.bkframework.newframework.Packet;
import com.bunker.bkframework.newframework.PacketFactory;

public class RequestPacketFactory implements PacketFactory<HttpPacket> {

	@Override
	public Packet<HttpPacket> creatPacket(HttpPacket arg0) {
		return null;
	}

	@Override
	public Packet<HttpPacket> creatPacket(int arg0) {
		return null;
	}

	@Override
	public void logging(OutputStream arg0, HttpPacket arg1) {	
	}

	@Override
	public void logging(OutputStream arg0, Packet<HttpPacket> arg1) {
	}	
}
