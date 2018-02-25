package network;
import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.NetHandle;
import com.bunker.bkframework.clientapi.Network;

public class TestNetwork implements Network<byte[], byte[]> {
	private NetHandle<byte[], byte[]> mHandle;
	private PeerConnection<byte[]> mConnection = new TestConnection();

	@Override
	public void start() {
		System.out.println("TestNetwork:start");
		if (mHandle != null)
			mHandle.chainning(mConnection, 1);
	}

	@Override
	public void changeHandle(NetHandle<byte[], byte[]> handle) {
		mHandle = handle;
	}

	@Override
	public int getNextSequence() {
		return 0;
	}

	@Override
	public PeerConnection<byte[]> getPeerConnection() {
		return mConnection;
	}
}
