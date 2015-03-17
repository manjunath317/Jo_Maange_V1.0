package com.jomaange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserDetailsActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        
        Button user_details_next_btn = (Button) findViewById(R.id.user_details_next_btn);
        user_details_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent i = new Intent(getApplicationContext(), SMSVerificationActivity.class);
				startActivity(i);
            }
		});
	}

	protected void onDestroy() {
	    	super.onDestroy();
	};

}
