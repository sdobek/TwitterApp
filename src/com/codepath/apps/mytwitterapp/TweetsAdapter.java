package com.codepath.apps.mytwitterapp;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.content.Intent;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet>{
	private final int REQUEST_CODE = 20;
	
	public TweetsAdapter(Context context, List<Tweet> tweets){
		super(context, 0, tweets);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		View view = convertView;
		if (view == null){
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.tweet_item, null);
		}
		
		Tweet tweet = getItem(position);
		User user = tweet.getUser();
		
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		imageView.setTag(user);
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getContext().getClass() != ProfileActivity.class){
					Intent i = new Intent(getContext(), ProfileActivity.class);
					User u = (User) v.getTag();
					i.putExtra("currentUser", u);
					getContext().startActivity(i);
				}
			}
		});

		
		
		TextView nameView = (TextView) view.findViewById(R.id.tvTweetName);
		String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + " <small><font color='#777'>@" +
				tweet.getUser().getScreenName() + "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));
		
		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		
		String dateTime = (String) DateUtils.getRelativeDateTimeString(
							getContext(), tweet.getDateTimeMS(),  DateUtils.MINUTE_IN_MILLIS,  
							DateUtils.WEEK_IN_MILLIS, 0);
		TextView dateView = (TextView) view.findViewById(R.id.tvDateTime);
		dateView.setText(dateTime);
		
		ImageView replyImage = (ImageView) view.findViewById(R.id.ivReply);
		replyImage.setTag(user);
		replyImage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (getContext().getClass() != ProfileActivity.class){
					Intent i = new Intent(getContext(), ComposeTweetActivity.class);
					User u = (User) v.getTag();
					i.putExtra("username", u.getScreenName());
					User currentUser = User.fromJson(TimelineActivity.user_data);
					i.putExtra("currentUser", currentUser);
					((FragmentActivity) getContext()).startActivityForResult(i, REQUEST_CODE);
				}
			}
		});
		
		view.setTag(tweet);
		view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getContext(), DetailedTweetActivity.class);
				Tweet t = (Tweet) v.getTag();
				i.putExtra("tweet", t);
				getContext().startActivity(i);
			}
		});
		return view;
	}

}
