package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

/**
 * Created by emilylroth on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{
    private List<Tweet> mTweets;
    Context context;

    // pass in tweets array into constructor
    public TweetAdapter(List<Tweet> tweets){
        mTweets = tweets;
    }
    //for each row inflate the layout and cache references into view holder

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;

    }

    //bind tweet object

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //data
        Tweet tweet = mTweets.get(position);

        //populate views'
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
    //    holder.tvHandle.setText(tweet.user.screenName);

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);

    }

    //view holder class
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvHandle;


        public ViewHolder(View itemView){
            super(itemView);
            //perform find view by ID
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
           // tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);
        }
    }

    @Override
    public int getItemCount(){
        return mTweets.size();
    }
}
