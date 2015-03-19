package com.jomaange.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

import com.jomaange.activity.R;
import com.jomaange.constant.NetworkAPIURLConstant;
import com.jomaange.model.EntityModel;
import com.jomaange.model.EntityTabContentModel;
import com.jomaange.model.EntityTabModel;
import com.jomaange.model.ServiceResponseModel;

public class EntityHelper {

	public static ServiceResponseModel getEntityList(String location){
		ServiceResponseModel respModel = null ;
		try{
			location = location.replace(" ", "%20");
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(NetworkAPIURLConstant.ENTITY_RESULTS_URL+location);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");
			}
			String response = sb.toString();
			if(response!=null && !response.isEmpty()){
				respModel = parseEntityList(response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return respModel;
	}
	
	public static ServiceResponseModel parseEntityList(String response){
		ServiceResponseModel respModel = new ServiceResponseModel();
		try{
			List<EntityModel> entityList = new ArrayList<EntityModel>();
			JSONArray respJsonArray =  new JSONArray(response);
			JSONObject venInfoJsonObj = null;
			JSONObject venInfoObj = null;
			JSONObject venAddInfoObj = null;
			for(int i=0;i<respJsonArray.length();i++){
				EntityModel model = new EntityModel();
				venInfoJsonObj =  respJsonArray.getJSONObject(i);
				venInfoObj = venInfoJsonObj.getJSONObject("venInfo");
				venAddInfoObj = venInfoJsonObj.getJSONObject("venAddInfo");
				
				// image path
				if(venInfoJsonObj.has("imgPath")){
					model.setImagePath("http://"+venInfoJsonObj.getString("imgPath"));
				}
				
				// vendor info
				model.setEntityId(venInfoObj.getString("id"));
				model.setVendor_id(venInfoObj.getString("vendor_id"));
				model.setTitle(venInfoObj.getString("title"));
				model.setIsdelivery(venInfoObj.getString("isdelivery"));
				model.setLandline(venInfoObj.getString("landline"));
				model.setIscorporate(venInfoObj.getString("iscorporate"));
				model.setAddress_id(venInfoObj.getString("address_id"));
				model.setTeam_id(venInfoObj.getString("team_id"));
				model.setMobile(venInfoObj.getString("mobile"));
				
				// vendor additional info
				model.setAddressType(venAddInfoObj.getString("addresstype"));
				model.setStreet(venAddInfoObj.getString("street"));
				model.setDoor(venAddInfoObj.getString("door"));
				model.setLocality(venAddInfoObj.getString("locality"));
				model.setPincode(venAddInfoObj.getString("pincode"));
				model.setLandmark(venAddInfoObj.getString("landmark"));
				model.setLatitude(venAddInfoObj.getString("lat"));
				model.setLongitude(venAddInfoObj.getString("lng"));
				model.setGeotagged(venAddInfoObj.getString("geotagged"));
				
				entityList.add(model);
			}
			if(entityList!=null && entityList.size() > 0){
				respModel.setSucessFlag(true);
				respModel = new ServiceResponseModel();
				respModel.setEntityList(entityList);
			}
		}catch(Exception ex){
			respModel.setSucessFlag(false);
			respModel.setErrorMessage("Server Internal Error");
			ex.printStackTrace();
		}
		return respModel;
	}
	
	public static ServiceResponseModel getEntityDetails(String vendorId){
		ServiceResponseModel respModel = null;
		try{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(NetworkAPIURLConstant.ENTITY_DETAILS_URL+vendorId);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent(), "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "n");
			}
			String response = sb.toString();
			if(response!=null && !response.isEmpty()){
				respModel = parseEntityDetails(response);
				//Log.i("response", response);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return respModel;
	}
	
	
	public static ServiceResponseModel parseEntityDetails(String response){
		ServiceResponseModel respModel = new ServiceResponseModel();
		Map<String, EntityTabModel> entityDetailsMap = new HashMap<String, EntityTabModel>();
		try {
			JSONObject responseObj = new JSONObject(response);
			JSONArray tagsArrayObj = responseObj.getJSONArray("tags");
			JSONArray productsArrayObj = responseObj.getJSONArray("products");
			// parse tags
			JSONObject obj = null;
			if(tagsArrayObj!=null && tagsArrayObj.length() > 0){
				for(int i=0;i<tagsArrayObj.length();i++){
					obj  = tagsArrayObj.getJSONObject(i);
				}
			}
			// parse products
			if(productsArrayObj!=null && productsArrayObj.length() > 0){
				String vprd=null;
				String cat = null;
				String prd = null;
				JSONObject obj1 = null;
				JSONObject obj2 = null;
				JSONObject obj3 = null;
				
				String itemId = null;
				String itemName = null;
				String itemPrice = null;
				String categoryId = null;
				String categoryName = null;
				
				List<EntityTabContentModel> entityTabContentModelList = null;
				EntityTabModel model =null;
				EntityTabContentModel model1 = null;
				for(int i=0;i<productsArrayObj.length();i++){
					obj  = productsArrayObj.getJSONObject(i);
					vprd = obj.getString("vprd");
					cat = obj.getString("cat");
					prd = obj.getString("prd");
					
					obj1 = new JSONObject(vprd);
					itemId = obj1.getString("id");
					itemName = obj1.getString("title");
					itemPrice = obj1.getString("price");
					
					obj2 = new JSONObject(cat);
					categoryName = obj2.getString("name");
					
					obj3 = new JSONObject(prd);
					categoryId = obj3.getString("category_id");
					if(entityDetailsMap.containsKey(categoryName)){
						model = entityDetailsMap.get(categoryName);
						entityTabContentModelList = model.getEntityTabContentModelList();
						model1 = new EntityTabContentModel(itemId, itemName, itemPrice, model.getEntityId(), model.getEntityTitle());
						entityTabContentModelList.add(model1);
						model.setEntityTabContentModelList(entityTabContentModelList);
						entityDetailsMap.put(categoryName, model);
					}else{
						model = new EntityTabModel(categoryId, categoryName);
						entityTabContentModelList = new ArrayList<EntityTabContentModel>();
						model1 = new EntityTabContentModel(itemId, itemName, itemPrice, categoryId, categoryName);
						entityTabContentModelList.add(model1);
						model.setEntityTabContentModelList(entityTabContentModelList);
						entityDetailsMap.put(categoryName, model);
					}
				}
				respModel.setEntityDetailsMap(entityDetailsMap);
				// reprint
				for(int i=0;i<entityDetailsMap.size();i++){
					Set<String> itr = entityDetailsMap.keySet();
					for(String tabName : itr){
						model = entityDetailsMap.get(tabName);
						entityTabContentModelList = model.getEntityTabContentModelList();
						for(EntityTabContentModel contentModel : entityTabContentModelList){
							//Log.i("entity details",model.getEntityId()+", "+model.getEntityTitle() +" >>>> "+contentModel.getItemId()+" , "+contentModel.getItemName()+" , "+contentModel.getItemPrice());
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respModel;
	}
	
	
}
