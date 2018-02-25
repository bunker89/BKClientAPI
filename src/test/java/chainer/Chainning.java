package chainer;

import org.junit.Before;
import org.junit.Test;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.Network;
import com.bunker.bkframework.clientapi.TrueNetwork;
import com.bunker.bkframework.clientapi.link.Chainer;
import com.bunker.bkframework.clientapi.link.ChainerLink;
import com.bunker.bkframework.clientapi.link.NetClearLink;
import com.bunker.bkframework.clientapi.link.NetLink;

public class Chainning {
	private NetLink<byte[], byte[]> mTestLink = new NetLink<byte[], byte[]>() {
		private int i = 0;
		
		@Override
		public void receive(PeerConnection<byte[]> b, byte[] data, int seq) {
			
		}
		
		@Override
		public void chainning(PeerConnection<byte[]> b, int seq) {
			System.out.println("test" + i + ":" + b.getEnviroment());
			i++;
			b.getEnviroment().put("test" + i, "tt");
			result(true);
		}
	};

	private NetLink<byte[], byte[]> mSubLink = new NetLink<byte[], byte[]>() {
		private int i = 0;

		@Override
		public void receive(PeerConnection<byte[]> b, byte[] data, int seq) {
		}

		@Override
		public void chainning(PeerConnection<byte[]> b, int seq) {
			System.out.println("sub" + i + ":" + b.getEnviroment());
			i++;
			result(true);
			b.getEnviroment().put("sub" + i, "ss");
		}
	};

	private Network<byte[], byte[]> mNetwork;

	@Before
	public void setUp() throws Exception {
		mNetwork = new TrueNetwork<byte[], byte[]>();
	}

	@Test
	public void subChainTest() {
		System.out.println("------subChainTest-----");
		Chainer<byte[], byte[]> chainer = new Chainer<byte[], byte[]>();
		chainer.addChain(mTestLink);
		chainer.addChain(mTestLink);

		Network<byte[], byte[]> subNet = new TrueNetwork<byte[], byte[]>();
		Chainer<byte[], byte[]> subChain = new Chainer<byte[], byte[]>(chainer);
		subChain.addChain(new ChainerLink<byte[], byte[], byte[], byte[]>(subChain, subNet));
		subChain.addChain(mSubLink);
		subChain.addChain(mSubLink);
		
		chainer.addChain(mTestLink);
		chainer.startNet(mNetwork);
	}

	@Test
	public void networkChange() {
		System.out.println("------networkChange-----");
		Chainer<byte[], byte[]> chainer = new Chainer<byte[], byte[]>();
		chainer.addChain(new NetClearLink<byte[], byte[]>(chainer));
		chainer.startNet(new TrueNetwork<byte[], byte[]>());
	}
}