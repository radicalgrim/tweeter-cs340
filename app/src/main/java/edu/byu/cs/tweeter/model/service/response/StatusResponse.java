package edu.byu.cs.tweeter.model.service.response;

import java.util.List;

import edu.byu.cs.tweeter.model.domain.Status;

public class StatusResponse extends PagedResponse {

    protected List<Status> statuses;

    StatusResponse(boolean success, boolean hasMorePages) {
        super(success, hasMorePages);
    }

    StatusResponse(boolean success, String message, boolean hasMorePages) {
        super(success, message, hasMorePages);
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public void setStatuses(List<Status> statuses) {
        this.statuses = statuses;
    }
}
