package com.pfives.coinraiser.application;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;

public class CoinRaiserApplication extends Application {
	
	public static final String API_ENDPOINT = "https://coinraiser.herokuapp.com/";
	private static final String APPLICATION_PREFS = "CoinRaiser.prefs";
	public static final String USERNAME = "username";
	private RequestQueue requestQueue;
	private ObjectMapper objectMapper;
	public static String CoinRaiserVersionNumber;
	private static CoinRaiserApplication _instance;
	
	public static CoinRaiserApplication getInstance() {
		return _instance;
	}

	public CoinRaiserApplication() {
		super();
		_instance = this;
	}

	public SharedPreferences getApplicationPreferences() {
		return getSharedPreferences(APPLICATION_PREFS, Context.MODE_PRIVATE);
	}

	private void initializeRequestQueue() {
		requestQueue = Volley.newRequestQueue(getApplicationContext());
	}

	public RequestQueue getRequestQueue() {
		return requestQueue;
	}
	
	public ObjectMapper getMapper() {
		if(objectMapper == null) {
			objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		}
		return objectMapper;
	}
	

	@Override
	public void onCreate() {
		super.onCreate();		
		initializeRequestQueue();
		setCoinRaiserVersionNumber();
//		ApiRequest.setOauthToken(getApplicationPreferences().getString(OAUTH_ACCESS_TOKEN, null));
//		if(getApplicationPreferences().getString(OAUTH_ACCESS_TOKEN, null) != null) {
//			getUserInformation();
//		}
	}
	
	private void setCoinRaiserVersionNumber() {
		try {
			CoinRaiserVersionNumber = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}
}
