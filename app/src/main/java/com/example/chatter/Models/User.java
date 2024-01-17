package com.example.chatter.Models;

public class User {

    private String USER_ID;
    private String NAME;
    private String PHONE_NUMBER;
    private String REGISTRATION_DATE;
    private String LAST_SEEN;
    private String PROFILE_PICTURE;

    public User() {
    }

    public User(String USER_ID, String NAME, String PHONE_NUMBER, String REGISTRATION_DATE, String LAST_SEEN, String PROFILE_PICTURE) {
        this.USER_ID = USER_ID;
        this.NAME = NAME;
        this.PHONE_NUMBER = PHONE_NUMBER;
        this.REGISTRATION_DATE = REGISTRATION_DATE;
        this.LAST_SEEN = LAST_SEEN;
        this.PROFILE_PICTURE = PROFILE_PICTURE;
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
}
