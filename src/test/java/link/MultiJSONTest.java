package link;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.bunker.bkframework.clientapi.TrueNetwork;
import com.bunker.bkframework.clientapi.link.MultiJSONLink;
import com.bunker.bkframework.clientapi.link.WorkConstants;
import com.bunker.bkframework.clientapi.link.ParamDecoLink;
import com.bunker.bkframework.clientapi.link.NetLink.OnLinkResultListener;
import com.bunker.bkframework.clientapi.link.bytes.BytesChainer;

public class MultiJSONTest implements OnLinkResultListener {

	@Test public void test() {
		MultiJSONLink link = new MultiJSONLink();
		link.addChain(new TestLink());
		link.addChain(new TestLink(this, "ab"));
		link.addChain(new ParamDecoLink(new TestLink(this, "abc")), "c");
		System.out.println(link.getJson());
		
		TrueNetwork<byte[], byte[]> network = new TrueNetwork<>();
		BytesChainer chainer = new BytesChainer();
		chainer.addChain(link);
		chainer.startNet(network);
		
		sendToBusinessStubData(network);
	}
	
	private void sendToBusinessStubData(TrueNetwork<byte[], byte[]> network) {
		JSONObject json = new JSONObject();
		json.put(WorkConstants.WORKING_RESULT, true);

		JSONObject resultOne = new JSONObject();
		resultOne.put(WorkConstants.WORKING_RESULT, true);
		resultOne.put("num", 1);
		
		JSONObject resultTwo = new JSONObject();
		resultTwo.put(WorkConstants.WORKING_RESULT, true);
		resultTwo.put("num", 2);

		JSONObject resultThree = new JSONObject();
		resultThree.put(WorkConstants.WORKING_RESULT, true);
		resultThree.put("num", 3);
		
		JSONArray array = new JSONArray();
		json.put(WorkConstants.MULTI_WORKING_RESULT_ARRAY, array);
		array.put(resultOne);
		array.put(resultTwo);
		array.put(resultThree);
		network.sendToBusiness(json.toString().getBytes());
	}

	@Override
	public void result(boolean result, String key, Object link) {
		System.out.println(key + "," + result);
	}
}