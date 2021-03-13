package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

public class UnfollowService extends AbstractService {
    public UnfollowResponse unfollow(UnfollowRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        UnfollowResponse unfollowResponse = serverFacade.unfollow(request);

        if(unfollowResponse.isSuccess()) {
            //loadImage(UnfollowResponse.getUser());
            //quit activity, toast
        }

        return unfollowResponse;
    }
}
