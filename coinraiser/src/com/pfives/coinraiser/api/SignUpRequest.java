package com.pfives.coinraiser.api;

import java.util.Map;

public class SignUpRequest extends ApiRequest{
	
	private String name;
	private String email;
	private String password;
	
	public SignUpRequest(String name, String email, String password){
		super("users");
		this.name = name;
		this.email = email;
		this.password = password;
	}

	@Override
	Map<String, String> getPostData() {
		Map<String, String> postData = super.getPostData();
		postData.put("username", name);
		postData.put("email", email);
		postData.put("password", password);
		return postData;
	}
	

}
