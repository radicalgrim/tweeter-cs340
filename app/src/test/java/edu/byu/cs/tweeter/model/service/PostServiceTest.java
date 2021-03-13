package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;


public class PostServiceTest {
    private PostRequest validRequest;
    private PostRequest invalidRequest;

    private PostResponse successResponse;
    private PostResponse failureResponse;

    private PostService PostServiceSpy;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    /**
     * Create a postingService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() {
        User userAllen = new User("Allen", "Anderson", MALE_IMAGE_URL);
        User fakeUser = new User("fakey", "fakerton", MALE_IMAGE_URL);

        // Setup request objects to use in the tests
        validRequest = new PostRequest("real Post");
        invalidRequest = new PostRequest("Fake Post");

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostResponse();
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.post(validRequest)).thenReturn(successResponse);

        failureResponse = new PostResponse("Could not post");
        Mockito.when(mockServerFacade.post(invalidRequest)).thenReturn(failureResponse);

        // Create a postingService instance and wrap it with a spy that will use the mock service
        PostServiceSpy = Mockito.spy(new PostService());
        Mockito.when(PostServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    @Test
    public void PostServiceSuccess() throws IOException {
        PostResponse response = PostServiceSpy.post(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    @Test
    public void PostServiceFail() throws IOException {
        PostResponse response = PostServiceSpy.post(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
