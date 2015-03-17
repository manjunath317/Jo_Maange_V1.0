package com.jomaange.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.jomaange.activity.R;

public class ContactUsFragment extends Fragment {
	
	public ContactUsFragment(){
		
	}
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        
        Button button = (Button) rootView.findViewById(R.id.btn_contact_us_email_us);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"support@jomaange.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Query for Jo Maange");
                i.putExtra(Intent.EXTRA_TEXT   , "");
                try {
                    startActivity(Intent.createChooser(i, "Send Email..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity().getApplicationContext(), "Sorry! There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
		});
        
        
        
        
        return rootView;
    }
}
