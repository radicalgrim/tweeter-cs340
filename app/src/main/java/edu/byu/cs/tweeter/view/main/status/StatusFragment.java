package edu.byu.cs.tweeter.view.main.status;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
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
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.view.asyncTasks.status.GetStatusTask;
import edu.byu.cs.tweeter.view.main.MainActivity;
import edu.byu.cs.tweeter.view.util.ImageUtils;

public abstract class StatusFragment extends Fragment {

    protected static final String USER_KEY = "UserKey";
    protected static final String AUTH_TOKEN_KEY = "AuthTokenKey";

    protected static final int LOADING_DATA_VIEW = 0;
    protected static final int ITEM_VIEW = 1;

    protected static final int PAGE_SIZE = 10;

    protected User user;
    protected AuthToken authToken;


    protected abstract class StatusHolder extends RecyclerView.ViewHolder {

        protected final ImageView userImage;
        protected final TextView userAlias;
        protected final TextView userName;

        protected final TextView tweetTimestamp;
        protected final TextView tweetMessage;
        protected final TextView tweetMentions;
        protected final TextView tweetLinks;

        protected StatusHolder(@NonNull View itemView, int viewType) {
            super(itemView);

            if(viewType == ITEM_VIEW) {
                userImage = itemView.findViewById(R.id.userImage);
                userAlias = itemView.findViewById(R.id.userAlias);
                userName = itemView.findViewById(R.id.userName);

                tweetTimestamp = itemView.findViewById(R.id.tweet_timestamp);
                tweetMessage = itemView.findViewById(R.id.tweet_message);
                tweetMentions = itemView.findViewById(R.id.tweet_mentions);
                tweetLinks = itemView.findViewById(R.id.tweet_links);

                itemView.findViewById(R.id.status_user_info).setOnClickListener(view -> Toast.makeText(getContext(),
                        "You selected '" + userName.getText() + "'.", Toast.LENGTH_SHORT).show());
            } else {
                userImage = null;
                userAlias = null;
                userName = null;

                tweetTimestamp = null;
                tweetMessage = null;
                tweetMentions = null;
                tweetLinks = null;
            }
        }

        void bindStatus(Status status) {
            if (status.getUser().getImageBytes() != null) {
                userImage.setImageDrawable(ImageUtils.drawableFromByteArray(status.getUser().getImageBytes()));
            }
            userAlias.setText(status.getUser().getAlias());
            userName.setText(status.getUser().getName());

            tweetTimestamp.setText(status.getTimestamp());
            tweetMessage.setText(status.getMessage());

            SpannableString mention = new SpannableString(status.getMention());
            mention.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View view) {

                    // TODO: Start new user activity from here

                    Toast.makeText(getActivity(), "You clicked me", Toast.LENGTH_SHORT).show();
                }
            }, 0, status.getMention().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tweetMentions.setText(mention);
            tweetMentions.setMovementMethod(LinkMovementMethod.getInstance());

            SpannableString url = new SpannableString(status.getLink());
            url.setSpan(new URLSpan(status.getLink()), 0, status.getLink().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            tweetLinks.setText(url);
            tweetLinks.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }


    protected abstract class StatusRecyclerViewAdapter extends RecyclerView.Adapter<StatusFragment.StatusHolder> implements GetStatusTask.Observer {
        protected final List<Status> statuses = new ArrayList<>();

        protected Status lastStatus;

        public boolean hasMorePages;
        public boolean isLoading = false;

        protected void addItems(List<Status> newStatuses) {
            int startInsertPosition = statuses.size();
            statuses.addAll(newStatuses);
            this.notifyItemRangeInserted(startInsertPosition, newStatuses.size());
        }

        void addItem(Status status) {
            statuses.add(status);
            this.notifyItemInserted(statuses.size() - 1);
        }

        void removeItem(Status status) {
            int position = statuses.indexOf(status);
            statuses.remove(position);
            this.notifyItemRemoved(position);
        }

        @Override
        public void onBindViewHolder(@NonNull StatusFragment.StatusHolder statusHolder, int position) {
            if(!isLoading) {
                statusHolder.bindStatus(statuses.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return statuses.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }

        protected void addLoadingFooter() {
            addItem(new Status("Dummy", "User", "", user));
        }

        protected void removeLoadingFooter() {
            removeItem(statuses.get(statuses.size() - 1));
        }
    }

}
