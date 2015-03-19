package com.jomaange.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.jomaange.activity.R;
import com.jomaange.adapter.EntityDetailsItemListAdapter;
import com.jomaange.app.CartController;
import com.jomaange.helper.EntityHelper;
import com.jomaange.model.EntityTabContentModel;
import com.jomaange.model.EntityTabModel;
import com.jomaange.model.ServiceResponseModel;

public class EntityDetailsTabViewHelper {

	private TabHost tabHost;
	private Context context;
	private Activity activity;
	private CartController cart;
	
	public EntityDetailsTabViewHelper(Activity activity, Context context, TabHost tabHost){
		this.tabHost = tabHost;
		this.context = context;
		this.activity = activity;
		this.cart = CartController.getInstance();
	}
	
	public void createView(Map<String, EntityTabModel> entityDetailsMap1){
		// read local file starts
		StringBuilder sb = new StringBuilder();
		try {
			InputStream is = activity.getResources().openRawResource(R.drawable.hotel_details);
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String str = "";
			if (is!=null) {							
				while ((str = reader.readLine()) != null) {	
					sb.append(str);
				}				
			}		
			is.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		ServiceResponseModel respModel = EntityHelper.getEntityDetails("79");
		Map<String, EntityTabModel> entityDetailsMap = respModel.getEntityDetailsMap();
		// read local file ends
		
		if(entityDetailsMap!=null && entityDetailsMap.size() > 0){
			Set<String> itr = entityDetailsMap.keySet();
			for(String tabName : itr){
				final EntityTabModel model = entityDetailsMap.get(tabName);
				final ListView ls1 = new ListView(context);       
	            TabSpec ts1 = tabHost.newTabSpec("TAB_TAG_"+tabName);
	            ts1.setIndicator(tabName);
	            ts1.setContent(new TabHost.TabContentFactory(){
	                public View createTabContent(String tag){
	                    EntityDetailsItemListAdapter adapter = new EntityDetailsItemListAdapter(activity, model.getEntityTabContentModelList());
	                    ls1.setAdapter(adapter);
	                    return ls1;
	                }    
	            });
	            tabHost.addTab(ts1);
			}
		}
		
	}
	
	
}
