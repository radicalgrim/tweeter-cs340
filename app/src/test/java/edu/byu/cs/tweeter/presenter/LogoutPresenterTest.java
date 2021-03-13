package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.LogoutService;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;


public class LogoutPresenterTest {
    private LogoutRequest request;
    private LogoutResponse response;
    private LogoutRequest failRequest;
    private LogoutResponse failResponse;
    private LogoutService mockLogoutService;
    private LogoutPresenter presenter;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    @BeforeEach
    public void setup() throws IOException {
        User userAllen = new User("Allen", "Anderson", MALE_IMAGE_URL);

        request = new LogoutRequest(userAllen);
        response = new LogoutResponse(true);

        User fakeUser = new User("Mr", "FakeBoi", MALE_IMAGE_URL);
        failRequest = new LogoutRequest(fakeUser);
        failResponse = new LogoutResponse("failed to logout user");

        mockLogoutService = Mockito.mock(LogoutService.class);
        //Mockito.when(mockLogoutService.follow(request)).thenReturn(response);

        presenter = Mockito.spy(new LogoutPresenter(new LogoutPresenter.View() {}));
        //Mockito.when(presenter.follow(request)).thenReturn(response);
        Mockito.when(presenter.getLogoutService()).thenReturn(mockLogoutService);
    }

    @Test
    public void followSuccess() throws IOException {
        Mockito.when(mockLogoutService.logout(request)).thenReturn(response);
        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response.isSuccess(), presenter.logout(request).isSuccess());
    }
    @Test
    public void followFail() throws IOException {
        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Mockito.when(mockLogoutService.logout(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.logout(request);
        });;
//        Assertions.assertEquals(failResponse.getMessage(), presenter.follow(failRequest).getMessage());
    }
}
