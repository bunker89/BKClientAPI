package com.bunker.bkframework.clientapi.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

import com.bunker.bkframework.newframework.Writer;

public class HttpWriter implements Writer<HttpRequestBase> {
	private HttpClient mClient;
	
	public HttpWriter(HttpClient client, HttpResponse response) {
		mClient = client;
	}

	@Override
	public void destroy() {
	}

	@Override
	public void setWriteBufferSize(int arg0) {
	}

	@Override
	public Writer<HttpRequestBase> unDecoWriter() {
		return null;
	}

	@Override
	public void write(HttpRequestBase arg0) {
	}

	@Override
	public void write(HttpRequestBase arg0, Integer arg1) {
	}
}
