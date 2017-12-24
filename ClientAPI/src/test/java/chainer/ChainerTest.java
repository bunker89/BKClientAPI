package chainer;

import org.junit.Before;
import org.junit.Test;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.link.Chainer;
import com.bunker.bkframework.clientapi.link.NetLink;

import network.TestNetwork;

public class ChainerTest {
	private Chainer chainer;

	@Before
	public void setUp() throws Exception {
		chainer = new Chainer(true);
	}

	@Test
	public void test() {
		chainer.addChain(new NetLink() {
			@Override
			public void receive(PeerConnection b, byte[] data, int seq) {
			}

			@Override
			public void chainning(PeerConnection b, int seq) {
				System.out.println("ChainerTest:test:innorHaindleChain1:chainning");
				result(true);
			}
		});

		chainer.addChain(new NetLink() {
			@Override
			public void receive(PeerConnection b, byte[] data, int seq) {
			}

			@Override
			public void chainning(PeerConnection b, int seq) {
				System.out.println("ChainerTest:test:innorHaindleChain2:chainning");
				result(true);
			}
		});

		System.out.println("start");
		chainer.startNet(new TestNetwork());
		chainer.addChain(new NetLink() {
			@Override
			public void receive(PeerConnection b, byte[] data, int seq) {
			}

			@Override
			public void chainning(PeerConnection b, int seq) {
				System.out.println("ChainerTest:test:innorHaindleChain3:chainning");
				result(true);
			}
		});
	}
}