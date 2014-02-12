package com.codepath.apps.mytwitterapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.fragments.TweetsListFragment;
import com.codepath.apps.mytwitterapp.fragments.UserTimelineFragment;
import com.codepath.apps.mytwitterapp.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {
	
	private User currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		
		currentUser = (User) getIntent().getSerializableExtra("currentUser");
		loadProfileInfo(currentUser);
		
		UserTimelineFragment fragmentUserTimeline = (UserTimelineFragment) 
		         getSupportFragmentManager().findFragmentById(R.id.fragmentUserTimeline);
		fragmentUserTimeline.setUser(currentUser);
	}

	private void loadProfileInfo(User u) {
		getActionBar().setTitle("@"+u.getScreenName());	
		ImageView ivProfile = (ImageView) findViewById(R.id.iv_ProfileImage);
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfile);
		TextView tvName = (TextView) findViewById(R.id.tv_ProfileId);
		tvName.setText(u.getName());
		TextView tvTagline = (TextView) findViewById(R.id.tv_Tagline);
		tvTagline.setText(u.getTagline());
		TextView tvFollowers = (TextView) findViewById(R.id.tv_Followers);
		tvFollowers.setText(u.getFollowersCount()+" Followers");
		TextView tvFollowing = (TextView) findViewById(R.id.tv_Following);
		tvFollowing.setText(u.getFriendsCount()+" Following");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
