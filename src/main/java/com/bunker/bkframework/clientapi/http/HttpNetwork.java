package com.bunker.bkframework.clientapi.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.ClientBusiness;

import okhttp3.*;

public class HttpNetwork extends ClientBusiness<String, String, String> implements PeerConnection<String> {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private final String URL;

    private final Map<String, Object> environment = new HashMap<>();

    private final OkHttpClient client = new OkHttpClient();

    private int timeout = 10;
    
    public HttpNetwork(String url) {
    	this.URL = url;
    }

    public void setTimeout(int timeout) {
    	this.timeout = timeout;
    }

    private String post(String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();

        Call call = client.newCall(request);
        call.timeout().timeout(timeout, TimeUnit.SECONDS);

        try (Response response = call.execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }

    @Override
    public void start() {
        established(this);
    }

    @Override
    public void sendToPeer(String s, int i) {
        try {
            String result = post(s);
            receive(this, result, i);
        } catch (IOException e) {
            e.printStackTrace();
        	postError();
        }
    }
    
    public void postError() {
    }

    @Override
    public void closePeer() {

    }

    @Override
    public Map<String, Object> getEnviroment() {
        return environment;
    }
}