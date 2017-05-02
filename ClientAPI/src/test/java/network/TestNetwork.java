package network;
import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.NetHandle;
import com.bunker.bkframework.clientapi.Network;

public class TestNetwork implements Network {
	private NetHandle mHandle;
	private PeerConnection mConnection = new TestConnection();

	@Override
	public void start() {
		System.out.println("TestNetwork:start");
		if (mHandle != null)
			mHandle.chainning(mConnection, 1);
	}

	@Override
	public void changeHandle(NetHandle handle) {
		mHandle = handle;
	}

	@Override
	public int getNextSequence() {
		return 0;
	}

	@Override
	public PeerConnection getPeerConnection() {
		return mConnection;
	}
}
