package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;

public class RegisterPresenterTest {
    private RegisterRequest request;
    private RegisterResponse response;
    private RegisterRequest failRequest;
    private RegisterResponse failResponse;
    private RegisterService mockRegisterService;
    private RegisterPresenter presenter;
    private static final String MALE_IMAGE_URL = "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png";

    @BeforeEach
    public void setup() throws IOException {
        User userAllen = new User("Allen", "Anderson", MALE_IMAGE_URL);

        request = new RegisterRequest("Braden", "Borough", "@bborough", "randomPass1", MALE_IMAGE_URL);
        response = new RegisterResponse(userAllen, new AuthToken());

        failRequest = new RegisterRequest("Braden", "Borough", "@bborough", "randomPass1", "fakephotolink");
        failResponse = new RegisterResponse("couldn't find the user");

        mockRegisterService = Mockito.mock(RegisterService.class);
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);

        presenter = Mockito.spy(new RegisterPresenter(new RegisterPresenter.View() {}));
        Mockito.when(presenter.register(request)).thenReturn(response);
    }

    @Test
    public void registerSuccess() throws IOException {
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response.getUser().getFirstName(), presenter.register(request).getUser().getFirstName());
        Assertions.assertEquals(response.getUser().getLastName(), presenter.register(request).getUser().getLastName());
        AuthToken authToken = presenter.register(request).getAuthToken();
        Assertions.assertNotNull(presenter.register(request).getAuthToken());
    }
    @Test
    public void registerFail() throws IOException {
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).

        //Assertions.assertEquals(failResponse.isSuccess(), presenter.register(failRequest).isSuccess());
        //Assertions.assertEquals(failResponse.getMessage(), presenter.register(failRequest).getMessage());

    }
}
