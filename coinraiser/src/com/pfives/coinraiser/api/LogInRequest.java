package com.pfives.coinraiser.api;

import java.util.Map;

import com.pfives.coinraiser.responses.LogInResponse;

public class LogInRequest extends ApiRequestor<LogInResponse>{
	
	private String email;
	private String password;
	
	public LogInRequest(String email, String password){
		super("users", LogInResponse.class);
		this.email = email; 
		this.password = password;
	}

	@Override
	Map<String, String> getQueryParameters() {
		Map<String, String> params = super.getQueryParameters();
		params.put("email", email);
		params.put("password", password);
		return params;
	}

	
	
	
}
