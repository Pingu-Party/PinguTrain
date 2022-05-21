package de.pinguparty.pingutrain.bot.domain;

import java.time.Instant;

public class ReceivedMessage {
    private String chatID;
    private String userName;
    private String firstName;
    private String lastName;
    private String userID;
    private Instant timestamp;
    private String text;
    private Location location;

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

    public String getFirstName() {
        return firstName;
    }

    public ReceivedMessage setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ReceivedMessage setLastName(String lastName) {
        this.lastName = lastName;
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

    public Location getLocation() {
        return location;
    }

    public ReceivedMessage setLocation(Location location) {
        this.location = location;
        return this;
    }

    public boolean hasLocation() {
        return location != null;
    }
}
