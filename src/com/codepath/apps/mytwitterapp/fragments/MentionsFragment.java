package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class MentionsFragment extends TweetsListFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MyTwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONArray jsonTweets){
				tweets = Tweet.fromJson(jsonTweets);
				getAdapter().addAll(tweets);
			}
		}, lastTweet);
	}
	
	void customLoadMoreDataFromApi(int totalItemCount) {
	 	if (tweets != null){
	 		lastTweet = tweets.get(totalItemCount-1);	
	 	}
		MyTwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler()
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
		}, lastTweet);
		
	}
}
