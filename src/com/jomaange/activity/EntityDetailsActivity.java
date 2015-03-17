package com.jomaange.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.jomaange.app.CartController;
import com.jomaange.constant.AppConstants;
import com.jomaange.model.ServiceResponseModel;
import com.jomaange.util.EntityDetailsTabViewHelper;

public class EntityDetailsActivity extends Activity {
	
    private TabHost myTabHost;
    private LinearLayout item_details_cart_layout;
    private TextView item_details_cart_items_qty, item_details_sub_total_text;
    private Button item_details_checkout_layout;
    private ServiceResponseModel respModel;
    private String vendorId;
    private String vendorName;
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_details);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		vendorName = (String) getIntent().getSerializableExtra("vendorName");
		vendorId = (String) getIntent().getSerializableExtra("vendorId");
		respModel = getIntent().getSerializableExtra(AppConstants.RESP_MODEL)!=null?(ServiceResponseModel)getIntent().getSerializableExtra(AppConstants.RESP_MODEL):null;
		
		if(vendorName!=null && !vendorName.isEmpty()){
			setTitle(vendorName);
			Log.i("vendor details", vendorId  +",  "+vendorName);
		}
		
		myTabHost = (TabHost)this.findViewById(R.id.tabHost); 
        myTabHost.setup();
        item_details_cart_layout = (LinearLayout) findViewById(R.id.item_details_cart_layout);
        item_details_checkout_layout = (Button) findViewById(R.id.item_details_checkout_layout);
        item_details_cart_items_qty = (TextView) findViewById(R.id.item_details_cart_items_qty);
        item_details_sub_total_text = (TextView) findViewById(R.id.item_details_sub_total_text);
        CartController cart = CartController.getInstance();
        
        EntityDetailsTabViewHelper viewHelper = new EntityDetailsTabViewHelper(EntityDetailsActivity.this, EntityDetailsActivity.this, myTabHost);
        viewHelper.createView(respModel.getEntityDetailsMap());
        
        item_details_cart_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Log.i("item_details_cart_layout", "Go to CART now");
            	Intent i = new Intent(getApplicationContext(), CartActivity.class);
				startActivity(i);
            }
        });
        
        item_details_checkout_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Log.i("item_details_checkout_layout", "Go to checkout now. SKIP cart view");
            	Intent i = new Intent(getApplicationContext(), UserDetailsActivity.class);
				startActivity(i);
            }
        });
        
	}
	
	protected void onDestroy() {
    	super.onDestroy();
	};

}
