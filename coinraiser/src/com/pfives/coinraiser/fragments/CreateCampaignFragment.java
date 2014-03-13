package com.pfives.coinraiser.fragments;

import com.pfives.coinraiser.R;
import com.pfives.coinraiser.activities.CampaignDetailActivity;
import com.pfives.coinraiser.api.ApiRequest.ApiResponseListener;
import com.pfives.coinraiser.api.CreateCampaignRequest;
import com.pfives.coinraiser.responses.ApiResponse;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class CreateCampaignFragment extends Fragment{

	private EditText campaignNameField;
	private EditText descriptionField;
	private EditText goalAmountField;
	private Button launchButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_create_campaign, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeViews();
	}
	
	public static CreateCampaignFragment newInstance(){
		CreateCampaignFragment createCampaignFragment = new CreateCampaignFragment();
		return createCampaignFragment;
	}
	
	private void initializeViews(){
		View v = getView();
		campaignNameField = (EditText)v.findViewById(R.id.campaign_name);
		descriptionField = (EditText)v.findViewById(R.id.description);
		goalAmountField = (EditText)v.findViewById(R.id.goal_amount);
		launchButton = (Button)v.findViewById(R.id.launch_button);
	}
	
	private void setupListeners(){		
		launchButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				createCampaign();
			}
		});
	}
	
	private void createCampaign(){
		String campaignName = campaignNameField.getText().toString();
		String description = descriptionField.getText().toString();
		String goalAmount = goalAmountField.getText().toString();
		
		
		new CreateCampaignRequest(campaignName, description, goalAmount).post(new ApiResponseListener() {
			
			@Override
			public void onSuccess(ApiResponse response) {
				Log.v("into","successfully created campaign: "+response.getResponseString());
				launchCampaignDetailActivity();
			}
			
			@Override
			public void onFailure(ApiResponse result) {
				Log.v("into","failed to create campaign: "+result.getResponseString());
			}
		});
	}
	
	private void launchCampaignDetailActivity(){
		startActivity(new Intent(getActivity(), CampaignDetailActivity.class));
	}

}
