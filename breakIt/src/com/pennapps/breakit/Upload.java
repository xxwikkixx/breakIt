package com.pennapps.breakit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import com.pennapps.breakit.MainActivity;


import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
public class Upload extends AsyncTask<String, Void, String>{
	final String TAG = this.getClass().getSimpleName();
	public final String MESSAGE_VIDEO_PATH = null;
	String URL = "http://breakit.herokuapp.com/upload";
	//uploadFile(URL, )
	public void uploadFilez(String[] params) {
	HttpClient httpClient = new DefaultHttpClient();
	    HttpContext localContext = new BasicHttpContext();
	    HttpPost httpPost = new HttpPost(URL);
	    try {
	        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	        entity.addPart("video", new FileBody(new File (params[1])));
	        entity.addPart("img", new FileBody(new File(params[2])));
	        Log.e(TAG,"videopath:"+params[1]);
	        Log.e(TAG,params[0]);
	        Log.e(TAG,"thumbpath:"+params[2]);
	       //entity.addPart("video", new FileBody(new File ("sdcard/DCIM/Camera/test.3gp")));
	        	
	        
	        entity.addPart("entry_id", new StringBody(params[0]));
	        entity.addPart("userEmail", new StringBody("nobody"));
	        entity.addPart("about", new StringBody(params[3]));
	        
	        //entity.addPart("title", new StringBody(""));
	      //      }
	//        }

	        httpPost.setEntity(entity);

	        HttpResponse response = httpClient.execute(httpPost, localContext);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	@Override
	protected String doInBackground(String... params) {
		uploadFilez(params);
		return null;
	}
	
	

}
