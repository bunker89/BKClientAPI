package test;

import org.junit.Test;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.link.NetLink;
import com.bunker.bkframework.clientapi.link.bytes.BytesChainer;
import com.bunker.bkframework.clientapi.nio.NIONetwork;

public class ConnectServer {
	private int a = 0;
	
	class ReconectLink extends NetLink<byte[], byte[]> {

		@Override
		public void receive(PeerConnection<byte[]> b, byte[] data, int seq) {
		}

		@Override
		public void chainning(PeerConnection<byte[]> b, int seq) {
		}		
	}
	
	@Test public void test() {
		BytesChainer chainer = new BytesChainer(true);
		NIONetwork network = new NIONetwork("test", null, "127.0.0.1", 1111);
		chainer.startNet(network);

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		chainer.addChain(new ReconectLink());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(network.mBKClientThreads.activeCount());
	}
}
