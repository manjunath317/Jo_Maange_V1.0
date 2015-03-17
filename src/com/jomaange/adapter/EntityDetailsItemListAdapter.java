package com.jomaange.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jomaange.activity.R;
import com.jomaange.app.CartController;
import com.jomaange.model.EntityTabContentModel;

public class EntityDetailsItemListAdapter extends BaseAdapter {
	
	private Activity activity;
    private LayoutInflater inflater;
    private List<EntityTabContentModel> entityItemList;
    public ImageLoader imageLoader; 
    private CartController cart;
    private TextView item_details_cart_items_qty;
    private TextView item_details_sub_total_text;
    
    public EntityDetailsItemListAdapter(Activity activity, List<EntityTabContentModel> entityItemList) {
        this.activity = activity;
        this.entityItemList = entityItemList;
        imageLoader=new ImageLoader(activity.getApplicationContext());
        this.cart = CartController.getInstance();
        this.item_details_cart_items_qty = (TextView) activity.findViewById(R.id.item_details_cart_items_qty);
        this.item_details_sub_total_text = (TextView) activity.findViewById(R.id.item_details_sub_total_text);
    }
 
    @Override
    public int getCount() {
        return entityItemList.size();
    }
 
    @Override
    public Object getItem(int location) {
        return entityItemList.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @SuppressLint("ResourceAsColor")
	@Override
    public View getView(final int position, View convertView, ViewGroup parent) {
    	
        if (inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_row_entity_item_details, null,true);
        }
        
        TextView entity_details_item_name = (TextView) convertView.findViewById(R.id.entity_details_item_name);
        TextView entity_details_item_price = (TextView) convertView.findViewById(R.id.entity_details_item_price);
        final TextView entity_details_item_qty = (TextView) convertView.findViewById(R.id.entity_details_item_qty);
        item_details_cart_items_qty = (TextView) activity.findViewById(R.id.item_details_cart_items_qty);
        item_details_sub_total_text = (TextView) activity.findViewById(R.id.item_details_sub_total_text);
        LinearLayout entity_details_item_qty_add = (LinearLayout) convertView.findViewById(R.id.entity_details_item_qty_add);
        LinearLayout entity_details_item_qty_less = (LinearLayout) convertView.findViewById(R.id.entity_details_item_qty_less);
        
        entity_details_item_name.setText(entityItemList.get(position).getItemName());
        entity_details_item_price.setText("Price : "+entityItemList.get(position).getItemPrice() +" ₹");
        final String quantity = entity_details_item_qty.getText().toString();
        
        entity_details_item_qty_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	final int pos = position;
            	if(quantity !=null && !quantity.isEmpty()){
            		int qty = Integer.parseInt(quantity);
                	qty = qty +1;
                	entity_details_item_qty.setText(qty+"");
                	cart.setCartItemsQuantity(cart.getCartItemsQuantity()+1);
                	cart.setSubTotal(cart.getSubTotal() + Integer.parseInt(entityItemList.get(position).getItemPrice()));
            	}else{
            		int qty = 0;
                	qty = qty +1;
                	entity_details_item_qty.setText(qty+"");
                	cart.setCartItemsQuantity(cart.getCartItemsQuantity()+1);
                	cart.setSubTotal(cart.getSubTotal() + Integer.parseInt(entityItemList.get(position).getItemPrice()));
            	}
            	cart.editCartItem(entityItemList.get(position));
            	Log.i("cart details", cart.getCartItemsQuantity() +" items, subtotal : "+cart.getSubTotal() +" Rs");
            	if(item_details_cart_items_qty!=null)
            	item_details_cart_items_qty.setText(cart.getCartItemsQuantity()+"");
            	item_details_sub_total_text.setText("Sub Total\n ₹  "+cart.getSubTotal());
            	notifyDataSetChanged();
            }
        });
        entity_details_item_qty_less.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	if(quantity !=null && !quantity.isEmpty()){
            		int qty = Integer.parseInt(quantity);
            		if(qty > 0){
            			qty = qty - 1;
            			entity_details_item_qty.setText(qty+"");
            			cart.setCartItemsQuantity(cart.getCartItemsQuantity()-1);
            			cart.setSubTotal(cart.getSubTotal() - Integer.parseInt(entityItemList.get(position).getItemPrice()));
            			if(qty == 0){
            				cart.removeItem(entityItemList.get(position));
            			}else{
            				cart.editCartItem(entityItemList.get(position));
            			}
            		}else{
            			cart.removeItem(entityItemList.get(position));
            		}
            	}else{
            		int qty = 0;
            		entity_details_item_qty.setText(qty+"");
            		cart.removeItem(entityItemList.get(position));
            	}
            	Log.i("cart details", cart.getCartItemsQuantity() +" items, subtotal : "+cart.getSubTotal() +" Rs");
            	if(item_details_cart_items_qty!=null)
            	item_details_cart_items_qty.setText(cart.getCartItemsQuantity()+" Items");
            	item_details_sub_total_text.setText("Sub Total\n ₹"+cart.getSubTotal());
            	notifyDataSetChanged();
            }
            
        });
        
        return convertView;
    }

}
