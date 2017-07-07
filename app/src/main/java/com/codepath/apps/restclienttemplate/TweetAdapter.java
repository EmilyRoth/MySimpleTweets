package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

/**
 * Created by emilylroth on 6/26/17.
 */

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{
    public List<Tweet> mTweets;
    Context context;
    private final int REQUEST_CODE = 20;
    TwitterClient client;
    TweetAdapterListener mListener;


    public interface TweetAdapterListener{
        public void onItemSelected(View view, int position, String clickID);
    }
    // pass in tweets array into constructor
    public TweetAdapter(List<Tweet> tweets, TweetAdapterListener listener){
        mTweets = tweets;
        mListener = listener;
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

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //data
        final Tweet tweet = mTweets.get(position);

        //populate views'
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvHandle.setText(tweet.user.screenName);
        holder.tvTime.setText(getRelativeTimeAgo(tweet.createdAt));
        holder.ivReply.setTag(tweet.user.screenName);
        holder.tvLikeCount.setText(Long.toString(tweet.favCount));
        holder.tvRTCount.setText(Long.toString(tweet.RTCount));

        client = TwitterApp.getRestClient();


        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);
          Glide.with(context).load(tweet.mediaURL).into(holder.ivMedia);

        holder.ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ComposeActivity.class);
                //startActivity(i)
                i.putExtra("tvHandle", holder.ivReply.getTag().toString());
                ((Activity)context).startActivityForResult(i, REQUEST_CODE);
            }
        });

        holder.ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, FollowingActivity.class);
                //startActivity(i)
                i.putExtra("uid", (tweet.user.uid));
                i.putExtra("screen_name", holder.tvHandle.getText().toString());
                i.putExtra("user_name", tweet.user.name );
                i.putExtra("followers_count", tweet.user.followersCount);
                i.putExtra("following_count", tweet.user.followingCount);
                i.putExtra("tagLine", tweet.user.tagLine);
                i.putExtra("profileUrl", tweet.user.profileImageUrl);
                ((Activity)context).startActivity(i);
            }
        });
        holder.ivLike.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(tweet.favStatus){
                    client.unlike(Long.toString(tweet.uid), new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            tweet.favStatus = false;
                            holder.ivLike.setImageResource(R.drawable.ic_vector_heart_stroke);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });

                } else {
                    client.like(Long.toString(tweet.uid), new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            tweet.favStatus = true;
                            holder.ivLike.setImageResource(R.drawable.ic_vector_heart);
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                }


            }
        });
        holder.ivRT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tweet.RTStatus) {
                    client.unretweet(Long.toString(tweet.uid), new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                            holder.ivRT.setImageResource(R.drawable.ic_vector_retweet);
                            tweet.RTStatus = false;
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                        }
                    });
                }else{
                client.retweet(Long.toString(tweet.uid), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                        holder.ivRT.setImageResource(R.drawable.ic_vector_retweet);
                        tweet.RTStatus = true;
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
            }
        });


    }
    public static StateListDrawable makeSelector(int color) {
        StateListDrawable res = new StateListDrawable();
        res.setExitFadeDuration(400);
        res.setAlpha(45);
        res.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(color));
        res.addState(new int[]{}, new ColorDrawable(Color.TRANSPARENT));
        return res;
    }

    //view holder class
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvHandle;
        public TextView tvTime;
        public ImageView ivReply;
        public ImageView ivLike;
        public ImageView ivRT;
        public TextView tvLikeCount;
        public TextView tvRTCount;
        public ImageView ivMedia;


        public ViewHolder(View itemView){
            super(itemView);
            //perform find view by ID
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUserName);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvHandle = (TextView) itemView.findViewById(R.id.tvHandle);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            ivReply = (ImageView) itemView.findViewById(R.id.ivReply);
            ivLike = (ImageView) itemView.findViewById(R.id.ivLike);
            ivRT = (ImageView) itemView.findViewById(R.id.ivRT);
            tvLikeCount = (TextView) itemView.findViewById(R.id.tvLikeCount);
            tvRTCount = (TextView) itemView.findViewById(R.id.tvRTCount);
            ivMedia = (ImageView) itemView.findViewById(R.id.ivMedia);


        }
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Tweet detail = mTweets.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, detailActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Tweet.class.getName(), Parcels.wrap(detail));
                intent.putExtra("uid", detail.getUid());


                // show the activity
                context.startActivity(intent);
            }
        }
    }

    @Override
    public int getItemCount(){
        return mTweets.size();
    }

    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }

}
