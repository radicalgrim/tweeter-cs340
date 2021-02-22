package edu.byu.cs.tweeter.model.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a status in a feed or story.
 */
public class Status {

    private String message;
    // Anything starting with an @ symbol
    private String mention;
    // Anything starting with http:// or https://
    private String link;
    private String timestamp;
    private User user;

    public Status(String message) {
        this.message = message;
    }

    public Status(String message, String mention, String link, User user) {
        this.message = message;
        this.mention = mention;
        this.link = link;
        this.user = user;
        this.timestamp = new SimpleDateFormat("MMM. dd, yyyy hh:mm").format(new Date());
    }

    public Status(String message, String mention, String link, String timestamp, User user) {
        this.message = message;
        this.mention = mention;
        this.link = link;
        this.user = user;
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
