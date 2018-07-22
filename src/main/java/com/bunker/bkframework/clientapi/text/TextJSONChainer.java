package com.bunker.bkframework.clientapi.text;

import com.bunker.bkframework.clientapi.link.Chainer;
import com.bunker.bkframework.clientapi.link.JSONAdapter;
import com.bunker.bkframework.clientapi.link.JSONAdapterChainer;

public class TextJSONChainer extends JSONAdapterChainer<String, String> {
	public TextJSONChainer() {
		super();
	}

	public TextJSONChainer(boolean connectionOriented) {
		super(connectionOriented);
	}

	public TextJSONChainer(Chainer<String, String> parent) {
		super(parent);
	}

	@Override
	public void addChain(JSONAdapter adapter) {
		TextJSONLink link = new TextJSONLink(adapter);
		addChain(link);
	}
	
	public void addStaticChain(JSONAdapter json) {
		super.addStaticChain(new TextJSONLink(json));
	}
}
