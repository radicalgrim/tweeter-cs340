package edu.byu.cs.tweeter.view.asyncTasks.status;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.StoryRequest;
import edu.byu.cs.tweeter.model.service.response.StoryResponse;
import edu.byu.cs.tweeter.presenter.StoryPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.status.GetStatusTask;

/**
 * An {@link AsyncTask} for retrieving a user's story.
 */
public class GetStoryTask extends AsyncTask<StoryRequest, Void, StoryResponse> implements GetStatusTask {

    private final StoryPresenter presenter;
    private final Observer observer;
    private Exception exception;

    /**
     * Creates an instance.
     *
     * @param presenter the presenter from whom this task should retrieve a story.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public GetStoryTask(StoryPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }

    /**
     * The method that is invoked on the background thread to retrieve the user's story. This
     * method is invoked indirectly by calling {@link #execute(StoryRequest...)}.
     *
     * @param storyRequests the request object (there will only be one).
     * @return the response.
     */
    @Override
    protected StoryResponse doInBackground(StoryRequest... storyRequests) {

        StoryResponse response = null;

        try {
            response = presenter.getStory(storyRequests[0]);
        } catch (IOException ex) {
            exception = ex;
        }

        return response;
    }

    /**
     * Notifies the observer (on the UI thread) when the task completes.
     *
     * @param response the response that was received by the task.
     */
    @Override
    protected void onPostExecute(StoryResponse response) {
        if(exception != null) {
            observer.handleException(exception);
        } else {
            observer.statusesRetrieved(response);
        }
    }

}
