package com.jomaange.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;


/**
 * This class used to validate gps and internt connection.
 * @author Manjunath
 *
 */
public class ServiceValidator {
	
	@SuppressWarnings("deprecation")
	public Boolean validateGpsConnection(Context getBaseContext) {
		ContentResolver contentResolver = getBaseContext.getContentResolver();  
		  boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver,  LocationManager.GPS_PROVIDER);
		  if (gpsStatus) {
			  return true;  
		  } else {
			  return false;  
		  }  
	 }
	
	@SuppressWarnings("deprecation")
	public Boolean validateNetworkProviderConnection(Context getBaseContext) {
		ContentResolver contentResolver = getBaseContext.getContentResolver();  
		  boolean providerStatus = Settings.Secure.isLocationProviderEnabled(contentResolver,  LocationManager.NETWORK_PROVIDER);
		  if (providerStatus) {
			  return true;  
		  } else {
			  return false;  
		  }
	 }
	
	public void alertbox(String title, String mymessage, Context context, final Activity activity){  
		  AlertDialog.Builder builder = new AlertDialog.Builder(context);  
		  builder.setMessage("Your Device's GPS is Disable")  
		  .setCancelable(false)  
		  .setTitle("Gps Status")  
		  .setPositiveButton("Gps On",  
		   new DialogInterface.OnClickListener() {  
		   public void onClick(DialogInterface dialog, int id) {  
		   // finish the current activity  
		   // AlertBoxAdvance.this.finish();  
		   Intent myIntent = new Intent(Settings.ACTION_SECURITY_SETTINGS);  
		   activity.startActivity(myIntent);  
		      dialog.cancel();  
		   }
		   })  
		   .setNegativeButton("Cancel",  
		   new DialogInterface.OnClickListener() {  
		   public void onClick(DialogInterface dialog, int id) {  
		    // cancel the dialog box  
		    dialog.cancel();  
		    }  
		   });  
		  AlertDialog alert = builder.create();  
		  alert.show();  
	}
	
	// unused method
	public boolean isOnline(Context ctx) {
	    NetworkInfo info = (NetworkInfo) ((ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
	    if (info != null && !info.isConnected() ) {
	        return false;
	    }
	    if (info != null && info.isRoaming()) {
	        return false;
	    }
	    return true;
	}
	
	public boolean isConnectingToInternet(Context context){
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null){
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null){
				for (int i = 0; i < info.length; i++){
					if (info[i].getState() == NetworkInfo.State.CONNECTED){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
}


