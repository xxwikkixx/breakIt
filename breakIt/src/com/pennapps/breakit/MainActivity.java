package com.pennapps.breakit;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class MainActivity extends Activity {
	final Context context = this;

	private SitesAdapter mAdapter;
	private ListView sitesList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getAccountNames();
		
		sitesList = (ListView)findViewById(R.id.sitesList);	
		sitesList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
				// TODO Auto-generated method stub
				String url = mAdapter.getItem(pos).getLink();
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
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
	
	private class SitesDownloadTask extends AsyncTask<Void, Void, Void>{

		protected Void doInBackground(Void... arg0) {
			//Download the file
			try {
				Downloader.DownloadFromUrl("LINK FOR URL GOES HERE /stacksites.xml", openFileOutput("StackSites.xml", Context.MODE_PRIVATE));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	
	private void getAccountNames() {
		ArrayList<CharSequence> emails = new ArrayList<CharSequence>();
		AccountManager mAccountManager = AccountManager.get(this);
		Account[] accounts = mAccountManager.getAccountsByType("com.google");
		for(int i=0;i<accounts.length;i++) {
			if (emails.indexOf(accounts[i]) == -1) {
				emails.add(accounts[i].name);
			}
		}
		CharSequence[] charseq = emails.toArray(new CharSequence[emails.size()]);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Select your account")
			.setCancelable(false)
		    .setSingleChoiceItems(charseq, 1, new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialogInterface, int item) {
		        	dialogInterface.dismiss();
		        }
		    });
		 
		builder.create().show();
}
	
	protected void onPostExecute(Void result){
		//setup our Adapter and set it to the ListView.
		mAdapter = new SitesAdapter(MainActivity.this, -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
		sitesList.setAdapter(mAdapter);
		Log.i("StackSites", "adapter size = "+ mAdapter.getCount());
	}
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

}
