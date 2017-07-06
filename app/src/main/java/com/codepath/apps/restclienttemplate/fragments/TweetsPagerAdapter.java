package com.codepath.apps.restclienttemplate.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by emilylroth on 7/3/17.
 */
//controls fragement
    //new timeline fragment
    //got rid of get timeline fragment but kept reference to timeline object
    //get instance
    //access timeline object itself
    //on activity result
    //method in fragment that takes a tweet
    //add tweet in timeline fragment
    //remember activity to fragment. fragment to activity never fragment to fragment
public class TweetsPagerAdapter extends FragmentPagerAdapter {
    // return total num of fragments
    private HomeTimelineFragment homeTimelineFragment; // new code
    private String tabTitles[] = new String [] {"Home", "Mentions"};
    private Context context;
    public TweetsPagerAdapter(FragmentManager fm, Context context){
        super(fm);
        homeTimelineFragment = new HomeTimelineFragment(); // get new tweet

        this.context = context;
    }
    public int getCount(){
        return 2;
    }
    // get count
    // return fragment to use

    @Override
    public Fragment getItem(int position) { //make switch meno
        if (position == 0){
            return new HomeTimelineFragment(); //timeline fragment = getTimelineInstance; return timeline fragment
        }else if(position == 1){
            return new MentionTimelineFragment();
        } else{
            return null;
        }
    }

    //return title

    @Override
    public CharSequence getPageTitle(int position) {
        // generate titles bailed on positoin
        return tabTitles[position];

    }
    //pricate timeline get timelineinstance(){ if null will update}
    // if (timelineFrag = null
    // timeline frag = new timeline frag???
}
