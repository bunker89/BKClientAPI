package com.bunker.bkframework.clientapi.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.bunker.bkframework.business.PeerConnection;
import com.bunker.bkframework.clientapi.ClientBusiness;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpNetwork extends ClientBusiness<String, String, String> implements PeerConnection<String> {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    private final String URL;
    private Map<String, Object> environment = new HashMap<>();

    private OkHttpClient client = new OkHttpClient();
    
    public HttpNetwork(String url) {
    	this.URL = url;
    }

    private String post(String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
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