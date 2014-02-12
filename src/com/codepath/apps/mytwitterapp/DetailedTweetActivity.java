package com.codepath.apps.mytwitterapp;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.os.Bundle;
import android.app.Activity;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailedTweetActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detailed_tweet);
		
		Tweet t = (Tweet) getIntent().getSerializableExtra("tweet");
		loadDetailTweetInfo(t);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.detailed_tweet, menu);
		return true;
	}
	
	private void loadDetailTweetInfo(Tweet t) {
		User u = t.getUser();
		
		getActionBar().setTitle("@"+u.getScreenName());	
		
		ImageView ivProfile = (ImageView) findViewById(R.id.ivReply);
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfile);
		
		TextView tvName = (TextView) findViewById(R.id.tvTweetName);
		tvName.setText(u.getName());
		
		TextView bodyView = (TextView) findViewById(R.id.tvTweetDetailed);
		bodyView.setText(Html.fromHtml(t.getBody()));
		
		String dateTime = (String) DateUtils.getRelativeDateTimeString(
							this, t.getDateTimeMS(),  DateUtils.MINUTE_IN_MILLIS,  
							DateUtils.WEEK_IN_MILLIS, 0);
		TextView dateView = (TextView) findViewById(R.id.tvTweetDate);
		dateView.setText(dateTime);
	}

}
