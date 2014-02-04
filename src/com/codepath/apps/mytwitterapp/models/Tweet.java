package com.codepath.apps.mytwitterapp.models;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;

public class Tweet extends BaseModel implements Serializable{
	
	private static final long serialVersionUID = -7625940712608996885L;
	
	private String body;
	private long uid;
	private boolean favorited;
	private boolean retweeted;
    private User user;
    private String dateTime;

    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getId() {
        return uid;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }
    
    public String getDateTime(){
    	return dateTime;
    }
    
    public long getDateTimeMS(){
    	Date d = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
    	try {
			d = sdf.parse(dateTime);
			long milliseconds = d.getTime();
			return milliseconds;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
    }

    public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
        	tweet.body = jsonObject.getString("text");
        	tweet.uid = jsonObject.getLong("id");
        	tweet.favorited = jsonObject.getBoolean("favorited");
        	tweet.retweeted = jsonObject.getBoolean("retweeted");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));            
            tweet.dateTime = jsonObject.getString("created_at");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            Tweet tweet = Tweet.fromJson(tweetJson);
            if (tweet != null) {
                tweets.add(tweet);
            }
        }

        return tweets;
    }
}
