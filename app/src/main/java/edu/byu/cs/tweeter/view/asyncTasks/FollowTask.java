package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.UnfollowPresenter;


public class FollowTask extends AsyncTask<FollowRequest, Void, FollowResponse> {
    private final FollowPresenter presenter;
    private final FollowTask.Observer observer;
    private Exception exception;

    public interface Observer {
        void followSuccessful(FollowResponse unfollowResponse);
        void followUnsuccessful(FollowResponse unfollowResponse);
        void handleException(Exception ex);
    }

    public FollowTask(FollowPresenter presenter, FollowTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    @Override
    protected FollowResponse doInBackground(FollowRequest... followRequests) {
        FollowResponse followResponse = null;

        try {
            followResponse = presenter.follow(followRequests[0]);

            if(followResponse.isSuccess()) {
                //loadImage(loginResponse.getUser());
                //quit activity
            }
        } catch (IOException ex) {
            exception = ex;
        }

        return followResponse;
    }

    @Override
    protected void onPostExecute(FollowResponse followResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(followResponse.isSuccess()) {
            observer.followSuccessful(followResponse);
        } else {
            observer.followUnsuccessful(followResponse);
        }
    }
}
