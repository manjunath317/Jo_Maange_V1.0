package com.jomaange.model;

import java.io.Serializable;
import java.util.List;

public class EntityTabModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String entityId;
	private String entityTitle;
	private List<EntityTabContentModel> entityTabContentModelList;
	
	
	public EntityTabModel(){
		
	}
	
	public EntityTabModel(String entityId, String entityTitle){
		this.entityId = entityId;
		this.entityTitle = entityTitle;
	}
	
	public EntityTabModel(String entityId, String entityTitle, List<EntityTabContentModel>entityTabContentModelList){
		this.entityId = entityId;
		this.entityTitle = entityTitle;
		this.entityTabContentModelList = entityTabContentModelList;
	}

	public String getEntityTitle() {
		return entityTitle;
	}

	public void setEntityTitle(String entityTitle) {
		this.entityTitle = entityTitle;
	}

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public List<EntityTabContentModel> getEntityTabContentModelList() {
		return entityTabContentModelList;
	}

	public void setEntityTabContentModelList(
			List<EntityTabContentModel> entityTabContentModelList) {
		this.entityTabContentModelList = entityTabContentModelList;
	}
	
	
}
