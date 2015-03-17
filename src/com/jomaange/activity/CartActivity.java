package com.jomaange.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;

public class CartActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
	}
	
	protected void onDestroy() {
    	super.onDestroy();
	};

}
