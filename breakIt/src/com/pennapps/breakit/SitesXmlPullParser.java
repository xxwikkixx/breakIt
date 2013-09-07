package com.pennapps.breakit;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Context;
import android.util.Log;

public class SitesXmlPullParser {

	static final String KEY_NAME = "name";
	static final String KEY_LINK = "link";
	static final String KEY_IMAGE_URL = "image";
	
	public static List<StackSite> getStackSitesFromFile(Context ctx) {
		List<StackSite> stackSites = new ArrayList<StackSite>();
		
		StackSite curStackSite = null;
		
		String curText = "";
		
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser xpp = factory.newPullParser();
			FileInputStream fis  = ctx.openFileInput("StackSites.xml"); //URL END NAME WITH ALL THE LISTS CHANGE LATER
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
			xpp.setInput(reader);
			
			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				StackSite newSite = new StackSite();
				String tagname = xpp.getName();
				
				switch(eventType) {
				case XmlPullParser.START_TAG:
				
				case XmlPullParser.TEXT:
					curText = xpp.getText();
					break;
					
				case XmlPullParser.END_TAG:
					if(tagname.equalsIgnoreCase(KEY_NAME)){
						newSite.setName(curText);
					}
					else if (tagname.equalsIgnoreCase(KEY_LINK)){
						newSite.setLink(curText);
					}
					else if (tagname.equalsIgnoreCase(KEY_IMAGE_URL)){
						newSite.setImgUrl(curText);
					}
					break;
					
					default:
						break;
				}
				stackSites.add(newSite);
				
				eventType = xpp.next();
			}
			Log.v("StackSites", "End");
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return stackSites;
	}
}
