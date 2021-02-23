package edu.byu.cs.tweeter.model.service.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.User;

public class FollowsResponse extends PagedResponse {

    protected List<User> users;

    FollowsResponse(boolean success, boolean hasMorePages) {
        super(success, hasMorePages);
    }

    FollowsResponse(boolean success, String message, boolean hasMorePages) {
        super(success, message, hasMorePages);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
