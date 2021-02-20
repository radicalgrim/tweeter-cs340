package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.FollowingService;
import edu.byu.cs.tweeter.model.service.LoginService;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
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

        failRequest = new LoginRequest("@AllenAnderson", "randomPass2");
        failResponse = new LoginResponse("couldn't find the user");

        mockLoginService = Mockito.mock(LoginService.class);
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        presenter = Mockito.spy(new LoginPresenter(new LoginPresenter.View() {}));
        Mockito.when(presenter.login(request)).thenReturn(response);
    }

    @Test
    public void loginSuccess() throws IOException {
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response.getUser().getFirstName(), presenter.login(request).getUser().getFirstName());
        Assertions.assertEquals(response.getUser().getLastName(), presenter.login(request).getUser().getLastName());
        AuthToken authToken = presenter.login(request).getAuthToken();
        Assertions.assertNotNull(presenter.login(request).getAuthToken());
    }
    @Test
    public void loginFail() throws IOException {
        Mockito.when(mockLoginService.login(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(failResponse.isSuccess(), presenter.login(failRequest).isSuccess());
        Assertions.assertEquals(failResponse.getMessage(), presenter.login(failRequest).getMessage());

    }

}
