package edu.byu.cs.tweeter.model.service.response;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public class PostResponse extends Response {

    public PostResponse(String message) {
        super(false, message);
    }

    public PostResponse() {
        super(true, null);
    }

}
