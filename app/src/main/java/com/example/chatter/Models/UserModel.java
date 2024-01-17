package com.example.chatter.Models;

import com.example.chatter.Constants;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Constants.USERS_TABLE)
public class UserModel {

    @NonNull
    @PrimaryKey
    private int userID = 0;

    private String name;
    private String phoneNumber;
    private String registrationDate;
    private int lastSeen;
    private String profilePicture;

    public UserModel() {

    }

    public UserModel(int userID, String name, String phoneNumber, String registrationDate, int lastSeen, String profilePicture) {
        this.userID = userID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.lastSeen = lastSeen;
        this.profilePicture = profilePicture;
    }

    public int getUserID() {
        return userID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRegistrationDate() {
        return registrationDate;
    }

    public int getLastSeen() {
        return lastSeen;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setUserID(@NonNull Integer userID) {
        this.userID = userID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRegistrationDate(String registrationDate) {
        this.registrationDate = registrationDate;
    }

    public void setLastSeen(int lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
