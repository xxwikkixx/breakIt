package com.pennapps.breakit;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

import com.pennapps.breakit.MainActivity;

public class DetailsActivity extends Activity implements OnTaskCompleteListener {
	
	//final static String MESSAGE_ID = "com.pennapps.breakit.DetailsActivity.id";
	//final static String MESSAGE_VIDEO_PATH = "com.pennapps.breakit.DetailsActivity.videoPath";
	final static String MESSAGE_REPO = "com.pennapps.breakit.DetailsActivity.repo";
	
	final String TAG = this.getClass().getSimpleName();
	
	String reponame;
	FastRepo repo;
	String videoPath;
	String entryId;
	String thumbPath;
	DetailsActivity context = this;
	
	private Button buUpload;
	private ProgressBar indicator;
	private PopupWindow upload_popup;
	private View layout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		
		reponame = intent.getStringExtra(MESSAGE_REPO);		
		repo = FastRepo.getRepo(reponame);
		videoPath = repo.get("videoPath");
		entryId = repo.get("entryId");
		/*videoPath = intent.getStringExtra(MESSAGE_VIDEO_PATH);
		entryId = intent.getStringExtra(MESSAGE_ID);*/
		thumbPath = Environment.getExternalStorageDirectory()+"/breakit_thumbcache.jpg";
		File outFile = new File(thumbPath);
		if (outFile.exists())
			outFile.delete();
		
		Log.e(TAG, videoPath);
		Log.e(TAG, entryId);
		
		setContentView(R.layout.activity_details);
		buUpload = (Button) findViewById(R.id.buUpload);
		indicator = (ProgressBar) findViewById(R.id.uploadProgress);
		indicator.setVisibility(View.INVISIBLE);
		
		Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Video.Thumbnails.MINI_KIND);
		try {
			FileOutputStream out = new FileOutputStream(thumbPath);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		LayoutInflater inflater = (LayoutInflater) DetailsActivity.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		layout = inflater.inflate(R.layout.upload_popup, (ViewGroup)findViewById(R.id.upload_popup));
		
		upload_popup = new PopupWindow(layout, 200, 300, true);		
		
		buUpload.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				String about = ((EditText) findViewById(R.id.editText1)).getText().toString();
				Upload u = new Upload(context);
				u.execute(entryId, videoPath, thumbPath, about);
				indicator.setVisibility(View.VISIBLE);
				buUpload.setEnabled(false);
			}
			
		});
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	public void onTaskComplete(Object resultArg) {
		Log.e(TAG, "upload complete!");
		buUpload.setEnabled(true);
		indicator.setVisibility(View.INVISIBLE);
		
		upload_popup.showAtLocation(layout, Gravity.CENTER, 0, 0);
		
		Button btnUploadPopupOK = (Button) layout.findViewById(R.id.upload_poup_OK_button); 
		btnUploadPopupOK.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				upload_popup.dismiss();
				setResult(RESULT_OK);
				finish();
			}			
		});
		
	}
}