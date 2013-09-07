package com.pennapps.breakit;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import android.util.Log;

public class Downloader {

	private static String  myTag = "entries";
	
	static final int POST_PROGRESS = 1;
	
	public static void DownloadFromUrl(String URL, FileOutputStream fos){
		try {
			URL url = new URL(URL);
			
			long startTime = System.currentTimeMillis();
			Log.d(myTag, "download begning");
			
			URLConnection ucon = url.openConnection();
			
			Log.i(myTag, "Opened Connection");
			
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			Log.i(myTag, "Got inputstream and bufferedinputstream");
			
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			Log.i(myTag, "got fileoutputstream");
			
			byte data[] = new byte[1024];
			
			int count;
			while((count = bis.read(data)) != -1) {
				bos.write(data, 0, count);
			}
			
			bos.flush();
			bos.close();
			
			Log.d(myTag, "download read");
		}
		catch (IOException e){
			Log.d(myTag, "error: " + e);
		}
	}
}
