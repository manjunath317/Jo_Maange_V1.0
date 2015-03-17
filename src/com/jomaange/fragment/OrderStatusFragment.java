package com.jomaange.fragment;

import com.jomaange.activity.R;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class OrderStatusFragment extends Fragment {
	
	public OrderStatusFragment(){
		
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_order_status, container, false);
        return rootView;
    }
}
