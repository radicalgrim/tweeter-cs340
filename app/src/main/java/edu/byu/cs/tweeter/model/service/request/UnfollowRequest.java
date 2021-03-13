package edu.byu.cs.tweeter.model.service.request;

import edu.byu.cs.tweeter.model.domain.User;

public class UnfollowRequest {
    private final User user;


    public UnfollowRequest(User user) {
        this.user = user;
    }

    /**
     * Returns the username of the user to be unfollowed  by this request.
     *
     * @return the username.
     */
    public User getUser() {
        return user;
    }
}
