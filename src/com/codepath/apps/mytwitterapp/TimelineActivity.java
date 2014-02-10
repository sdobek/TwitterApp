package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.codepath.apps.mytwitterapp.fragments.HomeTimelineFragment;
import com.codepath.apps.mytwitterapp.fragments.MentionsFragment;
import com.codepath.apps.mytwitterapp.fragments.TweetsListFragment;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TimelineActivity extends FragmentActivity implements TabListener{
	
	private final int REQUEST_CODE = 20;
	
	private boolean mReturningWithResult = false;
	
	ArrayList<Tweet> tweets;
	public static int tweetIndex;
	public static Tweet lastTweet;
	ListView lvTweets;
	private static JSONObject user_data;
	TweetsAdapter adapter;
	TweetsListFragment fragmentTweets;
	ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
		
		fragmentTweets = (TweetsListFragment) getSupportFragmentManager().
							findFragmentById(R.id.fragmentTweets);
		MyTwitterApp.getRestClient().getUserData(new JsonHttpResponseHandler()
		{
			@Override
			public void onSuccess(JSONObject user_data){
				TimelineActivity.user_data = user_data;
			}
		});
		
		
	}
	
	private void setupNavigationTabs() {
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home")
						.setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home)
						.setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions")
						.setTag("MentionsTimelineFragment").setIcon(R.drawable.ic_at)
						.setTabListener(this);

		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		//getMenuInflater().inflate(R.menu.actionbar_compose, menu);
		return true;
	}
	
		
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//	    // Handle presses on the action bar items
//	    switch (item.getItemId()) {
//	        case R.id.menu_compose:
//	            openCompose();
//	            return true;
//	        default:
//	            return super.onOptionsItemSelected(item);
//	    }
//	}
	
	public void openCompose(MenuItem mi){
		Intent i = new Intent(TimelineActivity.this, ComposeTweetActivity.class);
		User currentUser = User.fromJson(user_data);
		i.putExtra("currentUser", currentUser);
//		startActivity(i);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	public void onProfileView(MenuItem mi){
		Intent i = new Intent(this, ProfileActivity.class);
		User currentUser = User.fromJson(user_data);
		i.putExtra("currentUser", currentUser);
		startActivity(i);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//	     Tweet t = (Tweet) data.getSerializableExtra("tweet");
//	     tweets.add(0, t);
//	     adapter = new TweetsAdapter(getBaseContext(), tweets);
//	     lvTweets.setAdapter(adapter);
//		 lvTweets.setSelection(0);
		  
		  mReturningWithResult = true;
		 //attempt 1
		 //actionBar.setSelectedNavigationItem(0);
		 //attempt 2
		 //Tab t = actionBar.getTabAt(0);
		 //onTabSelected(t, null);
	     
	  }
	}
	
	protected void onPostResume() {
	    super.onPostResume();
	    if (mReturningWithResult) {
	    	//actionBar.setSelectedNavigationItem(0);
	    	Tab t = actionBar.getTabAt(0);
			onTabSelected(t, null);
	    }

	    // Reset the boolean flag back to false for next time.
	    mReturningWithResult = false;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
//		FragmentManager manager = getSupportFragmentManager();
//		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
//		if (tab.getTag() == "HomeTimelineFragment"){
//			fts.replace(R.id.fragmentTweets, new HomeTimelineFragment());
//		}
//		else if (tab.getTag() == "MentionsTimelineFragment"){
//			fts.replace(R.id.fragmentTweets, new MentionsFragment());
//		}
//		fts.commit();
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if (tab.getTag() == "HomeTimelineFragment"){
			fts.replace(R.id.fragmentTweets, new HomeTimelineFragment());
		}
		else if (tab.getTag() == "MentionsTimelineFragment"){
			fts.replace(R.id.fragmentTweets, new MentionsFragment());
		}
		fts.commit();
		
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
		// TODO Auto-generated method stub
		
	}

	
}
