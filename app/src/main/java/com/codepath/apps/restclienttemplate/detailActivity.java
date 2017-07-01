package com.codepath.apps.restclienttemplate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class detailActivity extends AppCompatActivity {
    public ImageView ivProfileImage;
    public TextView tvUserName;
    public TextView tvHandle;
    public TextView tvBody;
    public TextView tvTime;
    public ImageView ivReply;
    public ImageView ivLike;
    public ImageView ivRetweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvHandle = (TextView) findViewById(R.id.tvHandle);
        tvBody = (TextView) findViewById(R.id.tvBody);
        tvTime = (TextView) findViewById(R.id.tvTime);
        //==ivReply = (Tex)

    }
    private List<Tweet> mTweets;
    Context context;
    private final int REQUEST_CODE = 20;


    // pass in tweets array into constructor
    public detailActivity(List<Tweet> tweets){
        mTweets = tweets;
    }
    //for each row inflate the layout and cache references into view holder

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


    public void onBindViewHolder(final ViewHolder holder, int position) {
        //data
        Tweet tweet = mTweets.get(position);

        //populate views'
        holder.tvUsername.setText(tweet.user.name);
        holder.tvBody.setText(tweet.body);
        holder.tvHandle.setText(tweet.user.screenName);
        holder.tvTime.setText(getRelativeTimeAgo(tweet.createdAt));
        holder.ivReply.setTag(tweet.user.screenName);

        Glide.with(context).load(tweet.user.profileImageUrl).into(holder.ivProfileImage);


        holder.ivReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ComposeActivity.class);
                //startActivity(i)
                i.putExtra("tvHandle", holder.ivReply.getTag().toString());
                ((Activity)context).startActivityForResult(i, REQUEST_CODE);
            }
        });
        holder.ivLike.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

            }
        });

    }

    //view holder class
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvBody;
        public TextView tvHandle;
        public TextView tvTime;
        public ImageView ivReply;
        public ImageView ivLike;


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
        }
    }

}
