package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.activeandroid.util.Log;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends Activity {
	
	private final int REQUEST_CODE = 20;
	
	ArrayList<Tweet> tweets;
	public static int tweetIndex;
	public static Tweet lastTweet;
	ListView lvTweets;
	private static JSONObject user_data;
	TweetsAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		tweets = null;
		tweetIndex = 0;
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		user_data = null;
		
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONArray jsonTweets){
				if (tweets == null){
					tweets = Tweet.fromJson(jsonTweets);
				}
				else {
					tweets.addAll(Tweet.fromJson(jsonTweets));
				}
				
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
			}
		});
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        
	        public void onLoadMore(int page, int totalItemsCount) {
	        	tweetIndex = totalItemsCount;
	        	lastTweet = tweets.get(tweetIndex-1);
	            customLoadMoreDataFromApi(totalItemsCount); 
	        }
	    });
		
		MyTwitterApp.getRestClient().getUserData(new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONObject user_data){
				TimelineActivity.user_data = user_data;
			}
		});
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		//getMenuInflater().inflate(R.menu.actionbar_compose, menu);
		return true;
	}
	
	private void customLoadMoreDataFromApi(int page) {
		MyTwitterApp.getRestClient().getHomeTimeline(new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONArray jsonTweets){
				if (tweets == null){
					tweets = Tweet.fromJson(jsonTweets);
				}
				else {
					tweets.addAll(Tweet.fromJson(jsonTweets));
				}
				
				ListView lvTweets = (ListView) findViewById(R.id.lvTweets);
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
				lvTweets.setSelection(tweetIndex);
			}
		});
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.menu_compose:
	            openCompose();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void openCompose(){
		System.out.print("here");
		Intent i = new Intent(TimelineActivity.this, ComposeTweetActivity.class);
		User currentUser = User.fromJson(user_data);
		i.putExtra("currentUser", currentUser);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
	     Tweet t = (Tweet) data.getSerializableExtra("tweet");
	     tweets.add(0, t);
	     adapter = new TweetsAdapter(getBaseContext(), tweets);
	     lvTweets.setAdapter(adapter);
		 lvTweets.setSelection(0);
	     
	  }
	} 

}
