package com.pennapps.breakit;

import java.util.ArrayList;

import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.widget.GridView;

import android.content.Context;
import android.content.DialogInterface;

public class MainActivity extends Activity {
	final Context context = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getAccountNames();
		
		
		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
