package com.jomaange.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.jomaange.activity.R;
import com.jomaange.app.AppController;
import com.jomaange.model.LocationModel;

/**
 * This class used to set the parameters to display in open job list activity.
 * @author Manjunath
 *
 */
public class CityLocationsAdapter extends BaseAdapter {
	private Activity activity;
    private LayoutInflater inflater;
    private List<LocationModel> cityLocationList;
    private List<LocationModel> originalCityLocationList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
 
    public CityLocationsAdapter(Activity activity, List<LocationModel> cityLocationList) {
        this.activity = activity;
        this.cityLocationList = cityLocationList;
        this.inflater = LayoutInflater.from(activity);
        this.originalCityLocationList = new ArrayList<LocationModel>();
        this.originalCityLocationList.addAll(cityLocationList);
    }
    
    
 
    @Override
    public int getCount() {
        return cityLocationList.size();
    }
 
    @Override
    public Object getItem(int location) {
        return cityLocationList.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @SuppressLint("ResourceAsColor")
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
        if (inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_row_city_location, null,true);
        }
        if (imageLoader == null){
            imageLoader = AppController.getInstance().getImageLoader();
        }
        TextView city_location_name = (TextView) convertView.findViewById(R.id.city_location_name);
        city_location_name.setText(cityLocationList.get(position).getLocationName());
        return convertView;
    }
    
    public void filter(String charText) {
		charText = charText.toLowerCase(Locale.getDefault());
		cityLocationList.clear();
		if (charText.length() == 0) {
			cityLocationList.addAll(originalCityLocationList);
		} else {
			for (LocationModel model : originalCityLocationList) {
				if (model.getLocationName().toLowerCase(Locale.getDefault()).contains(charText)) {
					cityLocationList.add(model);
				}
			}
		}
		notifyDataSetChanged();
	}
 
}
