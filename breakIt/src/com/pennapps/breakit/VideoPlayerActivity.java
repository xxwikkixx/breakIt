package com.pennapps.breakit;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class VideoPlayerActivity extends Activity {
	final static String MESSAGE_URL = "com.pennapps.breakit.VideoPlayerActivity.url";
	final static String MESSAGE_TITLE = "com.pennapps.breakit.VideoPlayerActivity.title";
	final static String MESSAGE_ID = "com.pennapps.breakit.VideoPlayerActivity.id";
	
	final String TAG = this.getClass().getSimpleName();
	final int REQUEST_CAMERA_VIDEO = 1;
	String url = "";
	String title = "";
	String entryId = "";
	Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		url = intent.getStringExtra(MESSAGE_URL);
		title = intent.getStringExtra(MESSAGE_TITLE);
		entryId = intent.getStringExtra(MESSAGE_ID);
		setContentView(R.layout.activity_video_player);		
		
		//Initialize title
		TextView titleTextView = (TextView) findViewById(R.id.textView1);
		titleTextView.setText(title);
		//Initialize break button
		Button breakButton = (Button) findViewById(R.id.breakButton);
		breakButton.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				
				VideoView videoView = (VideoView) findViewById(R.id.video);
				videoView.stopPlayback();
				Intent cameraScreen = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				startActivityForResult(cameraScreen, REQUEST_CAMERA_VIDEO);
			}
		 });
		//Initialize player
		VideoView videoView = (VideoView) findViewById(R.id.video);
		MediaController mc = new MediaController(this);
		mc.setAnchorView(videoView);
		mc.setMediaPlayer(videoView);
		Log.e(TAG,url);
		Log.e(TAG,entryId);
		Uri video = Uri.parse(url);
		videoView.setMediaController(mc);
		videoView.setVideoURI(video);
		videoView.start();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch(requestCode) {
		case REQUEST_CAMERA_VIDEO:
			if (resultCode == RESULT_OK) {
				Uri videouri = data.getData();
				Log.d(TAG, videouri.toString());
				
				String path = videouri.getPath();
				Intent intent = new Intent(context, DetailsActivity.class);
				intent.putExtra(DetailsActivity.MESSAGE_VIDEO_PATH, path);
				intent.putExtra(DetailsActivity.MESSAGE_ID,entryId);
			    startActivity(intent);
			}
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.video_player, menu);
		return true;
	}

}
