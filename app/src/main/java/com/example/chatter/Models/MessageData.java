package com.example.chatter.Models;

public class MessageData {

    private String MESSAGE_ID;
    private String SENDER;
    private String RECEIVER;
    private String BODY;
    private String SENT_TIME;
    private String RECEIVE_TIME;
    private String CHAT_THREAD;
    private String SEEN;
    private String MESSAGE_TYPE;
    private String LAST_UPDATE;

    public MessageData() {

    }

    public MessageData(String MESSAGE_ID, String SENDER, String RECEIVER, String BODY, String SENT_TIME, String RECEIVE_TIME, String CHAT_THREAD, String SEEN, String MESSAGE_TYPE, String LAST_UPDATE) {
        this.MESSAGE_ID = MESSAGE_ID;
        this.SENDER = SENDER;
        this.RECEIVER = RECEIVER;
        this.BODY = BODY;
        this.SENT_TIME = SENT_TIME;
        this.RECEIVE_TIME = RECEIVE_TIME;
        this.CHAT_THREAD = CHAT_THREAD;
        this.SEEN = SEEN;
        this.MESSAGE_TYPE = MESSAGE_TYPE;
        this.LAST_UPDATE = LAST_UPDATE;
    }

    public String getMESSAGE_ID() {
        return MESSAGE_ID;
    }

    public String getSENDER() {
        return SENDER;
    }

    public String getRECEIVER() {
        return RECEIVER;
    }

    public String getBODY() {
        return BODY;
    }

    public String getSENT_TIME() {
        return SENT_TIME;
    }

    public String getRECEIVE_TIME() {
        return RECEIVE_TIME;
    }

    public String getCHAT_THREAD() {
        return CHAT_THREAD;
    }

    public String getSEEN() {
        return SEEN;
    }

    public String getMESSAGE_TYPE() {
        return MESSAGE_TYPE;
    }

    public String getLAST_UPDATE() {
        return LAST_UPDATE;
    }
}
