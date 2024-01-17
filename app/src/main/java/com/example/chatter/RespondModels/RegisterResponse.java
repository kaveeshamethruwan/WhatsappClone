package com.example.chatter.RespondModels;

import com.example.chatter.Models.User;

public class RegisterResponse {

    private long code;
    private String message;
    private User data;

    public RegisterResponse() {
    }

    public RegisterResponse(long code, String message, User data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public long getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public User getData() {
        return data;
    }
}
