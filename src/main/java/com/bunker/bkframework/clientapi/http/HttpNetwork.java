package com.bunker.bkframework.clientapi.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

import com.bunker.bkframework.clientapi.ClientBusiness;

public class HttpNetwork extends ClientBusiness<String, HttpRequestBase, HttpResponse> {
	public HttpNetwork(String addr) {
	}

	@Override
	public void start() {
	}
}