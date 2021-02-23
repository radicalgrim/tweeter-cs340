package edu.byu.cs.tweeter.model.service.request;

public class FollowerRequest {

    private final String followeeAlias;
    private final int limit;
    private final String lastFollowerAlias;

    public FollowerRequest(String followeeAlias, int limit, String lastFollowerAlias) {
        this.followeeAlias = followeeAlias;
        this.limit = limit;
        this.lastFollowerAlias = lastFollowerAlias;
    }

    public String getFolloweeAlias() {
        return followeeAlias;
    }

    public int getLimit() {
        return limit;
    }

    public String getLastFollowerAlias() {
        return lastFollowerAlias;
    }
}
