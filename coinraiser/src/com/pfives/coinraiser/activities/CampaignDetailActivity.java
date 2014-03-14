package com.pfives.coinraiser.activities;

import com.pfives.coinraiser.R;
import com.pfives.coinraiser.fragments.CampaignDetailFragment;
import com.pfives.coinraiser.fragments.CreateCampaignFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class CampaignDetailActivity extends FragmentActivity{

	public static final String CAMPAIGN_ID = "campaign_id";
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_home);
		
		attachCampaignDetailFragment();
	}
	
	private void attachCampaignDetailFragment(){
		
			CampaignDetailFragment campaignDetailFragment = new CampaignDetailFragment();
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			ft.replace(R.id.fragment_container, campaignDetailFragment, "Campaign").commit();
		
	}

}

