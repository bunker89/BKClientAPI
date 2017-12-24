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
	private NetLink mTestLink = new NetLink() {
		private int i = 0;
		
		@Override
		public void receive(PeerConnection b, byte[] data, int seq) {
			
		}
		
		@Override
		public void chainning(PeerConnection b, int seq) {
			System.out.println("test" + i + ":" + b.getEnviroment());
			i++;
			b.getEnviroment().put("test" + i, "tt");
			result(true);
		}
	};

	private NetLink mSubLink = new NetLink() {
		private int i = 0;

		@Override
		public void receive(PeerConnection b, byte[] data, int seq) {
		}

		@Override
		public void chainning(PeerConnection b, int seq) {
			System.out.println("sub" + i + ":" + b.getEnviroment());
			i++;
			result(true);
			b.getEnviroment().put("sub" + i, "ss");
		}
	};

	private Network mNetwork;

	@Before
	public void setUp() throws Exception {
		mNetwork = new TrueNetwork();
	}

	@Test
	public void subChainTest() {
		System.out.println("------subChainTest-----");
		Chainer chainer = new Chainer();
		chainer.addChain(mTestLink);
		chainer.addChain(mTestLink);

		Network subNet = new TrueNetwork();
		Chainer subChain = new Chainer(chainer);
		subChain.addChain(new ChainerLink(subChain, subNet));
		subChain.addChain(mSubLink);
		subChain.addChain(mSubLink);
		
		chainer.addChain(mTestLink);
		chainer.startNet(mNetwork);
	}

	@Test
	public void networkChange() {
		System.out.println("------networkChange-----");
		Chainer chainer = new Chainer();
		chainer.addChain(new NetClearLink(chainer));
		chainer.startNet(new TrueNetwork());
	}
}