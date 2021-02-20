package edu.byu.cs.tweeter.model.domain;

/**
 * Represents a status in a feed or story.
 */
public class Status {

    private String message;
    // Anything starting with an @ symbol
    private String mention;
    // Anything starting with http:// or https://
    private String link;

    public Status(String message) {
        this.message = message;
    }

    public Status(String message, String mention, String link) {
        this.message = message;
        this.mention = mention;
        this.link = link;
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
}
