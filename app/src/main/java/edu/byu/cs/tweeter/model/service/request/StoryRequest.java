package edu.byu.cs.tweeter.model.service.request;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * statuses for a specified user.
 */
public class StoryRequest {

    private final String userAlias;
    private final int limit;
    private final String lastTimestamp;

    /**
     * Creates an instance.
     *
     * @param userAlias the story whose statuses are to be returned.
     * @param limit the maximum number of statuses to return.
     * @param lastTimestamp the date of the last status that was returned in the previous request (null if
     *                     there was no previous request or if no statuses were returned in the
     *                     previous request).
     */
    public StoryRequest(String userAlias, int limit, String lastTimestamp) {
        this.userAlias = userAlias;
        this.limit = limit;
        this.lastTimestamp = lastTimestamp;
    }

    /**
     * Returns the story whose statuses are to be returned by this request.
     *
     * @return the story.
     */
    public String getUserAlias() {
        return userAlias;
    }

    /**
     * Returns the number representing the maximum number of statuses to be returned by this request.
     *
     * @return the limit.
     */
    public int getLimit() {
        return limit;
    }

    /**
     * Returns the last status that was returned in the previous request or null if there was no
     * previous request or if no statuses were returned in the previous request.
     *
     * @return the last status date.
     */
    public String getLastTimestamp() {
        return lastTimestamp;
    }

}