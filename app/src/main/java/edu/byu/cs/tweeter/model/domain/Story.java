package edu.byu.cs.tweeter.model.domain;

import java.util.TreeMap;

/**
 * Represents a user's statuses they have posted.
 */
public class Story {

    private String userAlias;
    // The TA's suggested to store the date as a String and worry about sorting later
    private TreeMap<String, Status> statusMap;

    public Story(String userAlias) {
        this.userAlias = userAlias;
    }

    public Story(TreeMap<String, Status> statusMap) {
        this.statusMap = statusMap;
    }

    public TreeMap<String, Status> getStatusMap() {
        return statusMap;
    }

    public void setStatusMap(TreeMap<String, Status> statusMap) {
        this.statusMap = statusMap;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public void addStatus(String timestamp, Status status) {
        statusMap.put(timestamp, status);
    }
}
