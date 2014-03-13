package com.pfives.coinraiser.api;

import java.util.Map;

import com.pfives.coinraiser.responses.CreateCampaignResponse;

public class CreateCampaignRequest extends ApiRequestor<CreateCampaignResponse> {
	
	private String campaignName;
	private String description;
	private String goalAmount;
	
	public CreateCampaignRequest(String campaignName, String description, String goalAmount){
		super("campaigns", CreateCampaignResponse.class);
		this.campaignName = campaignName;
		this.description = description;
		this.goalAmount = goalAmount;
	}

	@Override
	Map<String, String> getPostData() {
		Map<String, String> postData = super.getPostData();
		postData.put("campaign", campaignName);
		postData.put("description", description);
		postData.put("goal", goalAmount);
		return postData;
	}
	
	
	
	

}
