package com.jomaange.model;

import java.io.Serializable;

public class EntityTabContentModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String itemId;
	private String itemName;
	private String itemPrice;
	private String itemQuantity;
	private String categoryId;
	private String categoryName;
	
	public EntityTabContentModel(){
		
	}
	
	public EntityTabContentModel(String itemId, String itemName, String itemPrice){
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
	}
	
	public EntityTabContentModel(String itemId, String itemName, String itemPrice, String categoryName){
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.categoryName = categoryName;
	}
	
	public EntityTabContentModel(String itemId, String itemName, String itemPrice, String categoryName, String categoryId){
		this.itemId = itemId;
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.categoryName = categoryName;
		this.categoryId = categoryId;
	}
	
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}
	public String getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(String itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
}
