package com.codepath.apps.mytwitterapp;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ComposeTweetActivity extends Activity {
	
	EditText bodyView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		
		User u = (User) getIntent().getSerializableExtra("currentUser");
		
		ImageView imageView = (ImageView) findViewById(R.id.ivUserPic);
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), imageView);
		
		TextView nameView = (TextView) findViewById(R.id.tv_twitterName);
		String formattedName = "<small><font color='#777'>@" + u.getScreenName() + "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));
		
		bodyView = (EditText) findViewById(R.id.et_tweet);
	}
	
	public void onPostTweet(View v){
		String tweet = bodyView.getText().toString();
		if ( !(tweet.equals("")) ){
			MyTwitterApp.getRestClient().postUserTweet(new JsonHttpResponseHandler()
			{
				@Override
				public void onSuccess(JSONObject jsonTweet){
					Tweet postedTweet = Tweet.fromJson(jsonTweet);
					Intent i = new Intent();
					i.putExtra("tweet", postedTweet);
					setResult(RESULT_OK, i);
//					setResult(RESULT_OK);
					finish();
				}
			}, 
			tweet);
		}
	}
	
	public void onCancelTweet(View v){
		setResult(RESULT_CANCELED); 
		finish(); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}
	
}
