package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by emilylroth on 7/3/17.
 */

public class TweetsListFragment extends Fragment implements  TweetAdapter.TweetAdapterListener {
    //inflation happens inside onCreateView
    public TweetAdapter tweetAdapter;
    public ArrayList tweets;
    public RecyclerView rvTweets;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //inflate layout
        View v = inflater.inflate(R.layout.fragment_tweets, container, false);
        //find recycler view
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweet);
        //init arraylist
        tweets = new ArrayList<>();
        //construct adapter
        tweetAdapter = new TweetAdapter(tweets, this);
        //RecyclerView setup (layout manager, use adapter)
        rvTweets.setLayoutManager(new LinearLayoutManager(getContext()));
        // set the adapter
        rvTweets.setAdapter(tweetAdapter);
        return v;
    }

    public void addItems(JSONArray response){
        for(int i = 0; i < response.length(); i++){
            //convert object to tweet
            //add model to data source
            //notify the adapter that we've added an item
            Tweet tweet = null;
            try {
                tweet = Tweet.fromJSON(response.getJSONObject(i));
                tweets.add(tweet);
                tweetAdapter.notifyItemInserted(tweets.size()-1);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onItemSelected(View view, int position, String clickID) {

    }
}
