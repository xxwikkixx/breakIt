package com.pennapps.breakit;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

public class CameraActivity extends Activity implements OnClickListener, SurfaceHolder.Callback{
	
	private static final String TAG = "CameraActivity";
	private CamcorderProfile cp = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
	
	private ImageButton recordButton = null;
	private VideoView videoView = null;
	private int res_startbutton_ID;
	private int res_stopbutton_ID;
	private boolean isRecording = false;
	
	private Camera camera;
	private MediaRecorder recorder;
	private SurfaceHolder holder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e(TAG,"onCreate");
		setContentView(R.layout.activity_camera);
		recordButton = (ImageButton) findViewById(R.id.recordbutton);
				
		res_startbutton_ID = getResources().getIdentifier("startbutton" , "drawable", getPackageName());
		res_stopbutton_ID = getResources().getIdentifier("stopbutton" , "drawable", getPackageName());
		
		videoView = (VideoView)this.findViewById(R.id.videoView1);
		holder = videoView.getHolder();
        holder.addCallback(this);
        
		recordButton.setOnClickListener(this);
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		try {
			Log.e(TAG,"onStart");
			initCamera();
			Log.e(TAG,"cam inited");
			initRecorder();
			Log.e(TAG,"record inited");
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		Log.e(TAG,"surfaceCreated");
	    try {
	    	camera.setPreviewDisplay(holder);
	    	camera.startPreview();
	    	Log.e(TAG,"preview started");
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {		
		releaseRecorder();
		releaseCamera();
		Log.e(TAG,"surfaceDestroyed");
	}
	
	@Override
	public void onStop() {		
		super.onStop();
	}	
	
	@Override
	public void onClick(View view){
		
		switch (view.getId()) {
		
		case R.id.recordbutton:
			Log.e(TAG,"ClickButton");
			if (isRecording) {
				//stop it
				//stopRecord();
				isRecording = false;
				recordButton.setImageResource(res_startbutton_ID);				
			}else {
				//start record
				try {
					//startRecord();
					isRecording = true;
					recordButton.setImageResource(res_stopbutton_ID);
				} catch (Exception e) {
					e.printStackTrace();
				}				
			}
			break;			
		}		
	
	}
	
	
	private boolean initCamera(){
		camera = Camera.open();
		camera.setDisplayOrientation(90);
		Camera.Parameters camParams = camera.getParameters();
		camParams.setPreviewSize(176,144);
	    camera.setParameters(camParams);
		return true;
	}
	
	private void initRecorder() throws IllegalStateException, IOException {
		//release previous recorder, if any
		releaseRecorder();
		
		File outputDir = Environment.getExternalStorageDirectory();
		String outFileName = outputDir+"/test.mp4";
		
		File outFile = new File(outFileName);
		if (outFile.exists())
			outFile.delete();
		
		recorder = new MediaRecorder();
		recorder.setCamera(camera);
		recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
		recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        recorder.setProfile(cp);		
		recorder.setPreviewDisplay(holder.getSurface());
		recorder.setOutputFile(outFileName);		
		recorder.prepare();	
	}
	
	private void startRecord(){		
		camera.stopPreview();
		camera.unlock();		
		recorder.start();
	}
	
	private void stopRecord() throws IllegalStateException{
		if (recorder != null) {
			recorder.stop();			
		}			
	}
	
	private void releaseRecorder() {
		if (recorder != null) {
			recorder.release();
			recorder = null;
		}
		
	}
	
	private void releaseCamera() {
		if (camera != null) {
			try {
				camera.reconnect();
			}catch (IOException e) {
				e.printStackTrace();
			}
			camera.release();
			camera = null;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {		
	}	

}
