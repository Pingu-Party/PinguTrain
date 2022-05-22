package de.pinguparty.pingutrain.bot.messages;

import java.time.Instant;

public class UserMessage {
    private long chatID;
    private String userName;
    private String firstName;
    private String lastName;
    private long userID;
    private Instant timestamp;
    private String text;
    private Location location;

    public UserMessage() {

    }

    public long getChatID() {
        return chatID;
    }

    public UserMessage setChatID(long chatID) {
        this.chatID = chatID;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public UserMessage setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserMessage setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserMessage setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public long getUserID() {
        return userID;
    }

    public UserMessage setUserID(long userID) {
        this.userID = userID;
        return this;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public UserMessage setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getText() {
        return text;
    }

    public UserMessage setText(String text) {
        this.text = text;
        return this;
    }

    public boolean hasText() {
        return (this.text != null) && (!this.text.isEmpty());
    }

    public Location getLocation() {
        return location;
    }

    public UserMessage setLocation(Location location) {
        this.location = location;
        return this;
    }

    public boolean hasLocation() {
        return location != null;
    }
}
