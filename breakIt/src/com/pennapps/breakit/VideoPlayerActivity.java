package com.pennapps.breakit;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
	final static String MESSAGE_URL = "com.pennapps.breakit.VideoPlayerActivity.url";
	final static String MESSAGE_TITLE = "com.pennapps.breakit.VideoPlayerActivity.title";
	
	final String TAG = this.getClass().getSimpleName();
	String url = "";
	String title = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		url = intent.getStringExtra(MESSAGE_URL);
		title = intent.getStringExtra(MESSAGE_TITLE);
		setContentView(R.layout.activity_video_player);
		TextView titleTextView = (TextView) findViewById(R.id.textView1);
		titleTextView.setText(title);
		
		VideoView videoView = (VideoView) findViewById(R.id.video);
		MediaController mc = new MediaController(this);
		mc.setAnchorView(videoView);
		mc.setMediaPlayer(videoView);
		Log.e(TAG,url);
		Uri video = Uri.parse(url);
		videoView.setMediaController(mc);
		videoView.setVideoURI(video);
		videoView.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_player, menu);
		return true;
	}

}
