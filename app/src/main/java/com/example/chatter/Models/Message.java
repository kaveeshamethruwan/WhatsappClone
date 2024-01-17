package com.example.chatter.Models;

import static com.example.chatter.Constants.*;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = USER_CHATS_TABLE)
public class Message {

    @NonNull
    @PrimaryKey
    private Long messageID = 0L;

    private String sender;
    private String receiver;
    private String body;
    private String sendTime;
    private String receiveTime;
    private String chatThread;
    private String name;
    private String phoneNumber;
    private String registrationDate;
    private String lastSeen;
    private String profilePicture;
    private boolean seen;
    private String messageType;
    private String lastUpdate;
    private boolean fromMe;
    private String mediaLink;

    public Message() {

    }

    public Message(long messageID, String sender, String receiver, String body, String sendTime, String receiveTime, String chatThread, String name, String phoneNumber, String registrationDate, String lastSeen, String profilePicture, boolean seen, String messageType, String lastUpdate, boolean fromMe, String mediaLink) {
        this.messageID = messageID;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.sendTime = sendTime;
        this.receiveTime = receiveTime;
        this.chatThread = chatThread;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.lastSeen = lastSeen;
        this.profilePicture = profilePicture;
        this.seen = seen;
        this.messageType = messageType;
        this.lastUpdate = lastUpdate;
        this.fromMe = fromMe;
        this.mediaLink = mediaLink;
    }

    public Message(long messageID, String sender, String receiver, String body, String sendTime, String receiveTime, String chatThread, String name, String phoneNumber, String registrationDate, String lastSeen, String profilePicture, boolean seen, String messageType, String lastUpdate, String mediaLink) {
        this.messageID = messageID;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.sendTime = sendTime;
        this.receiveTime = receiveTime;
        this.chatThread = chatThread;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.registrationDate = registrationDate;
        this.lastSeen = lastSeen;
        this.profilePicture = profilePicture;
        this.seen = seen;
        this.messageType = messageType;
        this.lastUpdate = lastUpdate;
        this.fromMe = fromMe;
        this.mediaLink = mediaLink;
    }

    public Message(long itemCount, String formattedTimeEvent, String message, boolean seen, boolean fromMe) {
        messageID = itemCount;
        this.body = message;
        this.seen = seen;
        this.fromMe = fromMe;
    }

    public long getMessageID() {
        return messageID;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getBody() {
        return body;
    }

    public String getSendTime() {
        return sendTime;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public String getChatThread() {
        return chatThread;
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

    public String getLastSeen() {
        return lastSeen;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public boolean isFromMe() {
        return fromMe;
    }

    public String getMediaLink() {
        return mediaLink;
    }

    public void setMessageID(@NonNull Long messageID) {
        this.messageID = messageID;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public void setChatThread(String chatThread) {
        this.chatThread = chatThread;
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

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setFromMe(boolean fromMe) {
        this.fromMe = fromMe;
    }

    public void setMediaLink(String mediaLink) {
        this.mediaLink = mediaLink;
    }
}
