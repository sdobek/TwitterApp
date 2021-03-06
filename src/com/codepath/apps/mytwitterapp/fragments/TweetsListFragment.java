package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.TimelineActivity;
import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class TweetsListFragment extends Fragment {
	
	ArrayList<Tweet> tweets;
	public Tweet lastTweet;
	ListView lvTweets;
	private TweetsAdapter adapter;
	
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
      return inf.inflate(R.layout.fragment_tweets_list, parent, false);
    }
	
	 @Override 
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //getActivity to access methods in the linked activity
        ArrayList<Tweet> tweets = new ArrayList<Tweet>();
        adapter = new TweetsAdapter(getActivity(), tweets);
        lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
        lvTweets.setAdapter(adapter);
        lastTweet = null;
        
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
	        @Override
	        public void onLoadMore(int page, int totalItemsCount) {
	        	customLoadMoreDataFromApi(totalItemsCount); 
	        }
	    });
        		
	 }	
	 
	abstract void customLoadMoreDataFromApi(int totalItemsCount);
	 

	public TweetsAdapter getAdapter(){
		 return adapter;
	}
	 

}
