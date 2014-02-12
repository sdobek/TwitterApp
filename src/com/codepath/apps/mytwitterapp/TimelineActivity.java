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

public class TimelineActivity extends FragmentActivity {
	
	private final int REQUEST_CODE = 20;
	
	private boolean mReturningWithResult = false;
	
	ArrayList<Tweet> tweets;
	public int tweetIndex;
	public Tweet lastTweet;
	ListView lvTweets;
	public static JSONObject user_data;
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
			public void onSuccess(JSONObject u_data){
				user_data = u_data;
			}
		});
		
		
	}
	
	private void setupNavigationTabs() {
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home")
						.setTag("HomeTimelineFragment").setIcon(R.drawable.ic_home)
						.setTabListener(new FragmentTabListener<HomeTimelineFragment>(R.id.fragmentTweets, this, "home",
                                HomeTimelineFragment.class));
		Tab tabMentions = actionBar.newTab().setText("Mentions")
						.setTag("MentionsTimelineFragment").setIcon(R.drawable.ic_at)
						.setTabListener(new FragmentTabListener<MentionsFragment>(R.id.fragmentTweets, this, "mentions",
                                MentionsFragment.class));

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
	
		
	public void openCompose(MenuItem mi){
		Intent i = new Intent(TimelineActivity.this, ComposeTweetActivity.class);
		User currentUser = User.fromJson(user_data);
		i.putExtra("currentUser", currentUser);
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
		  mReturningWithResult = true;
	  }
	}
	
	protected void onPostResume() {
	    super.onPostResume();
	    if (mReturningWithResult) {
	    	//selects HomeTab and restarts home fragment to reflect update
	    	actionBar.setSelectedNavigationItem(0);
	    	FragmentManager manager = getSupportFragmentManager();
	    	android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
	    	fts.replace(R.id.fragmentTweets, new HomeTimelineFragment());
	    	fts.commit();
	    }

	    // Reset the boolean flag back to false for next time.
	    mReturningWithResult = false;
	}

	
}
