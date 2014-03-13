package com.pfives.coinraiser.api;

import org.codehaus.jackson.map.ObjectMapper;

import com.pfives.coinraiser.application.CoinRaiserApplication;

import android.util.Log;



public class ApiRequestor<T> extends ApiRequest{

	Class<T> klazz;
	
	public ApiRequestor(Class<T> klazz) {
		super();
		this.klazz = klazz;
	}
	
	public ApiRequestor(String resource, Class<T> klazz){
		super(resource);
		this.klazz = klazz;
	}

	@Override
	protected void deserializeResponse(String response, int responseCode) {
		Object deserializedResponse = deserialize(response);
		onSuccess(response, deserializedResponse, responseCode);
	}

	private Object deserialize(String response){
		T deserializedResponse = null;
		ObjectMapper mapper = CoinRaiserApplication.getInstance().getMapper();
		try {
			deserializedResponse = mapper.readValue(response, klazz);
		} catch (Exception e) {
			Log.e(getClassName(), "failed to deserialize: ", e);
		}
		return deserializedResponse;
	}
	
	@Override
	protected void deserializeErrorResponse(String response, int responseCode) {
		Object deserializedResponse = deserialize(response);
		onFailure(response, deserializedResponse, responseCode);
	}	
}
