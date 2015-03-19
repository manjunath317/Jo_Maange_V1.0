package com.jomaange.activity;


import java.util.ArrayList;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jomaange.adapter.NavDrawerListAdapter;
import com.jomaange.constant.AppConstants;
import com.jomaange.constant.MessageConstants;
import com.jomaange.fragment.ContactUsFragment;
import com.jomaange.fragment.FAQFragment;
import com.jomaange.fragment.NotificationsFragment;
import com.jomaange.fragment.OrderStatusFragment;
import com.jomaange.fragment.PastOrdersFragment;
import com.jomaange.fragment.PlaceOrderFragment;
import com.jomaange.model.NavDrawerItem;
import com.jomaange.util.ServiceValidator;

/**
 * This activity acts as a main activity. The left drawer is loaded here.
 * @author Manjunath
 *
 */
public class MainActivity extends Activity {
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private SharedPreferences sharedPreferences;
    private Dialog internetMonitorDialog;
    private Dialog gpsMonitorDialog;
    private ServiceValidator serviceValidator;
	private int selectedPosition = 0;
	private boolean isDrawerOpened = false;

 
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(AppConstants.MyPREFERENCES,Context.MODE_PRIVATE);
        serviceValidator = new ServiceValidator();
        
        mTitle = mDrawerTitle = getTitle();
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        navMenuIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
        navDrawerItems = new ArrayList<NavDrawerItem>();
 
        for (int i = 0; i < navMenuTitles.length; i++) {
        	navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons.getResourceId(i, -1)));
		}

        navMenuIcons.recycle();
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
 
        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(),navDrawerItems);
        mDrawerList.setAdapter(adapter);
 
        // enabling action bar app icon and behaving it as toggle button
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
 
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_drawer, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                isDrawerOpened = false;
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                isDrawerOpened = true;
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        if (savedInstanceState == null) {
            displayView(0);
        }
        
        IntentFilter netfilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(mReceivedInternetReceiver, netfilter);
        
        IntentFilter gpsFilter = new IntentFilter("android.gps.conn.CONNECTIVITY_CHANGE");
        this.registerReceiver(mReceivedGPSReceiver, gpsFilter);
        
        //mDrawerLayout.openDrawer(mDrawerList);
    }
    
    private BroadcastReceiver mReceivedInternetReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        	boolean isConnected = true;
        	if (connMgr != null){
        		NetworkInfo[] info = connMgr.getAllNetworkInfo();
    			if (info != null){
    				for (int i = 0; i < info.length; i++){
    					if (info[i].getState() == NetworkInfo.State.CONNECTED){
    						isConnected = true;
    						break;
    					}
    				}
    			}
        	}
        	if(isConnected){
        		closeInternetMonitorDialog();
        	}else{
        		displayInternetMonitorDialog();
        	}
        }
    };
    
    
    private BroadcastReceiver mReceivedGPSReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        	boolean isConnected = true;
        	if(serviceValidator.validateGpsConnection(context)){
        		isConnected = true;
        	}
        	if(isConnected){
        		closeGPSMonitorDialog();
        	}else{
        		displayGPSMonitorDialog();
        	}
        }
    };
    
    private void displayInternetMonitorDialog(){
    	if(internetMonitorDialog==null){
    		internetMonitorDialog = new Dialog(MainActivity.this);
    	}
    	internetMonitorDialog.setTitle(MessageConstants.INTERNET_CONNECTION_ERROR_TITLE);
    	internetMonitorDialog.setContentView(R.layout.dialog_internet_monitor);
    	internetMonitorDialog.setCancelable(false);
    	internetMonitorDialog.show();
    }
    
    private void displayGPSMonitorDialog(){
    	if(gpsMonitorDialog==null){
    		gpsMonitorDialog = new Dialog(MainActivity.this);
    	}
    	gpsMonitorDialog.setTitle(MessageConstants.GPS_CONNECTION_ERROR_TITLE);
    	gpsMonitorDialog.setContentView(R.layout.dialog_gps_monitor);
    	gpsMonitorDialog.setCancelable(false);
    	gpsMonitorDialog.show();
    }
    
    private void closeInternetMonitorDialog(){
    	if(internetMonitorDialog!=null){
    		internetMonitorDialog.dismiss();
    	}
    }
    
    private void closeGPSMonitorDialog(){
    	if(gpsMonitorDialog!=null){
    		gpsMonitorDialog.dismiss();
    	}
    }
    
    protected void onDestroy() {
    	super.onDestroy();
    	closeInternetMonitorDialog();
    	unregisterReceiver(mReceivedInternetReceiver);
    	unregisterReceiver(mReceivedGPSReceiver);
    };
    
    public void toastMessage(Context context, String message){
		Toast toast = Toast.makeText(context,message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
    
    
    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            displayView(position);
        }
    }
 
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
 
    private void displayView(int position) {
        Fragment fragment = null;
        selectedPosition = position;
        switch (position) {
        case 0:
            fragment = new PlaceOrderFragment();
            break;
        case 1:
            fragment = new PastOrdersFragment();
            break;
        case 2:
            fragment = new OrderStatusFragment();
            break;
        case 3:
            fragment = new NotificationsFragment();
            break;
        case 4:
            fragment = new FAQFragment();
            break;
        case 5:
            fragment = new ContactUsFragment();
            break;
        default:
            break;
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            Log.i("Fragment Initialization Error","fragment is null... ");
        }
    }
    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }
 
    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }
 
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }
    
    @Override
	public void onBackPressed() {
    	if (selectedPosition != 0) {
			displayView(0);
		}else if (isDrawerOpened) {
	        displayView(0);
	    } else {
		    new AlertDialog.Builder(this, R.style.DialogSlideAnim)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle(AppConstants.APP_NAME)
	        .setMessage(MessageConstants.EXIT_MSG)
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener(){
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();
	            Intent intent = new Intent(Intent.ACTION_MAIN);
 	    	    intent.addCategory(Intent.CATEGORY_HOME);
 	    	    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
 	    	    startActivity(intent);
	        }
	    })
	    .setNegativeButton("No", null)
	    .show();
	    }
	}
    
   
}
