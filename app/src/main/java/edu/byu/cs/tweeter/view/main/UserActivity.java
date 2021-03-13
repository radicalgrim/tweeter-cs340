package edu.byu.cs.tweeter.view.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.FollowRequest;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.request.UnfollowRequest;
import edu.byu.cs.tweeter.model.service.response.FollowResponse;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.model.service.response.UnfollowResponse;
import edu.byu.cs.tweeter.presenter.FollowPresenter;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.presenter.PostPresenter;
import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.presenter.UnfollowPresenter;
import edu.byu.cs.tweeter.view.LoginActivity;
import edu.byu.cs.tweeter.view.asyncTasks.FollowTask;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.asyncTasks.PostTask;
import edu.byu.cs.tweeter.view.asyncTasks.UnfollowTask;
import edu.byu.cs.tweeter.view.main.status.SectionsPagerAdapterUser;
import edu.byu.cs.tweeter.view.util.ImageUtils;

import static android.widget.Toast.LENGTH_LONG;

public class UserActivity extends AppCompatActivity implements LogoutPresenter.View, LogoutTask.Observer, UnfollowPresenter.View, UnfollowTask.Observer, FollowPresenter.View, FollowTask.Observer, PostPresenter.View, PostTask.Observer {
    public static final String USER_KEY = "UserKey";
    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private LogoutPresenter logoutPresenter;
    private UnfollowPresenter unfollowPresenter;
    private FollowPresenter followPresenter;

    //private FollowPresenter followPresenter;

    private static final String LOG_TAG = "UserActivity";
    public Button unfollow;
    public Button follow;
    private User clickedUser;
    Dialog newPost;
    private PostPresenter postPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        postPresenter = new PostPresenter(this);
        logoutPresenter = new LogoutPresenter(this);
        unfollowPresenter = new UnfollowPresenter(this);
        followPresenter = new FollowPresenter(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        User user = (User) getIntent().getSerializableExtra(USER_KEY);
        clickedUser = user;
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapterUser sectionsPagerAdapter = new SectionsPagerAdapterUser(this, getSupportFragmentManager(), clickedUser, authToken);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        // We should use a Java 8 lambda function for the listener (and all other listeners), but
        // they would be unfamiliar to many students who use this code.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Pop up a tweet writer dialog box

//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                newPost = new Dialog(UserActivity.this);
                newPost.setContentView(R.layout.post_dialogue);
                //newPost.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                newPost.getWindow().setLayout(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
                newPost.getWindow().getAttributes().gravity = Gravity.TOP;
                newPost.show();

                TextView body = newPost.findViewById(R.id.tweetBody);
                Button addPost = newPost.findViewById(R.id.addTweetButton);
                System.out.println("Tweet Body: " + body.toString());

                addPost.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(UserActivity.this, "Posting... ", Toast.LENGTH_LONG).show();
                        PostRequest postRequest = new PostRequest(body.toString());
                        PostTask postTask = new PostTask(postPresenter, UserActivity.this);
                        postTask.execute(postRequest);
                        newPost.dismiss();
                        //Get rid of the post box??
                        //How is the return succesful or return unsuccessful goin to work? Where will they go?
                    }
                });
            }
        });

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        userImageView.setImageDrawable(ImageUtils.drawableFromByteArray(user.getImageBytes()));

        TextView followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(getString(R.string.followeeCount, 42));

        TextView followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, 27));

        unfollow = findViewById(R.id.unfollowButton);
        unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserActivity.this, "Unfollowing...", Toast.LENGTH_LONG).show();

                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                UnfollowRequest unfollowRequest = new UnfollowRequest(clickedUser/*"dummyUserName", "dummyPassword"*/);
                UnfollowTask unfollowTask = new UnfollowTask(unfollowPresenter, UserActivity.this);
                unfollowTask.execute(unfollowRequest);
            }
        });

        follow = findViewById(R.id.followButton);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserActivity.this, "Following...", Toast.LENGTH_LONG).show();

                // It doesn't matter what values we put here. We will be logged in with a hard-coded dummy user.
                FollowRequest followRequest = new FollowRequest(clickedUser/*"dummyUserName", "dummyPassword"*/);
                FollowTask followTask = new FollowTask(followPresenter, UserActivity.this);
                followTask.execute(followRequest);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId() == R.id.logoutMenu)  {
//            Intent intent = new Intent(this, MainActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
//                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            this.startActivity(intent);
            //ServerFacade.getInstance().invalidateAuthToken();
            User userToLogout = ServerFacade.getInstance().getCurrentUser();
            LogoutRequest logoutRequest = new LogoutRequest(userToLogout);
            LogoutTask logoutTask = new LogoutTask(logoutPresenter, UserActivity.this);
            logoutTask.execute(logoutRequest);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void logoutSuccessful(LogoutResponse logoutResponse) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(intent);
        ServerFacade.setInstance(null);
        finish();
    }

    @Override
    public void logoutUnsuccessful(LogoutResponse logoutResponse) {
        Toast.makeText(this, "Failed to logout. " + logoutResponse.getMessage(), LENGTH_LONG).show();
    }

    @Override
    public void unfollowSuccessful(UnfollowResponse unfollowResponse) {
        Toast.makeText(this, "Successfully unfollowed ", LENGTH_LONG).show();
    }

    @Override
    public void unfollowUnsuccessful(UnfollowResponse unfollowResponse) {
        Toast.makeText(this, "Unable to unfollow ", LENGTH_LONG).show();
    }

    @Override
    public void followSuccessful(FollowResponse unfollowResponse) {
        Toast.makeText(this, "Successfully followed ", LENGTH_LONG).show();
    }

    @Override
    public void followUnsuccessful(FollowResponse unfollowResponse) {
        Toast.makeText(this, "Unable to follow ", LENGTH_LONG).show();

    }

    @Override
    public void postSuccessful(PostResponse postResponse) {
        Toast.makeText(this, "Post succeeded", Toast.LENGTH_LONG).show();
    }

    @Override
    public void postUnsuccessful(PostResponse postResponse) {
        Toast.makeText(this, "Post failed", Toast.LENGTH_LONG).show();
    }

    @Override
    public void handleException(Exception exception) {
        Log.e(LOG_TAG, exception.getMessage(), exception);
        Toast.makeText(this, "Failed to logout because of exception: " + exception.getMessage(), LENGTH_LONG).show();
    }
}
