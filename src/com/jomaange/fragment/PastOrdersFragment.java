package com.jomaange.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jomaange.activity.EntityDetailsActivity;
import com.jomaange.activity.R;

public class PastOrdersFragment extends Fragment {
	
	public PastOrdersFragment(){
		
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_past_orders, container, false);
        
        Intent intent = new Intent(getActivity().getApplicationContext(), EntityDetailsActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		
        return rootView;
    }
}
