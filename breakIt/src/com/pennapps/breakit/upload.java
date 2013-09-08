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
public class upload extends AsyncTask<String, Void, String>{
	String URL = "http://breakit.herokuapp.com/upload";
	//uploadFile(URL, )
	public void uploadFilez() {
	HttpClient httpClient = new DefaultHttpClient();
	    HttpContext localContext = new BasicHttpContext();
	    HttpPost httpPost = new HttpPost(URL);
	    try {
	        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
	        entity.addPart("video", new FileBody(new File (videouri.getData()));

//	        for(int index=0; index < nameValuePairs.size(); index++) {
	  //          if(nameValuePairs.get(index).getName().equalsIgnoreCase("image")) {
	                // If the key equals to "image", we use FileBody to transfer the data
	    //        } else {
	                // Normal string data
	        entity.addPart("testing!!!", new StringBody("it works"));
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
		uploadFilez();
		return null;
	}

}
