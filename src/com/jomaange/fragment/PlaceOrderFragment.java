package com.jomaange.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jomaange.activity.CityLocationsActivity;
import com.jomaange.activity.R;
import com.jomaange.constant.AppConstants;
import com.jomaange.helper.LocationHelper;
import com.jomaange.model.ServiceResponseModel;
import com.jomaange.util.ServiceValidator;

public class PlaceOrderFragment extends Fragment {
	
	private ServiceValidator serviceValidator=null;
	private ServiceResponseModel respModel;
	private ProgressBar progressBar;
	
	public PlaceOrderFragment(){
		
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_place_order1, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		Display display = getActivity().getWindowManager().getDefaultDisplay(); 
		int screenWidth = display.getWidth();
		int screenHeight = display.getHeight();
		
		ImageView home_banner_id = (ImageView) rootView.findViewById(R.id.home_banner_id);
		home_banner_id.setLayoutParams( new RelativeLayout.LayoutParams((screenWidth), (screenHeight/2)));
		
        // check GPS is switched ON or not. If not ON, display an error message.
		serviceValidator = new ServiceValidator();
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences(AppConstants.MyPREFERENCES,Context.MODE_PRIVATE);
		progressBar = (ProgressBar) rootView.findViewById(R.id.place_order_progressBar);
		progressBar.setVisibility(View.GONE);
        Button place_order_selected_city_btn = (Button) rootView.findViewById(R.id.place_order_selected_city_btn);
        place_order_selected_city_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	new CityLocationsActivityAsync().execute();
            }
		});
        return rootView;
    }
    
    
    private class CityLocationsActivityAsync extends AsyncTask{
    	@Override
		protected Object doInBackground(Object... arg0) {
			try {
				respModel = LocationHelper.getLocations();
			}catch(Exception e){
				e.printStackTrace();
				progressBar.setVisibility(View.GONE);
			}
			return null;
    	}
    	
    	@Override
		protected void onPreExecute() {
    		progressBar.setVisibility(View.VISIBLE);
		}
    	
    	@Override
		protected void onPostExecute(Object result) {
    		if (respModel!=null && !respModel.isErrorFlag() && respModel.getCityLocationsList()!=null && respModel.getCityLocationsList().size()>0) {
    			Intent i = new Intent(getActivity().getApplicationContext(), CityLocationsActivity.class);
                i.putExtra(AppConstants.RESP_MODEL, respModel);
                startActivity(i);
                //getActivity().overridePendingTransition(R.anim.left_animation, R.anim.right_animation);
                progressBar.setVisibility(View.GONE);
    		}else if (respModel!=null && respModel.getErrorMessage()!=null && !respModel.getErrorMessage().isEmpty()) {
        		toastMessage(getActivity(), respModel.getErrorMessage());
        		progressBar.setVisibility(View.GONE);
			}else{
				toastMessage(getActivity(),"No Results Found");
				progressBar.setVisibility(View.GONE);
			}
    	}
    	
    }
    
    
    
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
 
    public void toastMessage(Context context, String message){
		Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
    
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
    	Log.i("onPrepareOptionsMenu","executed, place order fragment");
    	menu.getItem(R.id.action_edit_location).setVisible(true);
    	super.onPrepareOptionsMenu(menu);
    }
    
}
