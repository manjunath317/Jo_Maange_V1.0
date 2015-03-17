package com.jomaange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddAddressActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        
        Button add_address_next_btn = (Button) findViewById(R.id.add_address_next_btn);
        add_address_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent i = new Intent(getApplicationContext(), OrderDetailsActivity.class);
				startActivity(i);
            }
		});
	}

	protected void onDestroy() {
	    	super.onDestroy();
	};
}
