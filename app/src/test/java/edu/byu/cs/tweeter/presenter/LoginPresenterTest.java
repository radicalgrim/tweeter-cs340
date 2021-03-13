package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;

public class LoginPresenterTest {
    private LoginRequest request;
    private LoginResponse response;
    private LoginRequest failRequest;
    private LoginResponse failResponse;
    private LoginService mockLoginService;
    private LoginPresenter presenter;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    @BeforeEach
    public void setup() throws IOException {
        User userAllen = new User("Allen", "Anderson", MALE_IMAGE_URL);

        request = new LoginRequest("@AllenAnderson", "randomPass1");
        response = new LoginResponse(userAllen, new AuthToken());

        User fakeUser = new User("Mr", "FakeBoi", MALE_IMAGE_URL);
        failRequest = new LoginRequest("@fakeyFakerton", "liarsPass");
        failResponse = new LoginResponse("couldnt find the user");

        mockLoginService = Mockito.mock(LoginService.class);
        //Mockito.when(mockLoginService.follow(request)).thenReturn(response);

        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        //Mockito.when(presenter.follow(request)).thenReturn(response);
        Mockito.when(presenter.getLoginService()).thenReturn(mockLoginService);
    }

    @Test
    public void followSuccess() throws IOException {
        Mockito.when(mockLoginService.login(request)).thenReturn(response);
        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response.isSuccess(), presenter.login(request).isSuccess());
    }
    @Test
    public void followFail() throws IOException {
        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Mockito.when(mockLoginService.login(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.login(request);
        });;
//        Assertions.assertEquals(failResponse.getMessage(), presenter.follow(failRequest).getMessage());
    }
}
