package com.jomaange.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.jomaange.activity.R;
import com.jomaange.app.AppController;
import com.jomaange.constant.AppConstants;
import com.jomaange.model.EntityModel;

/**
 * This class used to set the parameters to display list of entity. The entity may be hotel.
 * @author Manjunath
 *
 */
public class EntityListAdapter extends BaseAdapter {
	private Activity activity;
    private LayoutInflater inflater;
    private List<EntityModel> entityList;
    public ImageLoader imageLoader; 
 
    public EntityListAdapter(Activity activity, List<EntityModel> entityList) {
        this.activity = activity;
        this.entityList = entityList;
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }
 
    @Override
    public int getCount() {
        return entityList.size();
    }
 
    @Override
    public Object getItem(int location) {
        return entityList.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_entity_list, null,true);
        }
        ImageView entity_list_icon = (ImageView) convertView.findViewById(R.id.entity_list_icon);
        TextView entity_list_title = (TextView) convertView.findViewById(R.id.entity_list_title);
        TextView entity_list_location = (TextView) convertView.findViewById(R.id.entity_list_location);
        
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(getImageSize(), getImageSize());
        entity_list_icon.setLayoutParams(layoutParams);
        imageLoader.DisplayImage(entityList.get(position).getImagePath(), entity_list_icon, R.drawable.hotel_icon);
        
        String title = "";
        if(entityList.get(position)!=null && entityList.get(position).getTitle()!=null && !entityList.get(position).getTitle().isEmpty()){
        	title = entityList.get(position).getTitle();
        	entity_list_title.setText(title);
        }else{
        	entity_list_title.setText("No Name");
        }
        String location="";
        if(entityList.get(position)!=null && entityList.get(position).getDoor()!=null && !entityList.get(position).getDoor().isEmpty()){
        	location = entityList.get(position).getDoor().trim();
        }
        if(entityList.get(position)!=null && entityList.get(position).getStreet()!=null && !entityList.get(position).getStreet().isEmpty()){
        	if(!location.isEmpty()){
        		location= location  +","+entityList.get(position).getStreet().trim();
        	}else{
        		location= entityList.get(position).getStreet().trim();
        	}
        }
        entity_list_location.setText(location);
        
        return convertView;
    }
    
    private int getImageSize(){
        int density= activity.getResources().getDisplayMetrics().densityDpi;
        int size =100; 
        switch(density)
        {
        case DisplayMetrics.DENSITY_LOW:
            size = 100;
            break;
        case DisplayMetrics.DENSITY_MEDIUM:
            size = 100;
            break;
        case DisplayMetrics.DENSITY_HIGH:
            size = 150;
            break;
        }
        return size;
    } 
 
}
