package network;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.bunker.bkframework.business.PeerConnection;

public class TestConnection implements PeerConnection {
	private Map<String, Object> mEnviroment = new HashMap<>();
	@Override
	public void closePeer() {
		System.out.println("TestConnection:closepeer");
	}

	@Override
	public Map<String, Object> getEnviroment() {
		return mEnviroment;
	}

	@Override
	public void sendToPeer(byte[] arg0, int arg1) {
		System.out.println("TestConnection:sendToPeer:" + new String(arg0));
	}
}
