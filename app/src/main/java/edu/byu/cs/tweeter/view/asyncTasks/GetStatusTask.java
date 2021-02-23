package edu.byu.cs.tweeter.view.asyncTasks;

import edu.byu.cs.tweeter.model.service.response.StatusResponse;

public interface GetStatusTask {

        interface Observer {
                void statusesRetrieved(StatusResponse response);
                void handleException(Exception exception);
        }
}
