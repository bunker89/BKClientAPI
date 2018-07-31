package link;

import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.JSONAdapter;
import com.bunker.bkframework.clientapi.link.NetLink.OnLinkResultListener;

public class TestLink extends JSONAdapter {

	public TestLink() {
		// TODO Auto-generated constructor stub
	}
	
	public TestLink(OnLinkResultListener listener, String key) {
		setOnLinkResultListener(listener, key);
	}

	@Override
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		return json;
	}

	@Override
	public void receiveJSON(boolean result, JSONObject json) {
		System.out.println(json);
	}
}