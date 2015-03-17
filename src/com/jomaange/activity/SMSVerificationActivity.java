package com.jomaange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SMSVerificationActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_verification);
        
        TextView sms_verification_next_id3 = (TextView) findViewById(R.id.sms_verification_next_id3);
        sms_verification_next_id3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent i = new Intent(getApplicationContext(), AddAddressActivity.class);
				startActivity(i);
            }
		});
	}

	protected void onDestroy() {
	    	super.onDestroy();
	};

}
