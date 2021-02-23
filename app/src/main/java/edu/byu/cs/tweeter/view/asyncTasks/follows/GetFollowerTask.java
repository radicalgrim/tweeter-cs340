package edu.byu.cs.tweeter.view.asyncTasks.follows;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowerResponse;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;

public class GetFollowerTask extends AsyncTask<FollowerRequest, Void, FollowerResponse> implements GetFollowsTask {

    private final FollowerPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve followees.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetFollowerTask(FollowerPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve followees. This method is
     * invoked indirectly by calling {@link #execute(FollowerRequest...)}.
     *
     * @param FollowerRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected FollowerResponse doInBackground(FollowerRequest... FollowerRequests) {

        FollowerResponse response = null;

        try {
            response = presenter.getFollower(FollowerRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param FollowerResponse the response that was received by the task.
     */
    @Override
    protected void onPostExecute(FollowerResponse FollowerResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.usersRetrieved(FollowerResponse);
        }
    }
    
}
