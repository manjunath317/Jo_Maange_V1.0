package com.jomaange.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.jomaange.constant.AppConstants;

import android.util.Log;


/**
 * This class used to get the location list from google Places API. This adapter currently using it for auto complete location display with threshold length of 3.
 * @author Manjunath Jakkandi
 *
 */
public class LocationAutoCompleteAdapter {

	private static final String LOG_TAG = "Places_api";
	public static ArrayList<String> getAutoSuggestionLocations(String input){
		ArrayList<String> resultList = new ArrayList<String>();
		StringBuilder jsonResults = new StringBuilder();
		HttpURLConnection conn = null;
		try {
			StringBuilder sb = new StringBuilder(AppConstants.GOOGLE_PLACES_API_BASE);
			sb.append("?sensor=false&key=" + AppConstants.GOOGLE_CONSOLE_API_KEY);
            sb.append("&components=country:IN");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));
            URL url = new URL(sb.toString());
            conn = (HttpURLConnection) url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				jsonResults.append(line);
			}
			} catch (MalformedURLException e) {
	            Log.e(LOG_TAG, "Error processing Places API URL", e);
	            return resultList;
	        } catch (IOException e) {
	            Log.e(LOG_TAG, "Error connecting to Places API", e);
	            return resultList;
	        } finally {
	            if (conn != null) {
	                conn.disconnect();
	            }
	        }
		
		try {
			JSONObject predictions = new JSONObject(jsonResults.toString());
			JSONArray ja = new JSONArray(predictions.getString("predictions"));

			for (int i = 0; i < ja.length(); i++) {
				JSONObject jo = (JSONObject) ja.get(i);
				resultList.add(jo.getString("description"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return resultList;
	}
}
