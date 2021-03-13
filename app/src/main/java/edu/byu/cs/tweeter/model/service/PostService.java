package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostResponse;

public class PostService extends AbstractService {
    public PostResponse post(PostRequest request) throws IOException {
        ServerFacade serverFacade = getServerFacade();
        PostResponse postResponse = serverFacade.post(request);

        if(postResponse.isSuccess()) {
            //loadImage(logoutResponse.getUser());
            //quit activity, toast
        }

        return postResponse;
    }
}
