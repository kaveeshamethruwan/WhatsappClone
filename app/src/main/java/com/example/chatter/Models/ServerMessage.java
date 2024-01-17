package com.example.chatter.Models;

public class ServerMessage {

        private final String MESSAGE_ID;
        private String SENDER;
        private String RECEIVER;
        private String BODY;
        private String SENT_TIME;
        private String RECEIVE_TIME;
        private String CHAT_THREAD;
        private String SEEN;
        private String MESSAGE_TYPE;
        private String LAST_UPDATE;
        private String USER_ID;
        private String NAME;
        private String PHONE_NUMBER;
        private String REGISTRATION_DATE;
        private String LAST_SEEN;
        private String PROFILE_PICTURE;
        private String receive_time;
        private String last_update;

        public ServerMessage(String MESSAGE_ID, String SENDER, String RECEIVER, String BODY, String SENT_TIME, String RECEIVE_TIME, String CHAT_THREAD, String SEEN, String MESSAGE_TYPE, String LAST_UPDATE, String USER_ID, String NAME, String PHONE_NUMBER, String REGISTRATION_DATE, String LAST_SEEN, String PROFILE_PICTURE, String receive_time, String last_update) {
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
                this.USER_ID = USER_ID;
                this.NAME = NAME;
                this.PHONE_NUMBER = PHONE_NUMBER;
                this.REGISTRATION_DATE = REGISTRATION_DATE;
                this.LAST_SEEN = LAST_SEEN;
                this.PROFILE_PICTURE = PROFILE_PICTURE;
                this.receive_time = receive_time;
                this.last_update = last_update;
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

        public String getUSER_ID() {
                return USER_ID;
        }

        public String getNAME() {
                return NAME;
        }

        public String getPHONE_NUMBER() {
                return PHONE_NUMBER;
        }

        public String getREGISTRATION_DATE() {
                return REGISTRATION_DATE;
        }

        public String getLAST_SEEN() {
                return LAST_SEEN;
        }

        public String getPROFILE_PICTURE() {
                return PROFILE_PICTURE;
        }

        public String getReceive_time() {
                return receive_time;
        }

        public String getLast_update() {
                return last_update;
        }
}
