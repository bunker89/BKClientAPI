package com.bunker.bkframework.clientapi.http;

import java.util.Map;

import com.bunker.bkframework.business.PeerConnection;

public class HttpConnection implements PeerConnection<String> {

	@Override
	public void closePeer() {
	}

	@Override
	public Map<String, Object> getEnviroment() {
		return null;
	}

	@Override
	public void sendToPeer(String arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

}
