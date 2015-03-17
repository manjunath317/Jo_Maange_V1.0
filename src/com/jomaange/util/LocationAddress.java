package com.jomaange.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationAddress {
	private static final String TAG = "LocationAddress";
	
	public static String getCurrentLocality(final double latitude, final double longitude, final Context context, final Handler handler){
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String result = null;
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            Log.i("addresslist >>> ",addressList+"");
            if (addressList != null && addressList.size() > 0) {
            	Log.i("inside addresslist >>> ","--------1");
                Address address = addressList.get(0);
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i < address.getMaxAddressLineIndex(); i++) {
                    sb.append(address.getAddressLine(i)).append(",");
                }
                result = sb.toString();
                Log.i("inside addresslist >>> result ",result);
            }
        }catch(Exception ex){
        	ex.printStackTrace();
        }
        return result;
	}

    public static String getAddressFromLocation(final double latitude, final double longitude, final Context context, final Handler handler) {
    	String addressLocation = "";
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    Log.i("addresslist >>> ",addressList+"");
                    if (addressList != null && addressList.size() > 0) {
                    	Log.i("inside addresslist >>> ","--------1");
                        Address address = addressList.get(0);
                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                        }
                        Log.i("inside addresslist >>> ",address.getLocality() +", "+address.getPremises());
                        sb.append(address.getLocality()).append("\n");
                        sb.append(address.getPostalCode()).append("\n");
                        sb.append(address.getCountryName());
                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude + "\n\nAddress:\n" + result;
                        bundle.putString("address", result);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        result = "Latitude: " + latitude + " Longitude: " + longitude + "\n Unable to get address for this lat-long.";
                        bundle.putString("address", result);
                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
        return addressLocation;
    }
}
