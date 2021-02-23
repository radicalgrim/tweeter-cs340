package edu.byu.cs.tweeter.model.service.request;

public class FeedRequest {

    private final String userAlias;
    private final int limit;
    private final String lastTimestamp;

    public FeedRequest(String userAlias, int limit, String lastTimestamp) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.lastTimestamp = lastTimestamp;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public int getLimit() {
        return limit;
    }

    public String getLastTimestamp() {
        return lastTimestamp;
    }

}
