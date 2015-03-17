package com.jomaange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class OrderConfirmActivity  extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        
        Button order_confirm_place_new_order_btn = (Button) findViewById(R.id.order_confirm_place_new_order_btn);
        LinearLayout order_confirm_order_status = (LinearLayout) findViewById(R.id.order_confirm_order_status);
        order_confirm_place_new_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
            }
		});
        
        order_confirm_order_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent i = new Intent(getApplicationContext(), MainActivity.class);
				startActivity(i);
            }
		});
	}

	protected void onDestroy() {
	    	super.onDestroy();
	};

}
