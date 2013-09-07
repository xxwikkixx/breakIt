package com.pennapps.breakit;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;

public class DetailsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		
	}
	
	public class UploadVideo extends Activity {
		private static final int SELECT_VIDEO = 2;
		String selectedPath = "";
		
		@Override
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_details);
			
			openGalleryVideo();
		}
		public void openGalleryVideo(){
			Intent intent = new Intent();
			intent.setType("video/*");
			intent.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(Intent.createChooser(intent,  "Select Video "), SELECT_VIDEO);
		}
		public void onActivityResult(int requestCode, int resultCode, Intent data){
			if (resultCode == RESULT_OK){
				if (requestCode == SELECT_VIDEO)
				{
					Uri selectedImageUri = data.getData();
					selectedPath = getPath()
				}
			}
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}