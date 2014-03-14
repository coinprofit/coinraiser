package com.pfives.coinraiser.fragments;

import java.util.ArrayList;

import com.pfives.coinraiser.R;
import com.pfives.coinraiser.activities.CampaignDetailActivity;
import com.pfives.coinraiser.activities.CoinbaseConnectActivity;
import com.pfives.coinraiser.activities.CreateCampaignActivity;
import com.pfives.coinraiser.api.ApiRequest.ApiResponseListener;
import com.pfives.coinraiser.api.GetAllCampaignsRequest;
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

public class AllCampaignsFragment extends Fragment {

	private ArrayList<Campaign> allCampaigns = new ArrayList<Campaign>();
	private ListView campaignsList;
	private View bottomSubmitContainer;
	private Button bottomConnectCoinbaseButton;
	private Context context;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		context = getActivity();
		initializeViews();
		checkShouldHideCoinbaseConnect();
		setupListeners();
		fetchAllCampaigns();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_all_campaigns, container, false);
	}

	private void initializeViews() {
		View v = getView();
		bottomSubmitContainer = v.findViewById(R.id.bottom_submit_container);
		bottomConnectCoinbaseButton = (Button)v.findViewById(R.id.bottom_connect_coinbase);
	}

	private void checkShouldHideCoinbaseConnect() {
		boolean userIsConnected = false;
		if(userIsConnected) {
			bottomSubmitContainer.setVisibility(View.GONE);
		} else {
			bottomSubmitContainer.setVisibility(View.VISIBLE);
		}
	}

	private void setupListeners() {
		bottomConnectCoinbaseButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startCoinbaseConnectActivity();
			}
		});
	}

	private void startCoinbaseConnectActivity() {
		Intent intent = new Intent(context, CoinbaseConnectActivity.class);
		startActivity(intent);
	}

	private void fetchAllCampaigns() {
		new GetAllCampaignsRequest().get(new ApiResponseListener() {

			@Override
			public void onSuccess(ApiResponse response) {
				setupAllCampaignsList();
			}

			@Override
			public void onFailure(ApiResponse result) {
				// Toast.makeText(context, "Unable to load campaigns",
				// Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void setupAllCampaignsList() {
		AllCampaignsAdapter allCampaignsAdapter = new AllCampaignsAdapter(context, allCampaigns);
		campaignsList.setAdapter(allCampaignsAdapter);
		campaignsList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				String campaignId = "";//allCampaigns.get(0).
				startCampaignDetailActivity(campaignId);
			}
		});
	}

	private void startCreateCampaignActivity() {
		Intent i = new Intent(getActivity(), CreateCampaignActivity.class);
		startActivity(i);
	}

	private void startCampaignDetailActivity(String campaignId) {
		Intent intent = new Intent(context, CampaignDetailActivity.class);
		intent.putExtra(CampaignDetailActivity.CAMPAIGN_ID, campaignId);
		startActivity(intent);
	}

	public class AllCampaignsAdapter extends BaseAdapter {

		Context context;
		ArrayList<Campaign> allCampaigns = new ArrayList<Campaign>();

		public AllCampaignsAdapter(Context context, ArrayList<Campaign> allCampaigns) {
			this.context = context;
			this.allCampaigns = allCampaigns;
		}

		@Override
		public int getCount() {
			return allCampaigns.size();
		}

		@Override
		public Object getItem(int position) {
			return allCampaigns.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			CampaignRowHolder campaignRowHolder;
			if(v == null) {
				v = LayoutInflater.from(context).inflate(R.layout.campaign_row_item, parent, false);
				campaignRowHolder = new CampaignRowHolder();
				campaignRowHolder.campaignName = (TextView)v.findViewById(R.id.campaign_name);
				campaignRowHolder.mainPhoto = (ImageView)v.findViewById(R.id.main_photo);
				v.setTag(campaignRowHolder);
			} else {
				campaignRowHolder = (CampaignRowHolder)v.getTag();
			}

			// campaignRowHolder.campaignName.setText(getItem(position).get)

			return v;
		}

	}

	static class CampaignRowHolder {
		TextView campaignName;
		ImageView mainPhoto;
	}

}
