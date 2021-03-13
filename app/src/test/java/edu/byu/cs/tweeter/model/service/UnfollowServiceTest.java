package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

public class UnfollowServiceTest {
    private UnfollowRequest validRequest;
    private UnfollowRequest invalidRequest;

    private UnfollowResponse successResponse;
    private UnfollowResponse failureResponse;

    private UnfollowService UnfollowServiceSpy;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    /**
     * Create a FollowingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User userAllen = new User("Allen", "Anderson", MALE_IMAGE_URL);
        User fakeUser = new User("fakey", "fakerton", MALE_IMAGE_URL);

        // Setup request objects to use in the tests
        validRequest = new UnfollowRequest(userAllen);
        invalidRequest = new UnfollowRequest(fakeUser);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new UnfollowResponse(true);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.unfollow(validRequest)).thenReturn(successResponse);

        failureResponse = new UnfollowResponse("couldn't unfollow the user");
        Mockito.when(mockServerFacade.unfollow(invalidRequest)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        UnfollowServiceSpy = Mockito.spy(new UnfollowService());
        Mockito.when(UnfollowServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void UnfollowServiceSuccess() throws IOException {
        UnfollowResponse response = UnfollowServiceSpy.unfollow(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void UnfollowServiceFail() throws IOException {
        UnfollowResponse response = UnfollowServiceSpy.unfollow(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}

