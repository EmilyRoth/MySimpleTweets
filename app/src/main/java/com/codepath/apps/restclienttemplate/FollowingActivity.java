package com.codepath.apps.restclienttemplate;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineFragment;

/**
 * Created by emilylroth on 7/5/17.
 */

public class FollowingActivity extends AppCompatActivity {
    TwitterClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String screenName = getIntent().getStringExtra("screen_name");
        String userName = getIntent().getStringExtra("user_name");
        String tagLine = getIntent().getStringExtra("tagLine");
        int followingCount = getIntent().getIntExtra("following_count", 0);
        int followerCount = getIntent().getIntExtra("followers_count", 0);
        String profileImage = getIntent().getStringExtra("profileUrl");

        long userID = getIntent().getLongExtra("uid", 0);

        // create user fragment
        UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(screenName);
        //display user timeline fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        //make changes
        ft.replace(R.id.flContainer, userTimelineFragment);

        //commit
        ft.commit();
        client = TwitterApp.getRestClient();
        populateUserHeadline(screenName, userName, tagLine, followerCount, followingCount, profileImage);

    }
    public void populateUserHeadline(String screenName, String userName, String tagLine, int followersCount, int followingCount, String profileImage){
        TextView tvName = (TextView) findViewById(R.id.tvName);
        TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
        TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
        TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);


        ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvName.setText(userName);

        //load profule image
        tvTagline.setText(tagLine);
        tvFollowers.setText(followersCount + " Followers");
        tvFollowing.setText(followingCount + " Following");

        Glide.with(this).load(profileImage).into(ivProfileImage);

    }
}
