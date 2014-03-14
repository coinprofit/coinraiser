package com.pfives.coinraiser.fragments;

import java.util.ArrayList;

import com.pfives.coinraiser.R;
import com.pfives.coinraiser.activities.CampaignDetailActivity;
import com.pfives.coinraiser.activities.CoinbaseConnectActivity;
import com.pfives.coinraiser.activities.TutorialActivity;
import com.pfives.coinraiser.api.ApiRequest.ApiResponseListener;
import com.pfives.coinraiser.api.GetMyCampaignsRequest;
import com.pfives.coinraiser.models.Campaign;
import com.pfives.coinraiser.responses.ApiResponse;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyCampaignsFragment extends Fragment{

	private ArrayList<Campaign> myCampaigns = new ArrayList<Campaign>();
	private View noCampaignsContainer;
	private ListView campaignsList;
	private View bottomSubmitContainer;
	private Button bottomConnectCoinbaseButton;
	private Button connectCoinBaseButton;
	private Button tutorialButton;
	private Context context;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		initializeViews();
		setupListeners();
		fetchCampaigns();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_my_campaigns, container, false);
	}
	
	private void initializeViews(){
		View v = getView();
		noCampaignsContainer = v.findViewById(R.id.no_campaigns_container);
		campaignsList = (ListView)v.findViewById(R.id.campaigns_list);
		bottomSubmitContainer = v.findViewById(R.id.bottom_submit_container);
		bottomConnectCoinbaseButton = (Button)v.findViewById(R.id.bottom_connect_coinbase);
		connectCoinBaseButton = (Button)v.findViewById(R.id.connect_coinbase);
		tutorialButton = (Button)v.findViewById(R.id.tutorial);
	}
	
	private void setupListeners(){
		bottomConnectCoinbaseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startCoinbaseConnectActivity();
			}
		});
		connectCoinBaseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startCoinbaseConnectActivity();
			}
		});
		tutorialButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startTutorialActivity();
			}
		});
	}
	
	private void startCoinbaseConnectActivity(){
		Intent intent = new Intent(context, CoinbaseConnectActivity.class);
		startActivity(intent);		
	}
	
	private void startTutorialActivity(){
		Intent intent = new Intent(context, TutorialActivity.class);
		startActivity(intent);
	}

	private void fetchCampaigns(){
		new GetMyCampaignsRequest().get(new ApiResponseListener() {
			
			@Override
			public void onSuccess(ApiResponse response) {
				setupMyCampaigns();
			}
			
			@Override
			public void onFailure(ApiResponse result) {
				Toast.makeText(getActivity(), "Unable to load campaigns. Please try again.", Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	private void setupMyCampaigns(){
		if(myCampaigns != null && myCampaigns.size() > 0){
			displayMyCampaigns();
			setupMyCampaignsList();
		}else{
			displayNoCampaigns();
		}
	}
	
	private void displayNoCampaigns(){
		noCampaignsContainer.setVisibility(View.VISIBLE);		
		campaignsList.setVisibility(View.GONE);
		bottomSubmitContainer.setVisibility(View.GONE);
	}
	
	private void displayMyCampaigns(){
		campaignsList.setVisibility(View.VISIBLE);
		bottomSubmitContainer.setVisibility(View.VISIBLE);
		noCampaignsContainer.setVisibility(View.GONE);		
	}
	
	private void setupMyCampaignsList(){
		MyCampaignsAdapter myCampaignsAdapter = new MyCampaignsAdapter(context, myCampaigns);
		campaignsList.setAdapter(myCampaignsAdapter);
		campaignsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				String campaignId = "";// = myCampaigns.get(position).getCampaignId();
				startCampaignDetailActivity(campaignId);
			}
		});
	}
	
	private void startCampaignDetailActivity(String campaignId){
		Intent intent = new Intent(context, CampaignDetailActivity.class);
		intent.putExtra(CampaignDetailActivity.CAMPAIGN_ID, campaignId);
		startActivity(intent);
	}
	
	public class MyCampaignsAdapter extends BaseAdapter{

		Context context;
		ArrayList<Campaign> myCampaigns = new ArrayList<Campaign>();
		
		public MyCampaignsAdapter(Context context, ArrayList<Campaign> myCampaigns){
			this.context = context;
			this.myCampaigns = myCampaigns;
		}
		
		@Override
		public int getCount() {
			return myCampaigns.size();
		}

		@Override
		public Object getItem(int position) {
			return myCampaigns.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			CampaignRowHolder campaignRowHolder;
			if(v == null){
				v = LayoutInflater.from(context).inflate(R.layout.campaign_row_item, parent, false);
				campaignRowHolder = new CampaignRowHolder();
				campaignRowHolder.campaignName = (TextView)v.findViewById(R.id.campaign_name);
				campaignRowHolder.mainPhoto = (ImageView)v.findViewById(R.id.main_photo);
				v.setTag(campaignRowHolder);
			}else{
				campaignRowHolder = (CampaignRowHolder)v.getTag();
			}
			
			//campaignRowHolder.campaignName.setText(getItem(position).get)

			
			return v;
		}
		
	}
	
	static class CampaignRowHolder{
		TextView campaignName;
		ImageView mainPhoto;
	}
	
}
