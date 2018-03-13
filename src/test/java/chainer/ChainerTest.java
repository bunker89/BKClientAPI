package chainer;

import org.junit.Before;
import org.junit.Test;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.link.Chainer;
import com.bunker.bkframework.clientapi.link.NetLink;

import network.TestNetwork;

public class ChainerTest {
	private Chainer<byte[], byte[]> chainer;

	@Before
	public void setUp() throws Exception {
		chainer = new Chainer<byte[], byte[]>(true);
	}

	@Test
	public void test() {
		chainer.addChain(new NetLink<byte[], byte[]>() {
			@Override
			public void receive(PeerConnection<byte[]> b, byte[] data, int seq) {
			}

			@Override
			public void chainning(PeerConnection<byte[]> b, int seq) {
				System.out.println("ChainerTest:test:innorHaindleChain1:chainning");
				result(true);
			}
		});

		chainer.addChain(new NetLink<byte[], byte[]>() {
			@Override
			public void receive(PeerConnection<byte[]> b, byte[] data, int seq) {
			}

			@Override
			public void chainning(PeerConnection<byte[]> b, int seq) {
				System.out.println("ChainerTest:test:innorHaindleChain2:chainning");
				result(true);
			}
		});

		System.out.println("start");
		chainer.startNet(new TestNetwork());
		try {
			Thread.sleep(6000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		chainer.addChain(new NetLink<byte[], byte[]>() {
			@Override
			public void receive(PeerConnection<byte[]> b, byte[] data, int seq) {
			}

			@Override
			public void chainning(PeerConnection<byte[]> b, int seq) {
				System.out.println("ChainerTest:test:innorHaindleChain3:chainning");
				result(true);
			}
		});
		try {
			Thread.sleep(10000000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}