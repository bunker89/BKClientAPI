package link;

import org.json.JSONObject;

import com.bunker.bkframework.clientapi.link.JSONAdapter;

public class TestLink extends JSONAdapter {

	@Override
	public JSONObject getJson() {
		JSONObject json = new JSONObject();
		return json;
	}

	@Override
	public void receiveJSON(boolean result, JSONObject json) {
		// TODO Auto-generated method stub
		
	}

}
