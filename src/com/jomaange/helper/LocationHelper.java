package com.jomaange.helper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.jomaange.constant.NetworkAPIURLConstant;
import com.jomaange.model.LocationModel;
import com.jomaange.model.ServiceResponseModel;

public class LocationHelper {
	
	public static ServiceResponseModel getLocations(){
		ServiceResponseModel respModel = null ;
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(NetworkAPIURLConstant.LOCATIONS_URL);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");
			}
			String response = sb.toString();
			if(response!=null && !response.isEmpty()){
				respModel = parseCityLocations(response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return respModel;
	}
	
	public static ServiceResponseModel parseCityLocations(String response){
		ServiceResponseModel respModel = new ServiceResponseModel();
		try{
			List<LocationModel> cityLocationList = new ArrayList<LocationModel>();
			JSONArray localityArrayObj = new JSONArray(response);
			JSONObject localityObj = null;
			JSONObject localityElementObj = null;
			for(int i=0; i<localityArrayObj.length();i++){
				localityObj = new JSONObject(localityArrayObj.getString(i));
				if(localityObj.has("Locality")){
					localityElementObj = localityObj.getJSONObject("Locality");
					LocationModel model = new LocationModel();
					if(localityElementObj.has("id") && localityElementObj.has("name")){
						model.setLocationId(localityElementObj.getString("id"));
						model.setLocationName(localityElementObj.getString("name"));
						cityLocationList.add(model);
					}
				}
			}
			if(cityLocationList!=null && cityLocationList.size() > 0){
				respModel.setSucessFlag(true);
				respModel = new ServiceResponseModel();
				respModel.setCityLocationsList(cityLocationList);
			}
		}catch(Exception ex){
			respModel.setSucessFlag(false);
			respModel.setErrorMessage("Server Internal Error");
			ex.printStackTrace();
		}
		return respModel;
	}
	
}
