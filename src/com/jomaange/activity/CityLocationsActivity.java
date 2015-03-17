package com.jomaange.activity;


import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jomaange.adapter.CityLocationsAdapter;
import com.jomaange.constant.AppConstants;
import com.jomaange.helper.EntityHelper;
import com.jomaange.model.LocationModel;
import com.jomaange.model.ServiceResponseModel;

public class CityLocationsActivity extends Activity {
	
	private List<LocationModel> locationList;
	private ListView listView;
	private CityLocationsAdapter adapter;
	private ServiceResponseModel respModel;
	private LocationModel locModel;
	private EditText city_location_search_edit_text_id;
	private ImageView searchIcon;
	private RelativeLayout searchSection;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_locations);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setTitle("Bangalore");
		searchIcon = (ImageView) findViewById(R.id.city_location_search_icon);
		searchSection = (RelativeLayout) findViewById(R.id.city_location_search_section_id);
		respModel = getIntent().getSerializableExtra(AppConstants.RESP_MODEL)!=null?(ServiceResponseModel)getIntent().getSerializableExtra(AppConstants.RESP_MODEL):null;
		if(respModel !=null){
			locationList = respModel.getCityLocationsList();
			if(locationList!=null && locationList.size() > 0){
				listView = (ListView) findViewById(R.id.chosse_location_list);
				adapter = new CityLocationsAdapter(this, locationList);
	            listView.setAdapter(adapter);
	            //getListViewSize(listView);
		        listView.setOnItemClickListener(new OnItemClickListener() {
		        	@Override
					public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
		        		locModel = locationList.get(position);
		        		new EntityListAsync().execute(locModel.getLocationName());
					}
				});
			}
		}		
		city_location_search_edit_text_id = (EditText) findViewById(R.id.city_location_search_edit_text_id);
		city_location_search_edit_text_id.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				String searchQuery = city_location_search_edit_text_id.getText().toString().toLowerCase(Locale.getDefault());
				adapter.filter(searchQuery);
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if(searchSection.getVisibility() == (view.VISIBLE)){
            		searchSection.setVisibility(view.GONE);
            	}else{
            		searchSection.setVisibility(view.VISIBLE);
            	}
            }
		});
	}
	
	public static void getListViewSize(ListView myListView) {
        ListAdapter myListAdapter = myListView.getAdapter();
        if (myListAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight + (myListView.getDividerHeight() * (myListAdapter.getCount() - 1));
        myListView.setLayoutParams(params);
    }
	
	
	private class EntityListAsync extends AsyncTask{
		ProgressDialog  updateJobDialog = new ProgressDialog(CityLocationsActivity.this);
		
		@Override
		protected Object doInBackground(Object... arg0) {
			try {
				respModel = EntityHelper.getEntityList(arg0[0].toString());
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
    		if (respModel!=null && !respModel.isErrorFlag() && respModel.getEntityList()!=null && respModel.getEntityList().size()>0) {
    			Intent i = new Intent(getApplicationContext(), SearchResultsActivity.class);
                i.putExtra(AppConstants.RESP_MODEL, respModel);
                i.putExtra("sel_loc", locModel);
                startActivity(i);
                //overridePendingTransition(R.anim.left_animation, R.anim.right_animation);
    		}else if (respModel!=null && respModel.getErrorMessage()!=null && !respModel.getErrorMessage().isEmpty()) {
        		toastMessage(getApplicationContext(), respModel.getErrorMessage());
			}else{
				toastMessage(getApplicationContext(),"No Results Found");
			}
    	}
    }
	
	public void toastMessage(Context context, String message){
		Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
	

}
