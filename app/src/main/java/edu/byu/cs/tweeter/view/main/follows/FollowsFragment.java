package edu.byu.cs.tweeter.view.main.follows;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.asyncTasks.follows.GetFollowsTask;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public abstract class FollowsFragment extends Fragment {

    protected static final String USER_KEY = "UserKey";
    protected static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    protected static final int LOADING_DATA_VIEW = 0;
    protected static final int ITEM_VIEW = 1;

    protected static final int PAGE_SIZE = 10;

    protected User user;
    protected AuthToken authToken;


    protected abstract class FollowsHolder extends RecyclerView.ViewHolder {

        protected final ImageView userImage;
        protected final TextView userAlias;
        protected final TextView userName;

        protected FollowsHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                userImage = itemView.findViewById(R.id.userImage);
                userAlias = itemView.findViewById(R.id.userAlias);
                userName = itemView.findViewById(R.id.userName);
                
                itemView.setOnClickListener(view -> Toast.makeText(getContext(),
                        "You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show());
            } else {
                userImage = null;
                userAlias = null;
                userName = null;
            }
        }

        void bindUser(User user) {
            if (user.getImageBytes() != null) {
                userImage.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));
            }
            userAlias.setText(user.getAlias());
            userName.setText(user.getName());
        }
    }


    protected abstract class FollowsRecyclerViewAdapter extends RecyclerView.Adapter<FollowsFragment.FollowsHolder> implements GetFollowsTask.Observer {
        protected final List<User> users = new ArrayList<>();

        protected User lastUser;

        public boolean hasMorePages;
        public boolean isLoading = false;

        protected void addItems(List<User> newUsers) {
            int startInsertPosition = users.size();
            users.addAll(newUsers);
            this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
        }

        void addItem(User user) {
            users.add(user);
            this.notifyItemInserted(users.size() - 1);
        }

        void removeItem(User user) {
            int position = users.indexOf(user);
            users.remove(position);
            this.notifyItemRemoved(position);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowsFragment.FollowsHolder FollowsHolder, int position) {
            if(!isLoading) {
                FollowsHolder.bindUser(users.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        protected void addLoadingFooter() {
            addItem(new User("Dummy", "User", ""));
        }

        protected void removeLoadingFooter() {
            removeItem(users.get(users.size() - 1));
        }
    }
    
}
