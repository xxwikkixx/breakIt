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

import com.pennapps.breakit.MainActivity;

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
					selectedPath = getPath();//need a path here 
					doFileUpload(); //file uploading
				}
			}
		}
		
		private void doFileUpload(){
			HttpURLConnection conn  = null;
			DataOutputStream dos = null;
			DataInputStream inStream = null;
			String lineEnd = "rn";
			String twoHyphens = "--";
			String boundary = "*****";
			int bytesRead, bytesAvailable, bufferSize;
			byte[] buffer;
			int maxBufferSize = 1*1024*1024;
			String responseFromServer = "";
			String urlString = "http://breakit.herokuapp.com/upload";
			
			try {
				FileInputStream fileInputStream = new FileInputStream(new File(selectedPath));
				URL url = new URL(urlString);
				conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
		         dos = new DataOutputStream( conn.getOutputStream() );
		         dos.writeBytes(twoHyphens + boundary + lineEnd);
		         dos.writeBytes(lineEnd);
		         // create a buffer of maximum size
		         bytesAvailable = fileInputStream.available();
		         bufferSize = Math.min(bytesAvailable, maxBufferSize);
		         buffer = new byte[bufferSize];
		         // read file and write it into form...
		         bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		         while (bytesRead > 0)
		         {
		          dos.write(buffer, 0, bufferSize);
		          bytesAvailable = fileInputStream.available();
		          bufferSize = Math.min(bytesAvailable, maxBufferSize);
		          bytesRead = fileInputStream.read(buffer, 0, bufferSize);
		         }
		         // send multipart form data necesssary after file data...
		         dos.writeBytes(lineEnd);
		         dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
		         // close streams
		         Log.e("Debug","File is written");
		         fileInputStream.close();
		         dos.flush();
		         dos.close();
		        }
		        catch (MalformedURLException ex)
		        {
		             Log.e("Debug", "error: " + ex.getMessage(), ex);
		        }
		        catch (IOException ioe)
		        {
		             Log.e("Debug", "error: " + ioe.getMessage(), ioe);
		        }
		        //------------------ read the SERVER RESPONSE
		        try {
		              inStream = new DataInputStream ( conn.getInputStream() );
		              String str;
		 
		              while (( str = inStream.readLine()) != null)
		              {
		                   Log.e("Debug","Server Response "+str);
		              }
		              inStream.close();
		 
		        }
		        catch (IOException ioex){
		             Log.e("Debug", "error: " + ioex.getMessage(), ioex);
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