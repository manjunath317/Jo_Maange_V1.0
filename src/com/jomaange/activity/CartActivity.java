package com.jomaange.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jomaange.adapter.CartListAdapter;
import com.jomaange.app.CartController;
import com.jomaange.constant.AppConstants;
import com.jomaange.model.ServiceResponseModel;

public class CartActivity extends Activity {
	
	private CartController cart;
	private ListView listView;
	private CartListAdapter adapter;
	private ProgressDialog pDialog;
	private ServiceResponseModel respModel;
    private String vendorId;
    private String vendorName;
    private TextView cart_item_details_cart_items_qty, cart_item_details_sub_total_text;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
		LinearLayout cart_info_layout = (LinearLayout) findViewById(R.id.cart_info_layout);
		RelativeLayout cart_items_layout = (RelativeLayout) findViewById(R.id.cart_items_layout);
		TextView cart_no_items_id = (TextView) findViewById(R.id.cart_no_items_id);
		
		cart_item_details_cart_items_qty = (TextView) findViewById(R.id.cart_item_details_cart_items_qty);
		cart_item_details_sub_total_text = (TextView) findViewById(R.id.cart_item_details_sub_total_text);
		
		vendorName = (String) getIntent().getSerializableExtra("vendorName");
		vendorId = (String) getIntent().getSerializableExtra("vendorId");
		respModel = getIntent().getSerializableExtra(AppConstants.RESP_MODEL)!=null?(ServiceResponseModel)getIntent().getSerializableExtra(AppConstants.RESP_MODEL):null;
		
		cart = CartController.getInstance();
		
		if(cart.getEntityItemList() != null && cart.getEntityItemList().size() > 0){
			cart_info_layout.setVisibility(View.VISIBLE);
			cart_items_layout.setVisibility(View.VISIBLE);
			cart_no_items_id.setVisibility(View.GONE);
			
			// setting cart info details
			cart_item_details_cart_items_qty.setText(String.valueOf(cart.getCartItemsQuantity()));
			cart_item_details_sub_total_text.setText("Sub Total \n ₹"+cart.getSubTotal());
			
			listView = (ListView) findViewById(R.id.cart_items);
			adapter = new CartListAdapter(this, cart.getEntityItemList());
            listView.setAdapter(adapter);
		}else{
			cart_info_layout.setVisibility(View.GONE);
			cart_items_layout.setVisibility(View.GONE);
			cart_no_items_id.setVisibility(View.VISIBLE);
		}
		
	}
	
	public void processing(View v){
		if(pDialog==null){
			pDialog = new ProgressDialog(v.getContext());
		}
		pDialog.setMessage("Loading. Please wait...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}
	
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

	private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
            	onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
    	super.onBackPressed();
    }
	

}
