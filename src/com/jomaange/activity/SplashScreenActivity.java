package com.jomaange.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.jomaange.constant.AppConstants;



public class SplashScreenActivity extends Activity {
	private final int splash_time=3000;  // 3 seconds
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splashscreen);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		try {
			// set app version name and version code.
			PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
			AppConstants.APP_VERSION_NAME = info.versionName;
			AppConstants.APP_VERSION_CODE = info.versionCode;
			final ProgressBar progressBar = (ProgressBar) findViewById(R.id.splash_progressBar);
			progressBar.setVisibility(View.VISIBLE);
			try {
				Thread.sleep(1000);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(getApplicationContext(), MainActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						progressBar.setVisibility(View.GONE);
					}
				}, splash_time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
}

