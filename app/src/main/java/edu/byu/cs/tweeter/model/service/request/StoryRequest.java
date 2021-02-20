package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.Story;

/**
 * Contains all the information needed to make a request to have the server return the next page of
 * statuses for a specified user.
 */
public class StoryRequest {

    private final Story story;
    private final int limit;
    private final String lastTimestamp;

    /**
     * Creates an instance.
     *
     * @param story the story whose statuses are to be returned.
     * @param limit the maximum number of statuses to return.
     * @param lastTimestamp the date of the last status that was returned in the previous request (null if
     *                     there was no previous request or if no statuses were returned in the
     *                     previous request).
     */
    public StoryRequest(Story story, int limit, String lastTimestamp) {
        this.story = story;
        this.limit = limit;
        this.lastTimestamp = lastTimestamp;
    }

    /**
     * Returns the story whose statuses are to be returned by this request.
     *
     * @return the story.
     */
    public Story getStory() {
        return story;
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