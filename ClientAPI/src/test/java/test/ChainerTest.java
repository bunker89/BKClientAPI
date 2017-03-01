package test;

import org.junit.Before;
import org.junit.Test;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.HandleChain;

import network.TestNetwork;

public class ChainerTest {
	private com.bunker.bkframework.clientapi.Chainer chainer;
	@Before
	public void setUp() throws Exception {
		chainer = new com.bunker.bkframework.clientapi.Chainer(true);
	}

	@Test
	public void test() {
		chainer.startNet(new TestNetwork());
		chainer.addChain(new HandleChain() {
			
			@Override
			public void receive(PeerConnection b, byte[] data, int seq) {
			}
			
			@Override
			public void chainning(PeerConnection b, int seq) {
				System.out.println("ChainerTest:test:innorHaindleChain:chainning");
			}
		});
	}
}