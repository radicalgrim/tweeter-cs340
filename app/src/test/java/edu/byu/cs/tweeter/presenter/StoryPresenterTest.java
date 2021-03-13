package edu.byu.cs.tweeter.presenter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.StoryService;
import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;

public class StoryPresenterTest {

    private StoryRequest request;
    private StoryResponse response;
    private StoryService mockStoryService;
    private StoryPresenter presenter;

    @BeforeEach
    public void setup() throws IOException {
        User testUser = new User("FirstName", "LastName",
                "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        Status currentStatus = new Status("Hello World", "@user",
                "https://google.com", "1:00", testUser);

        Status resultStatus1 = new Status("Hello World1", "@user1",
                "https://google.com", "1:00", testUser);
        Status resultStatus2 = new Status("Hello World2", "@user2",
                "https://google.com", "2:00", testUser);
        Status resultStatus3 = new Status("Hello World3", "@user3",
                "https://google.com", "3:00", testUser);

        request = new StoryRequest(currentStatus.getUser().getAlias(), 3, null);
        response = new StoryResponse(Arrays.asList(resultStatus1, resultStatus2, resultStatus3), false);

        // Create a mock StoryService
        mockStoryService = Mockito.mock(StoryService.class);
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);

        // Wrap a StoryPresenter in a spy that will use the mock service.
        presenter = Mockito.spy(new StoryPresenter(new StoryPresenter.View() {}));
        Mockito.when(presenter.getStoryService()).thenReturn(mockStoryService);
    }

    @Test
    public void testGetStory_returnsServiceResult() throws IOException {
        Mockito.when(mockStoryService.getStory(request)).thenReturn(response);

        // Assert that the presenter returns the same response as the service (it doesn't do
        // anything else, so there's nothing else to test).
        Assertions.assertEquals(response, presenter.getStory(request));
    }

    @Test
    public void testGetStory_serviceThrowsIOException_presenterThrowsIOException() throws IOException {
        Mockito.when(mockStoryService.getStory(request)).thenThrow(new IOException());

        Assertions.assertThrows(IOException.class, () -> {
            presenter.getStory(request);
        });
    }
}


