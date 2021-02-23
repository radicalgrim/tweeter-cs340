package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FeedService;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;

public class FeedPresenter {

    private final FeedPresenter.View view;

    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }
    
    public FeedPresenter(View view) {
        this.view = view;
    }

    public FeedResponse getFeed(FeedRequest request) throws IOException {
        FeedService FeedService = getFeedService();
        return FeedService.getFeed(request);
    }

    FeedService getFeedService() {
        return new FeedService();
    }
}
