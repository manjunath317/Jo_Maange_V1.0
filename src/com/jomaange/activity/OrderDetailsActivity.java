package com.jomaange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderDetailsActivity  extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        
        Button order_details_next_btn = (Button) findViewById(R.id.order_details_next_btn);
        order_details_next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent i = new Intent(getApplicationContext(), OrderConfirmActivity.class);
				startActivity(i);
            }
		});
	}

	protected void onDestroy() {
	    	super.onDestroy();
	};

}
