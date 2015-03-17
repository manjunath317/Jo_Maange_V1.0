package com.jomaange.model;

import java.io.Serializable;

/**
 * This model used to set the details of location. The list of locations are retrieved from the server with respect to city id or city name.
 * @author manjunathj
 *
 */
public class LocationModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String locationId;
	private String locationName;
	private String cityId;
	private String cityName;
	
	public LocationModel(){
		
	}
	
	public LocationModel(String locationId, String locationName){
		this.locationId = locationId;
		this.locationName = locationName;
	}
	
	public LocationModel(String locationId, String locationName, String cityId){
		this.locationId = locationId;
		this.locationName = locationName;
		this.cityId = cityId;
	}
	
	public LocationModel(String locationId, String locationName, String cityId, String cityName){
		this.locationId = locationId;
		this.locationName = locationName;
		this.cityId = cityId;
		this.cityName = cityName;
	}
	
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	

}
