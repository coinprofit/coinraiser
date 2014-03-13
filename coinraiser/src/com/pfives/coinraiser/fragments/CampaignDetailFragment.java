package com.pfives.coinraiser.fragments;

import com.pfives.coinraiser.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class CampaignDetailFragment extends Fragment {

	Button pledgeButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_campaign_detail, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeViews();	
	}
	
	private void initializeViews(){
		View v = getView();
		pledgeButton = (Button)v.findViewById(R.id.pledge_button);
	}
	
	
	
	
	

}
