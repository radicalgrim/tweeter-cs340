package edu.byu.cs.tweeter.view.main.follows;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowerRequest;
import edu.byu.cs.tweeter.model.service.response.FollowsResponse;
import edu.byu.cs.tweeter.model.service.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.FollowerPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.follows.GetFollowerTask;
import edu.byu.cs.tweeter.view.asyncTasks.follows.GetFollowsTask;
import edu.byu.cs.tweeter.view.asyncTasks.status.GetStatusTask;
import edu.byu.cs.tweeter.view.main.status.StatusFragment;

public class FollowerFragment extends FollowsFragment implements FollowerPresenter.View {

    private static final String LOG_TAG = "FollowerFragment";

    private FollowerPresenter presenter;

    private FollowerFragment.FollowerRecyclerViewAdapter followerRecyclerViewAdapter;

    public static FollowerFragment newInstance(User user, AuthToken authToken) {
        FollowerFragment fragment = new FollowerFragment();

        Bundle args = new Bundle(2);
        args.putSerializable(USER_KEY, user);
        args.putSerializable(AUTH_TOKEN_KEY, authToken);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_follows, container, false);

        user = (User) getArguments().getSerializable(USER_KEY);
        authToken = (AuthToken) getArguments().getSerializable(AUTH_TOKEN_KEY);

        presenter = new FollowerPresenter(this);

        RecyclerView followerRecyclerView = view.findViewById(R.id.followsRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followerRecyclerView.setLayoutManager(layoutManager);

        followerRecyclerViewAdapter = new FollowerFragment.FollowerRecyclerViewAdapter();
        followerRecyclerView.setAdapter(followerRecyclerViewAdapter);

        followerRecyclerView.addOnScrollListener(new FollowerFragment.FollowerRecyclerViewPaginationScrollListener(layoutManager));

        return view;
    }


    private class FollowerHolder extends FollowsFragment.FollowsHolder {
        FollowerHolder(@NonNull View itemView, int viewType) {
            super(itemView, viewType);
        }
    }


    private class FollowerRecyclerViewAdapter extends FollowsFragment.FollowsRecyclerViewAdapter implements GetFollowsTask.Observer {

        FollowerRecyclerViewAdapter() {
            loadMoreItems();
        }

        @NonNull
        @Override
        public FollowerFragment.FollowerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowerFragment.this.getContext());
            View view;

            if(viewType == LOADING_DATA_VIEW) {
                view = layoutInflater.inflate(R.layout.loading_row, parent, false);
            }
            else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }

            return new FollowerFragment.FollowerHolder(view, viewType);
        }

        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFollowerTask getFollowerTask = new GetFollowerTask(presenter, this);
            FollowerRequest request = new FollowerRequest(user.getAlias(), PAGE_SIZE, lastUser == null ? null : lastUser.getAlias());
            getFollowerTask.execute(request);
        }

        @Override
        public void usersRetrieved(FollowsResponse response) {
            List<User> users = response.getUsers();

            lastUser = (users.size() > 0) ? users.get(users.size() - 1) : null;
            hasMorePages = response.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            followerRecyclerViewAdapter.addItems(users);
        }

        @Override
        public void handleException(Exception exception) {
            Log.e(LOG_TAG, exception.getMessage(), exception);
            removeLoadingFooter();
            Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    private class FollowerRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FollowerRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!followerRecyclerViewAdapter.isLoading && followerRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    followerRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
    
}
