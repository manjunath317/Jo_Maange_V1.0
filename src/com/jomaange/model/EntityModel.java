package com.jomaange.model;

import java.io.Serializable;

public class EntityModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String entityId;
	private String vendor_id;
	private String isdelivery;
	private String landline;
	private String iscorporate;
	private String address_id;
	private String team_id;
	private String mobile;
	private String addressType;
	private String street;
	private String door;
	private String locality;
	private String pincode;
	private String landmark;
	private String title;
	private String latitude;
	private String longitude;
	private String geotagged;
	private String imagePath;
	
	public EntityModel(){
		
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getVendor_id() {
		return vendor_id;
	}

	public void setVendor_id(String vendor_id) {
		this.vendor_id = vendor_id;
	}

	public String getIsdelivery() {
		return isdelivery;
	}

	public void setIsdelivery(String isdelivery) {
		this.isdelivery = isdelivery;
	}

	public String getLandline() {
		return landline;
	}

	public void setLandline(String landline) {
		this.landline = landline;
	}

	public String getIscorporate() {
		return iscorporate;
	}

	public void setIscorporate(String iscorporate) {
		this.iscorporate = iscorporate;
	}

	public String getAddress_id() {
		return address_id;
	}

	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}

	public String getTeam_id() {
		return team_id;
	}

	public void setTeam_id(String team_id) {
		this.team_id = team_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getGeotagged() {
		return geotagged;
	}

	public void setGeotagged(String geotagged) {
		this.geotagged = geotagged;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

}
