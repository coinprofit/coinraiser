package com.pfives.coinraiser.fragments;

import com.pfives.coinraiser.R;
import com.pfives.coinraiser.activities.CreateCampaignActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class AllCampaignsFragment extends Fragment{

	private Button createCampaignButton;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeViews();
		setupListeners();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_all_campaigns, container, false);
	}

	
	private void initializeViews(){
		View v = getView();
		createCampaignButton = (Button)v.findViewById(R.id.create_campaign);
	}
	
	private void setupListeners(){
		createCampaignButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startCreateCampaignActivity();
			}
		});
	}
	
	private void startCreateCampaignActivity(){
		Intent i = new Intent(getActivity(), CreateCampaignActivity.class);
		startActivity(i);
	}
}
