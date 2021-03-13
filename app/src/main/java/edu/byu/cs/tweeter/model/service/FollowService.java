package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

public class FollowService extends AbstractService{
    public FollowResponse follow(FollowRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        FollowResponse followResponse = serverFacade.follow(request);

        if(followResponse.isSuccess()) {
            //loadImage(UnfollowResponse.getUser());
            //quit activity, toast
        }

        return followResponse;
    }
}
