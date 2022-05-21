package de.pinguparty.pingu_train.domain;

import java.time.Instant;

public class ReceivedMessage {
    private String chatID;
    private String userName;
    private String userID;
    private Instant timestamp;
    private String text;
    //private Location location;

    public ReceivedMessage(){

    }

    public String getChatID() {
        return chatID;
    }

    public ReceivedMessage setChatID(String chatID) {
        this.chatID = chatID;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public ReceivedMessage setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserID() {
        return userID;
    }

    public ReceivedMessage setUserID(String userID) {
        this.userID = userID;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public ReceivedMessage setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getText() {
        return text;
    }

    public ReceivedMessage setText(String text) {
        this.text = text;
        return this;
    }
}
