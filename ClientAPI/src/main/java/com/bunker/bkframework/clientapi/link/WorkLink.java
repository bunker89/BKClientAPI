package com.bunker.bkframework.clientapi.link;

import org.json.JSONException;
import org.json.JSONObject;

import com.bunker.bkframework.business.PeerConnection;

/**
 * Created by WIN7 on 10¿ù 20ÀÏ 0020.
 */

abstract public class WorkLink extends NetLink {

    @Override
    final public void receive(PeerConnection peerConnection, byte[] bytes, int i) {
        try {
            JSONObject json = new JSONObject(new String(bytes));
            boolean result = json.getBoolean("result");
            result(result);
            if (result)
                receiveJSON(result, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    final public void chainning(PeerConnection peerConnection, int index) {
        JSONObject json = getJson();
        try {
            json.put("working", getWork());
            peerConnection.sendToPeer(json.toString().getBytes(), index);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected abstract int getWork();
    protected abstract JSONObject getJson();
    protected abstract void receiveJSON(boolean result, JSONObject json);
}