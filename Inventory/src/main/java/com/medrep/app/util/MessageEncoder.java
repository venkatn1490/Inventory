package com.medrep.app.util;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.medrep.app.model.ChatMessages;

public class MessageEncoder implements Encoder.Text<ChatMessages> {

    private static Gson gson = new Gson();

    @Override
    public String encode(ChatMessages message) throws EncodeException {
        String json = gson.toJson(message);
        return json;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
