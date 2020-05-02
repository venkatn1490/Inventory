package com.medrep.app.util;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.medrep.app.model.ChatMessages;

public class MessageDecoder implements Decoder.Text<ChatMessages> {

    private static Gson gson = new Gson();

    @Override
    public ChatMessages decode(String s) throws DecodeException {
    	ChatMessages message = gson.fromJson(s, ChatMessages.class);
        return message;
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
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
