package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.restclienttemplate.fragments.TweetsPagerAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity {



    private final int REQUEST_CODE = 20;
    //private final int RESULT_OK = 0;

    public String body;
    public long uid;
    public User user;
    public String createdAt;
    private SwipeRefreshLayout swipeContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setContentView(R.layout.activity_timeline);
 /*       swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
              //  fetchTimelineAsync(0);
            }

        });
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

*/

        // Setup refresh listener which triggers new data loading
        //get view pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set the adapter
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager(), this));
        //set up tablayout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);


    }

/*    public void fetchTimelineAsync(int page) {
        // Send the network request to fetch the updated data
        // `client` here is an instance of Android Async HTTP
        // getHomeTimeline is an example endpoint.

        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // Remember to CLEAR OUT old items before appending in the new ones
                tweetAdapter.clear();
                // ...the data has come back, add new items to your adapter...
                //tweetAdapter.addAll(response);
                for(int i = 0; i < response.length(); i++){
                    //convert object to tweet
                    //add model to data source
                    //notify the adapter that we've added an item

              /*      try {
                        Tweet tweet = Tweet.fromJSON(response.getJSONObject(i));
                        tweets.add(tweet);
                        tweetAdapter.notifyItemInserted(tweets.size()-1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                // Now we call setRefreshing(false) to signal refresh has finished
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }


        });
    }*/


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        if(item.getItemId() == R.id.miCompose){
            Intent i = new Intent(TimelineActivity.this, ComposeActivity.class);
            //startActivity(i);
            startActivityForResult(i, REQUEST_CODE);
        }
        return true;
    }
    public void onProfileView(MenuItem item){
    //launch profile view
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            //String name = data.getExtras().getString("body");
            //int code = data.getExtras().getInt("code", 0);
            // Toast the name to display temporarily on screen
            //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));

            //timeline fragment to get item
        }    }

    //@Override
 /*   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            //String name = data.getExtras().getString("body");
            //int code = data.getExtras().getInt("code", 0);
            // Toast the name to display temporarily on screen
            //Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
            tweets.add(0, tweet);
            tweetAdapter.notifyItemInserted(0);
            rvTweets.scrollToPosition(0);
            //timeline fragment to get item
        }
    }
*/


}
