package edu.byu.cs.tweeter.view.main;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.Console;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.service.request.LoginRequest;
import edu.byu.cs.tweeter.model.service.request.LogoutRequest;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.request.RegisterRequest;
import edu.byu.cs.tweeter.model.service.response.LogoutResponse;
import edu.byu.cs.tweeter.model.service.response.PostResponse;
import edu.byu.cs.tweeter.presenter.LoginPresenter;
import edu.byu.cs.tweeter.presenter.LogoutPresenter;
import edu.byu.cs.tweeter.presenter.PostPresenter;
import edu.byu.cs.tweeter.presenter.RegisterPresenter;
import edu.byu.cs.tweeter.view.LoginActivity;
import edu.byu.cs.tweeter.view.asyncTasks.LoginTask;
import edu.byu.cs.tweeter.view.asyncTasks.LogoutTask;
import edu.byu.cs.tweeter.view.asyncTasks.PostTask;
import edu.byu.cs.tweeter.view.asyncTasks.RegisterTask;
import edu.byu.cs.tweeter.view.main.status.PostDialogueFragment;
import edu.byu.cs.tweeter.view.util.ImageUtils;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements LogoutPresenter.View, LogoutTask.Observer, PostPresenter.View, PostTask.Observer {

    public static final String CURRENT_USER_KEY = "CurrentUser";
    public static final String AUTH_TOKEN_KEY = "AuthTokenKey";
    private LogoutPresenter logoutPresenter;
    private static final String LOG_TAG = "MainActivity";
    Dialog newPost;
    private PostPresenter postPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        postPresenter = new PostPresenter(this);
        logoutPresenter = new LogoutPresenter(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = (User) getIntent().getSerializableExtra(CURRENT_USER_KEY);
        if(user == null) {
            throw new RuntimeException("User not passed to activity");
        }

        AuthToken authToken = (AuthToken) getIntent().getSerializableExtra(AUTH_TOKEN_KEY);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), user, authToken);
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
                newPost = new Dialog(MainActivity.this);
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
                        Toast.makeText(MainActivity.this, "Posting... ", Toast.LENGTH_LONG).show();
                        PostRequest postRequest = new PostRequest(body.toString());
                        PostTask postTask = new PostTask(postPresenter, MainActivity.this);
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
            LogoutTask logoutTask = new LogoutTask(logoutPresenter, MainActivity.this);
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
        Toast.makeText(this, "Failed to logout. " + logoutResponse.getMessage(), Toast.LENGTH_LONG).show();
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
        Toast.makeText(this, "Failed to logout because of exception: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}