package com.jomaange.activity;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jomaange.adapter.EntityListAdapter;
import com.jomaange.constant.AppConstants;
import com.jomaange.helper.EntityHelper;
import com.jomaange.model.EntityModel;
import com.jomaange.model.LocationModel;
import com.jomaange.model.ServiceResponseModel;

public class SearchResultsActivity extends Activity {
	
	private ProgressDialog pDialog;
	private LocationModel locModel;
	private List<EntityModel> entityList;
	private ServiceResponseModel respModel;
	private ListView listView;
	private EntityListAdapter adapter;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
		locModel = (LocationModel) getIntent().getSerializableExtra("sel_loc");
		if(locModel!=null){
			setTitle(locModel.getLocationName()+", Bangalore");
		}
		respModel = getIntent().getSerializableExtra(AppConstants.RESP_MODEL)!=null?(ServiceResponseModel)getIntent().getSerializableExtra(AppConstants.RESP_MODEL):null;
		if(respModel !=null){
			entityList = respModel.getEntityList();
			if(entityList!=null && entityList.size() > 0){
				listView = (ListView) findViewById(R.id.search_results_city_locations_list);
				adapter = new EntityListAdapter(this, entityList);
	            listView.setAdapter(adapter);
	            getListViewSize(listView);
		        listView.setOnItemClickListener(new OnItemClickListener() {
		        	@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		        		EntityModel model = entityList.get(position);
		        		Log.i("vendor details", model.getVendor_id()  +",  "+model.getTitle());
		        		new EntityDetailsAsync().execute(model.getEntityId(), model.getTitle());
					}
				});
			}
		}
	}
	
	private class EntityDetailsAsync extends AsyncTask{
		ProgressDialog  updateJobDialog = new ProgressDialog(SearchResultsActivity.this);
		String vendorName = null;
		String vendorId = null;
		
		@Override
		protected Object doInBackground(Object... arg0) {
			try {
				vendorId = arg0[0].toString();
				vendorName = arg0[1].toString();
				respModel = EntityHelper.getEntityDetails(vendorId);
			}catch(Exception e){
				e.printStackTrace();
			}
			return null;
    	}
    	
    	@Override
		protected void onPreExecute() {
    		updateJobDialog.setMessage("Loading. Please Wait...");
			updateJobDialog.setIndeterminate(false);
			updateJobDialog.setCancelable(true);
			updateJobDialog.show();
		}
    	
    	@Override
		protected void onPostExecute(Object result) {
    		if (updateJobDialog != null) {
				updateJobDialog.dismiss();
				updateJobDialog = null;
			}
    		if (respModel!=null && !respModel.isErrorFlag() && respModel.getEntityDetailsMap()!=null && respModel.getEntityDetailsMap().size()>0) {
    			Intent i = new Intent(getApplicationContext(), EntityDetailsActivity.class);
                i.putExtra(AppConstants.RESP_MODEL, respModel);
                i.putExtra("vendorName", vendorName);
                i.putExtra("vendorId", vendorId);
                startActivity(i);
    		}else if (respModel!=null && respModel.getErrorMessage()!=null && !respModel.getErrorMessage().isEmpty()) {
        		toastMessage(getApplicationContext(), respModel.getErrorMessage());
			}else{
				toastMessage(getApplicationContext(),"No Results Found");
			}
    	}
    }
	
	public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            if(listItem!=null){
            	listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
        }
      //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
        // print height of adapter on log
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
	
	public void toastMessage(Context context, String message){
		Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
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
