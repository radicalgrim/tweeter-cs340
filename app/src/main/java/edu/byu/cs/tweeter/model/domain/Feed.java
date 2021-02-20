package edu.byu.cs.tweeter.model.domain;

import java.util.Map;

/**
 * Represents a user's feed of statuses from the users they are following.
 */
public class Feed {

    // The TA's suggested to store the date as a String and worry about sorting later
    private Map<String, Status> userFeed;

    public Feed() {
    }

    public Feed(Map<String, Status> userFeed) {
        this.userFeed = userFeed;
    }


    public Map<String, Status> getUserFeed() {
        return userFeed;
    }

    public void setUserFeed(Map<String, Status> userFeed) {
        this.userFeed = userFeed;
    }
}
