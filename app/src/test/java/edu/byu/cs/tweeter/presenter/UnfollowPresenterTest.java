package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

public class UnfollowPresenterTest {
    private UnfollowRequest request;
    private UnfollowResponse response;
    private UnfollowRequest failRequest;
    private UnfollowResponse failResponse;
    private UnfollowService mockUnfollowService;
    private UnfollowPresenter presenter;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    @BeforeEach
    public void setup() throws IOException {
        User userAllen = new User("Allen", "Anderson", MALE_IMAGE_URL);

        request = new UnfollowRequest(userAllen);
        response = new UnfollowResponse(true);

        User fakeUser = new User("Mr", "FakeBoi", MALE_IMAGE_URL);
        failRequest = new UnfollowRequest(fakeUser);
        failResponse = new UnfollowResponse("couldn't unfollow the user");

        mockUnfollowService = Mockito.mock(UnfollowService.class);
        //Mockito.when(mockUnfollowResponse.follow(request)).thenReturn(response);

        presenter = Mockito.spy(new UnfollowPresenter(new UnfollowPresenter.View() {}));
        //Mockito.when(presenter.follow(request)).thenReturn(response);
        Mockito.when(presenter.getUnfollowService()).thenReturn(mockUnfollowService);
    }

    @Test
    public void followSuccess() throws IOException {
        Mockito.when(mockUnfollowService.unfollow(request)).thenReturn(response);
        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response.isSuccess(), presenter.unfollow(request).isSuccess());
    }
    @Test
    public void followFail() throws IOException {
        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Mockito.when(mockUnfollowService.unfollow(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.unfollow(request);
        });;
//        Assertions.assertEquals(failResponse.getMessage(), presenter.follow(failRequest).getMessage());
    }
}
