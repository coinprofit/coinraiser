package com.pfives.coinraiser.responses;

import org.codehaus.jackson.annotate.JsonProperty;

public class LogInResponse {

	@JsonProperty("username")
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
