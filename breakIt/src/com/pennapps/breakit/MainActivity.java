package com.pennapps.breakit;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.VideoView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {
	final static String REPONAME = "com.pennapps.breakit.key.fastrepo";
	final String TAG = this.getClass().getSimpleName();
	final Context context = this;
	
	private SitesAdapter mAdapter;
	private ListView sitesList;
	
	 private VideoView video;
	 private MediaController ctlr;
	
	final int REQUEST_CAMERA_VIDEO = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getAccountNames();
		Button buRecord = (Button) findViewById(R.id.buRecord);
		
		 buRecord.setOnClickListener(new View.OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent cameraScreen = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
				startActivityForResult(cameraScreen, REQUEST_CAMERA_VIDEO);
				
				//Intent cameraScreen = new Intent(getApplicationContext(), CameraActivity.class); //button click to next screen
				//startActivity(cameraScreen);
			}
		 });
		
		
		 sitesList = (ListView)findViewById(R.id.sitesList);
			
			//Set the click listener to launch the browser when a row is clicked.
			sitesList.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
					StackSite stackSite = mAdapter.getItem(pos);
					String url = stackSite.getLink();
					String title = stackSite.getAbout()+" "+stackSite.getName();
					String entryId = stackSite.getEntryId();
					Intent intent = new Intent(context, VideoPlayerActivity.class);
					
					String reponame = MainActivity.REPONAME;
					FastRepo fastRepo = new FastRepo();
					fastRepo.put("url", url);
					fastRepo.put("title", title);
					fastRepo.put("entryId", entryId);
					FastRepo.putRepo(reponame, fastRepo);					
					
					intent.putExtra(VideoPlayerActivity.MESSAGE_REPO, reponame);
					
					/*intent.putExtra(VideoPlayerActivity.MESSAGE_URL, url);
					intent.putExtra(VideoPlayerActivity.MESSAGE_TITLE, title);
					intent.putExtra(VideoPlayerActivity.MESSAGE_ID, entryId);*/
				    startActivity(intent);
					 
					/* MediaController mc = new MediaController(this);
					mc.setAnchorView(videoView);
					mc.setMediaPlayer(videoView);
					Uri video = Uri.parse(url);
					videoView.setMediaController(mc);
					videoView.setVideoURI(video);
					videoView.start();
					
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url));
					startActivity(i); */
					
				}
				
			});
		
		
		if(isNetworkAvailable()){
			Log.i("StackSites", "starting download Task");
			SitesDownloadTask download = new SitesDownloadTask();
			download.execute();
		}else{
			mAdapter = new SitesAdapter(getApplicationContext(), -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
			sitesList.setAdapter(mAdapter);
		}
		
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	} 
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		switch(requestCode) {
		case REQUEST_CAMERA_VIDEO:
			if (resultCode == RESULT_OK) {
				Uri videouri = data.getData();
				Log.d(TAG, videouri.toString());
			}
			break;
		}
	}
	
	private class SitesDownloadTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... arg0) {
			//Download the file
			try {
				Downloader.DownloadFromUrl("http://breakit.herokuapp.com/recentEntries.xml", openFileOutput("recentEntries.xml", Context.MODE_PRIVATE));
		
			} 
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return null;
		}

		protected void onPostExecute(Void result){
			//setup our Adapter and set it to the ListView.
			mAdapter = new SitesAdapter(MainActivity.this, -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
			sitesList.setAdapter(mAdapter);
			Log.i("StackSites", "adapter size = "+ mAdapter.getCount());
		}
	}
	private void getAccountNames() {
		ArrayList<CharSequence> emails = new ArrayList<CharSequence>();
		AccountManager mAccountManager = AccountManager.get(context);
		Account[] accounts = mAccountManager.getAccountsByType("com.google");
		if (accounts.length != 0)
		{
		
		for(int i=0;i<accounts.length;i++) {
			if (emails.indexOf(accounts[i]) == -1) {
				emails.add(accounts[i].name);
			}
		}
		CharSequence[] charseq = emails.toArray(new CharSequence[emails.size()]);
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Select your account")
			.setCancelable(false)
		    .setSingleChoiceItems(charseq, 1, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialogInterface, int item) {
		        	dialogInterface.dismiss();
		        }
		    });
		 
		builder.create().show();
		}
}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}
