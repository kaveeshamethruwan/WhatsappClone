package com.example.chatter.RespondModels;

import com.example.chatter.Models.MessageData;

public class MessageResponse {

    private int code;
    private String message;
    private MessageData data;

    public MessageResponse() {

    }

    public MessageResponse(int code, String message, MessageData data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public MessageData getData() {
        return data;
    }
}
