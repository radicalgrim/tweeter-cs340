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
import edu.byu.cs.tweeter.model.service.RegisterService;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.RegisterResponse;
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

        request = new RegisterRequest("Allen", "Anderson","@AllenAnderson", "randomPass1", MALE_IMAGE_URL);
        response = new RegisterResponse(userAllen, new AuthToken());

        User fakeUser = new User("Mr", "FakeBoi", MALE_IMAGE_URL);
        failRequest = new RegisterRequest("Fakey", "Fakerton", "@fakeyFakerton", "liarsPass", MALE_IMAGE_URL);
        failResponse = new RegisterResponse("couldnt register the user");

        mockRegisterService = Mockito.mock(RegisterService.class);
        //Mockito.when(mockRegisterService.follow(request)).thenReturn(response);

        presenter = Mockito.spy(new RegisterPresenter(new RegisterPresenter.View() {}));
        //Mockito.when(presenter.follow(request)).thenReturn(response);
        Mockito.when(presenter.getRegisterService()).thenReturn(mockRegisterService);
    }

    @Test
    public void followSuccess() throws IOException {
        Mockito.when(mockRegisterService.register(request)).thenReturn(response);
        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response.isSuccess(), presenter.register(request).isSuccess());
    }
    @Test
    public void followFail() throws IOException {
        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Mockito.when(mockRegisterService.register(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.register(request);
        });;
//        Assertions.assertEquals(failResponse.getMessage(), presenter.follow(failRequest).getMessage());
    }
}
