package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.presenter.UnfollowPresenter;

public class UnfollowTask extends AsyncTask<UnfollowRequest, Void, UnfollowResponse> {
    private final UnfollowPresenter presenter;
    private final UnfollowTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void unfollowSuccessful(UnfollowResponse unfollowResponse);
        void unfollowUnsuccessful(UnfollowResponse unfollowResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to login.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public UnfollowTask(UnfollowPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }
    @Override
    protected UnfollowResponse doInBackground(UnfollowRequest... unfollowRequests) {
        UnfollowResponse unfollowResponse = null;

        try {
            unfollowResponse = presenter.unfollow(unfollowRequests[0]);

            if(unfollowResponse.isSuccess()) {
                //loadImage(loginResponse.getUser());
                //quit activity
            }
        } catch (IOException ex) {
            exception = ex;
        }

        return unfollowResponse;
    }

    @Override
    protected void onPostExecute(UnfollowResponse unfollowResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(unfollowResponse.isSuccess()) {
            observer.unfollowSuccessful(unfollowResponse);
        } else {
            observer.unfollowUnsuccessful(unfollowResponse);
        }
    }
}
