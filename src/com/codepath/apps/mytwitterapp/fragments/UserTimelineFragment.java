package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.ProfileActivity;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;


public class UserTimelineFragment extends TweetsListFragment {
	private User current;
		
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyTwitterApp.getRestClient().getUserTimeline(new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONArray jsonTweets){
				tweets = Tweet.fromJson(jsonTweets);
				getAdapter().addAll(tweets);
			}
		}, current, lastTweet);
	}
	
	void customLoadMoreDataFromApi(int totalItemCount) {
		if (tweets != null){
	 		lastTweet = tweets.get(totalItemCount-1);	
	 	}
		MyTwitterApp.getRestClient().getUserTimeline(new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONArray jsonTweets){
				if (tweets == null){
					tweets = Tweet.fromJson(jsonTweets);
					getAdapter().addAll(tweets);
					getAdapter().notifyDataSetChanged();
				}
				else {
					ArrayList<Tweet> newTweets = Tweet.fromJson(jsonTweets);
					tweets.addAll(newTweets);
					getAdapter().addAll(newTweets);
					getAdapter().notifyDataSetChanged();						
				}				
			}
		}, current, lastTweet);
	}
	
	public void setUser(User u){
		current = u;
	}
}
