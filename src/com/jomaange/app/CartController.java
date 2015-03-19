package com.jomaange.app;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.jomaange.model.EntityTabContentModel;

public class CartController {

	private static CartController cart = null;
	private int subTotal;
	private int promotionDiscount;
	private String couponCode;
	private String grandTotal;
	private int cartItemsQuantity;
	private List<EntityTabContentModel> entityItemList;
	
	private CartController(){
		
	}
	
	public int getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(int subTotal) {
		this.subTotal = subTotal;
	}

	public int getPromotionDiscount() {
		return promotionDiscount;
	}

	public void setPromotionDiscount(int promotionDiscount) {
		this.promotionDiscount = promotionDiscount;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(String grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	public int getCartItemsQuantity() {
		return cartItemsQuantity;
	}

	public void setCartItemsQuantity(int cartItemsQuantity) {
		this.cartItemsQuantity = cartItemsQuantity;
	}

	public List<EntityTabContentModel> getEntityItemList() {
		return entityItemList;
	}

	public void setEntityItemList(List<EntityTabContentModel> entityItemList) {
		this.entityItemList = entityItemList;
	}
	
	public void addCartItem(EntityTabContentModel model){
		if(getEntityItemList()!=null && getEntityItemList().size() > 0){
			this.entityItemList.add(model);
			setEntityItemList(this.entityItemList);
		}else{
			List<EntityTabContentModel> entityItemList = new ArrayList<EntityTabContentModel>();
			entityItemList.add(model);
			setEntityItemList(entityItemList);
		}
	}
	
	public void removeItem(EntityTabContentModel model){
		if(getEntityItemList()!=null && getEntityItemList().size() > 0){
			for(int i=0;i < getEntityItemList().size() ; i++){
				if(getEntityItemList().get(i).getItemId() == model.getItemId()){
					this.entityItemList.remove(i);
					break;
				}
			}
		}
		Log.i("entityItemList",entityItemList.toString());
	}
	
	public void editCartItem(EntityTabContentModel model, int quantity){
		boolean isExist = false;
		model.setItemQuantity(String.valueOf(quantity));
		if(getEntityItemList()!=null && getEntityItemList().size() > 0){
			for(int i=0;i < getEntityItemList().size() ; i++){
				if(getEntityItemList().get(i).getItemId() == model.getItemId()){
					this.entityItemList.set(i, model);
					isExist = true;
					break;
				}
			}
		}else{
			List<EntityTabContentModel> entityItemList = new ArrayList<EntityTabContentModel>();
			entityItemList.add(model);
			setEntityItemList(entityItemList);
			isExist = true;
		}
		if(!isExist){
			this.entityItemList.add(model);
		}
		Log.i("entityItemList",entityItemList.toString());
	}
	
	public String getCartItemQuantity(EntityTabContentModel model){
		String quantity = "0";
		if(getEntityItemList()!=null && getEntityItemList().size() > 0){
			for(int i=0;i < getEntityItemList().size() ; i++){
				if(getEntityItemList().get(i).getItemId() == model.getItemId()){
					quantity = getEntityItemList().get(i).getItemQuantity();
					break;
				}
			}
		}
		return quantity;
	}

	public static CartController getInstance(){
		if(cart == null){
			cart = new CartController();
		}
		return cart;
	}
	
	public static void setCart(CartController cart){
		CartController.cart = cart;
	}
	
}
