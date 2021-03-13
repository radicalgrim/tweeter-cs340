package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.presenter.PostPresenter;
import edu.byu.cs.tweeter.presenter.PostPresenter;
import edu.byu.cs.tweeter.util.ByteArrayUtils;


public class PostTask extends AsyncTask<PostRequest, Void, PostResponse> {
    private final PostPresenter presenter;
    private final PostTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void postSuccessful(PostResponse postResponse);
        void postUnsuccessful(PostResponse postResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to login.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public PostTask(PostPresenter presenter, PostTask.Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }


    @Override
    protected PostResponse doInBackground(PostRequest... postRequests) {
        PostResponse PostResponse = null;

        try {
            PostResponse = presenter.post(postRequests[0]);

            if(PostResponse.isSuccess()) {
                //loadImage(PostResponse.getUser());
            }
        } catch (IOException ex) {
            exception = ex;
        }

        return PostResponse;
    }

    /**
     * Loads the profile image for the user.
     *
     * @param user the user whose profile image is to be loaded.
     */
    private void loadImage(User user) {
        try {
            byte [] bytes = ByteArrayUtils.bytesFromUrl(user.getImageUrl());
            user.setImageBytes(bytes);
        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.toString(), e);
        }
    }

    @Override
    protected void onPostExecute(PostResponse PostResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(PostResponse.isSuccess()) {
            observer.postSuccessful(PostResponse);
        } else {
            observer.postUnsuccessful(PostResponse);
        }
    }
}
