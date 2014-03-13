package com.pfives.coinraiser.activities;

import com.pfives.coinraiser.R;
import com.pfives.coinraiser.fragments.CreateCampaignFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class CreateCampaignActivity extends FragmentActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		attachCreateCampaignFragment();
	}

	
	private void attachCreateCampaignFragment(){
		CreateCampaignFragment createCampaignFragment = CreateCampaignFragment.newInstance();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fragment_container, createCampaignFragment, "CAmpaign").commit();
	}
	
	
}
