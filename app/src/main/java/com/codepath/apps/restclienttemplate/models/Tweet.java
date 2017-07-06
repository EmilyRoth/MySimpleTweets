package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by emilylroth on 6/26/17.
 */
@Parcel
public class Tweet{
    // list out attributes
    public String body;
    public long uid;
    public User user;
    public String createdAt;
    public long RTCount;
    public long favCount;
    public boolean RTStatus;
    public boolean favStatus;
    public String mediaURL;

    public Tweet(){

    }
    //deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) throws JSONException{
        Tweet tweet = new Tweet();

        tweet.body = jsonObject.getString("text");
        tweet.uid = jsonObject.getLong("id");
        tweet.createdAt = jsonObject.getString("created_at");
        tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        tweet.RTCount = jsonObject.getLong("retweet_count");
        tweet.favCount = jsonObject.getLong("favorite_count");
        tweet.RTStatus = jsonObject.getBoolean("retweeted");
        tweet.favStatus = jsonObject.getBoolean("favorited");
        if(jsonObject.has("entitiess")){
            String[] types = {"urls", "media"};
            tweet.mediaURL = ((JSONObject) jsonObject.getJSONObject("entities").getJSONArray("media").get(0)).getString("media_url");
        }
        return tweet;

    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getRTCount() {
        return RTCount;
    }

    public long getFavCount() {
        return favCount;
    }

    public boolean isRTStatus() {
        return RTStatus;
    }

    public boolean isFavStatus() {
        return favStatus;
    }
}
