package com.bunker.bkframework.clientapi.link;

import org.json.JSONException;
import org.json.JSONObject;

import com.bunker.bkframework.business.PeerConnection;

/**
 * Created by WIN7 on 10�� 20�� 0020.
 */

abstract public class WorkLink extends NetLink<byte[], byte[]> implements JSONLink {

    @Override
    final public void receive(PeerConnection<byte[]> peerConnection, byte[] bytes, int i) {
        try {
            JSONObject json = new JSONObject(new String(bytes));
            boolean result = json.getBoolean("result");
            if (result)
                receiveJSON(result, json);
            result(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    final public void chainning(PeerConnection<byte[]> peerConnection, int index) {
        JSONObject json = getJson();
        try {
            json.put("working", getWork());
            peerConnection.sendToPeer(json.toString().getBytes(), index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}