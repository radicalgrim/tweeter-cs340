package edu.byu.cs.tweeter.model.service;

import java.io.IOException;

import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

/**
 * Contains the business logic for getting the story of a particular user.
 */
public class StoryService {

    /**
     * Returns the story of the specified user in the request. Uses information in the request
     * object to limit the number of statuses returned and to return the next set of statuses
     * after any that were returned in a previous request. Uses the {@link ServerFacade} to get
     * the story from the server.
     *
     * @param request contains the data required to fulfill the request.
     * @return the story.
     */
    public StoryResponse getStory(StoryRequest request) throws IOException {
        StoryResponse response = getServerFacade().getStory(request);

        if(response.isSuccess()) {
            // TODO: Implement
        }

        return response;
    }

    /**
     * Returns an instance of {@link ServerFacade}. Allows mocking of the ServerFacade class for
     * testing purposes. All usages of ServerFacade should get their ServerFacade instance from this
     * method to allow for proper mocking.
     *
     * @return the instance.
     */
    ServerFacade getServerFacade() {
        return new ServerFacade();
    }

}
