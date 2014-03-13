package com.pfives.coinraiser.responses;


public class ApiResponse {

	private String responseString;
	private int responseCode;
	private Object deserializedResult;
	
	public ApiResponse(){
	}
	public ApiResponse(String responseString, Object deserializedResult){
		setDeserializedResult(deserializedResult);
		setResponseString(responseString);
	}
	public ApiResponse(String responseString, Object deserializedResult, int responseCode){
		setDeserializedResult(deserializedResult);
		setResponseString(responseString);
		setResponseCode(responseCode);
	}
	public void setResponseString(String responseString){
		this.responseString = responseString;
	}
	public void setDeserializedResult(Object result){
		this.deserializedResult = result;
	}
	public String getResponseString(){
		return responseString;
	}
	public Object getDeserializedResult(){
		return deserializedResult;
	}
	public void setResponseCode(int responseCode){
		this.responseCode = responseCode;
	}
	public int getResponseCode() {
		return responseCode;
	}
}
