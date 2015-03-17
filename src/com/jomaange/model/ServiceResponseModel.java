package com.jomaange.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


public class ServiceResponseModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String responseStr;
	private List<LocationModel> cityLocationsList;
	private List<EntityModel> entityList;
	private boolean errorFlag;
	private boolean sucessFlag;
	private String errorCode;
	private String successCode;
	private String errorTitle;
	private String successTitle;
	private String errorMessage;
	private String successMessage;
	private Map<String, EntityTabModel> entityDetailsMap;
	
	public String getResponseStr() {
		return responseStr;
	}
	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}
	public List<LocationModel> getCityLocationsList() {
		return cityLocationsList;
	}
	public void setCityLocationsList(List<LocationModel> cityLocationsList) {
		this.cityLocationsList = cityLocationsList;
	}
	public boolean isErrorFlag() {
		return errorFlag;
	}
	public void setErrorFlag(boolean errorFlag) {
		this.errorFlag = errorFlag;
	}
	public boolean isSucessFlag() {
		return sucessFlag;
	}
	public void setSucessFlag(boolean sucessFlag) {
		this.sucessFlag = sucessFlag;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getSuccessCode() {
		return successCode;
	}
	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}
	public String getErrorTitle() {
		return errorTitle;
	}
	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}
	public String getSuccessTitle() {
		return successTitle;
	}
	public void setSuccessTitle(String successTitle) {
		this.successTitle = successTitle;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getSuccessMessage() {
		return successMessage;
	}
	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}
	public List<EntityModel> getEntityList() {
		return entityList;
	}
	public void setEntityList(List<EntityModel> entityList) {
		this.entityList = entityList;
	}
	public Map<String, EntityTabModel> getEntityDetailsMap() {
		return entityDetailsMap;
	}
	public void setEntityDetailsMap(Map<String, EntityTabModel> entityDetailsMap) {
		this.entityDetailsMap = entityDetailsMap;
	}

}
