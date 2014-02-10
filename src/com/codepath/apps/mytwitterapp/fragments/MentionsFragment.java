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
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MyTwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONArray jsonTweets){
				tweets = Tweet.fromJson(jsonTweets);
				getAdapter().addAll(tweets);
			}
		}, lastTweet);
	}
	
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	        	customLoadMoreDataFromApi(totalItemsCount); 
	        }
	    });
	}
	
	private void customLoadMoreDataFromApi(int page) {
	 	if (tweets != null){
	 		lastTweet = tweets.get(tweets.size()-1);	
	 	}
		MyTwitterApp.getRestClient().getMentions(new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONArray jsonTweets){
				if (tweets == null){
					tweets = Tweet.fromJson(jsonTweets);
					adapter.addAll(tweets);
					adapter.notifyDataSetChanged();
				}
				else {
					ArrayList<Tweet> newTweets = Tweet.fromJson(jsonTweets);
					tweets.addAll(newTweets);
					adapter.addAll(newTweets);
					adapter.notifyDataSetChanged();						
				}				
			}
		}, lastTweet);
		
	}
}
