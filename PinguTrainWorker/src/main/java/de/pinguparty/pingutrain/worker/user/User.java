package de.pinguparty.pingutrain.worker.user;

import de.pinguparty.pingutrain.worker.interactions.Interaction;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document
public class User {
    @Id
    private long userID;
    private long chatID;
    private String firstName;
    private String lastName;
    private Interaction currentInteraction;
    private Instant lastAction;

    public User() {
        this.currentInteraction = null;
        this.lastAction = Instant.now();
    }

    public long getChatID() {
        return chatID;
    }

    public long getUserID() {
        return userID;
    }

    public User setUserID(long userID) {
        this.userID = userID;
        return this;
    }

    public User setChatID(long chatID) {
        this.chatID = chatID;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Interaction getCurrentInteraction() {
        return currentInteraction;
    }

    public User setCurrentInteraction(Interaction currentInteraction) {
        this.currentInteraction = currentInteraction;
        return this;
    }

    public Instant getLastAction() {
        return lastAction;
    }

    public User setLastAction(Instant lastAction) {
        this.lastAction = lastAction;
        return this;
    }

    public User updateLastAction() {
        this.lastAction = Instant.now();
        return this;
    }
}
