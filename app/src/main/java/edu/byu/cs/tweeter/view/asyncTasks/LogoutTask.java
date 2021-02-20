package edu.byu.cs.tweeter.view.asyncTasks;

import android.os.AsyncTask;

import java.io.IOException;

import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.response.LoginResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;


public class LogoutTask extends AsyncTask<LogoutRequest, Void, LogoutResponse> {
    private final LogoutPresenter presenter;
    private final LogoutTask.Observer observer;
    private Exception exception;

    /**
     * An observer interface to be implemented by observers who want to be notified when this task
     * completes.
     */
    public interface Observer {
        void logoutSuccessful(LogoutResponse logoutResponse);
        void logoutUnsuccessful(LogoutResponse logoutResponse);
        void handleException(Exception ex);
    }

    /**
     * Creates an instance.
     *
     * @param presenter the presenter this task should use to login.
     * @param observer the observer who wants to be notified when this task completes.
     */
    public LogoutTask(LogoutPresenter presenter, Observer observer) {
        if(observer == null) {
            throw new NullPointerException();
        }

        this.presenter = presenter;
        this.observer = observer;
    }
    @Override
    protected LogoutResponse doInBackground(LogoutRequest... logoutRequests) {
        LogoutResponse logoutResponse = null;

        try {
            logoutResponse = presenter.logout(logoutRequests[0]);

            if(logoutResponse.isSuccess()) {
                //loadImage(loginResponse.getUser());
                //quit activity
            }
        } catch (IOException ex) {
            exception = ex;
        }

        return logoutResponse;
    }

    @Override
    protected void onPostExecute(LogoutResponse logoutResponse) {
        if(exception != null) {
            observer.handleException(exception);
        } else if(logoutResponse.isSuccess()) {
            observer.logoutSuccessful(logoutResponse);
        } else {
            observer.logoutUnsuccessful(logoutResponse);
        }
    }
}
