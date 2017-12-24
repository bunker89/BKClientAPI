package test;

import java.nio.ByteBuffer;

import com.bunker.bkframework.business.Business;
import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.PeerNIOClient;
import com.bunker.bkframework.clientapi.link.NetLink;
import com.bunker.bkframework.newframework.Peer;

public class ConnectServer {
	public static void main(String []args) {
		PeerNIOClient peer = new PeerNIOClient(new Business<ByteBuffer>() {
			
			@Override
			public void removeBusinessData(PeerConnection arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void receive(PeerConnection arg0, byte[] arg1, int arg2) {
				System.out.println(new String(arg1));
			}
			
			@Override
			public void established(PeerConnection arg0) {
				arg0.sendToPeer("1".getBytes(), 0);
			}
		}, "127.0.0.1", 9011);
		peer.setWriterBufferSize(10);
		new Thread(peer).start();
	}
}
