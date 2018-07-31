package com.bunker.bkframework.clientapi.text;

import com.bunker.bkframework.clientapi.link.Chainer;
import com.bunker.bkframework.clientapi.link.JSONAdapterChainer;
import com.bunker.bkframework.clientapi.link.JSONAdapter;

public class TextChainer extends JSONAdapterChainer<String, String> {
	public TextChainer() {
		super();
	}

	public TextChainer(boolean connectionOriented) {
		super(connectionOriented);
	}

	public TextChainer(Chainer<String, String> parent) {
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
