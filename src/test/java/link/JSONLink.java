package link;

import org.json.JSONObject;
import org.junit.Test;

import com.bunker.bkframework.clientapi.TrueNetwork;
import com.bunker.bkframework.clientapi.link.JSONAdapter;
import com.bunker.bkframework.clientapi.link.NetLink.OnLinkResultListener;
import com.bunker.bkframework.clientapi.link.SingleLink;
import com.bunker.bkframework.clientapi.link.bytes.BytesJSONLink;

public class JSONLink implements OnLinkResultListener {
	@Test public void test() {
		TrueNetwork<byte[], byte[]> network = new TrueNetwork<>();
		BytesJSONLink link = new BytesJSONLink(new JSONAdapter() {
			
			@Override
			public void receiveJSON(boolean result, JSONObject json) {
				System.out.println(json);
			}

			@Override
			public JSONObject getJson() {
				JSONObject json = new JSONObject();
				return json;
			}
		});

		link.setOnLinkResultListener(this, "abc");

		network.changeHandle(new SingleLink<>(link));
		network.start();
		JSONObject resultJSON = new JSONObject();
		resultJSON.put("result", true);
		
		network.sendToBusiness(resultJSON.toString().getBytes());
	}

	@Override
	public void result(boolean result, String key, Object link) {
		System.out.println("listener");
	}
}