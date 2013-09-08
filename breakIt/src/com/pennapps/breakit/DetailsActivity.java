package com.pennapps.breakit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.pennapps.breakit.MainActivity;

public class DetailsActivity extends Activity {
	
	final static String MESSAGE_ID = "com.pennapps.breakit.DetailsActivity.id";
	final static String MESSAGE_VIDEO_PATH = "com.pennapps.breakit.DetailsActivity.videoPath";
	
	final String TAG = this.getClass().getSimpleName();
	
	String videoPath;
	String entryId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		videoPath = intent.getStringExtra(MESSAGE_VIDEO_PATH);
		entryId = intent.getStringExtra(MESSAGE_ID);
		
		Log.e(TAG, videoPath);
		Log.e(TAG, entryId);
		
		setContentView(R.layout.activity_details);
		
		Button buUpload = (Button) findViewById(R.id.buUpload);
		buUpload.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Upload u = new Upload();
				u.execute(entryId, videoPath);
			}
			
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}