package edu.byu.cs.tweeter.presenter;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.FollowService;
import edu.byu.cs.tweeter.model.service.UnfollowService;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;

public class UnfollowPresenter {
    private final View view;

    /**
     * The interface by which this presenter communicates with it's view.
     */
    public interface View {
        // If needed, specify methods here that will be called on the view in response to model updates
    }

    /**
     * Creates an instance.
     *
     * @param view the view for which this class is the presenter.
     */
    public UnfollowPresenter(View view) {
        this.view = view;
    }


    public UnfollowResponse unfollow(UnfollowRequest unfollowRequest) throws IOException {
        UnfollowService unfollowService = getUnfollowService();
        return unfollowService.unfollow(unfollowRequest);
    }

    public UnfollowService getUnfollowService(){
        return new UnfollowService();
    }
}
