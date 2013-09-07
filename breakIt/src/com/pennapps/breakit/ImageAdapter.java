package com.pennapps.breakit;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    String path;
    Bitmap bmp;
    // Bitmap[] bitmapArray = new Bitmap[20];
    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>(20);

    public ImageAdapter(Context c) {
    mContext = c;
    }

    public int getCount() {
        return bitmapArray.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) { 
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(bitmapArray.get(position));
        return imageView;
    }

    // references to images

    ImageAdapter() {
        String xml = XML_VIDEObmp.getXML();
        Document doc = XML_VIDEObmp.XMLfromString(xml);
        
        NodeList nodes = doc.getElementsByTagName("content");
        for(int i=0;i<nodes.getLength();i++){
            Element e=(Element)nodes.item(i);               
            try{
                path=XML_VIDEObmp.getValue(e,"previews");
                System.out.println(path);
                URL ulrn = new URL(path);
                HttpURLConnection con = (HttpURLConnection)ulrn.openConnection();
                InputStream is = con.getInputStream();
                 bmp = BitmapFactory.decodeStream(is);           
                 bitmapArray.add(bmp); // Add a bitmap          
            }catch(Exception e1){}              
        }
    }
}