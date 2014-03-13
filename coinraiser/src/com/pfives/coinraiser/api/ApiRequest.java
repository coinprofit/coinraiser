package com.pfives.coinraiser.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.net.Uri;
import android.util.Log;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import com.pfives.coinraiser.application.CoinRaiserApplication;
import com.pfives.coinraiser.responses.ApiResponse;

public class ApiRequest {

	private static final String AUTH_COOKIE = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";
	public static final String USER_AGENT = "User-Agent";
	public static String ANDROID_USER_AGENT = "CoinRaiser for Android ";
	private static final int INVALID_TOKEN = 401;
	private String endpoint;
	protected ApiResponseListener apiResponseListener;
	private Map<String, String> queryParameters = new HashMap<String, String>();
	private static Map<String, String> requestHeaders = new HashMap<String, String>();
	private RequestQueue requestQueue;
	private static String oauthToken;

	public enum Operation {
		POST, GET
	};

	public static String getOauthToken() {
		return oauthToken;
	}

	public static void setOauthToken(String oauthToken) {
		ApiRequest.oauthToken = oauthToken;
		setRequestHeaders();
	}

	public static Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}
	
	private static void setRequestHeaders() {
		requestHeaders = new HashMap<String, String>();
		StringBuilder builder = new StringBuilder();
		builder.append(ANDROID_USER_AGENT);
		builder.append(CoinRaiserApplication.CoinRaiserVersionNumber);
		builder.append(" (");
		builder.append(android.os.Build.MODEL);
		builder.append(" - ");
		builder.append(android.os.Build.VERSION.RELEASE);
		builder.append(")");
		requestHeaders.put(USER_AGENT, builder.toString());
		if(getOauthToken() != null) {
			requestHeaders.put(AUTH_COOKIE, BEARER_PREFIX.concat(getOauthToken()));
		}
	}

	public interface ApiResponseListener {
		public void onSuccess(ApiResponse response);

		public void onFailure(ApiResponse result);
	}

	protected String getClassName() {
		return getClass().getSimpleName();
	}

	public ApiRequest() {
		this("");
	}

	public ApiRequest(String endpoint) {
		requestQueue = CoinRaiserApplication.getInstance().getRequestQueue();
		this.endpoint = endpoint;
	}

	public void post(ApiResponseListener apiResponseListener) {
		this.apiResponseListener = apiResponseListener;
		requestQueue.add(prepareRequest(Method.POST));
	}

	public void get(ApiResponseListener apiResponseListener) {
		this.apiResponseListener = apiResponseListener;
		requestQueue.add(prepareRequest(Method.GET));
	}

	protected void deserializeResponse(String response, int responseCode) {
		ApiRequest.this.onSuccess(response, null, responseCode);
	}

	protected void deserializeErrorResponse(String response, int responseCode) {
		ApiRequest.this.onFailure(response, null, responseCode);
	}

	private String buildUri() {
		Uri.Builder builder = Uri.parse(CoinRaiserApplication.API_ENDPOINT).buildUpon();
		builder.appendEncodedPath(endpoint);

		queryParameters = getQueryParameters();
		if(queryParameters != null && !queryParameters.isEmpty()) {
			Iterator<Entry<String, String>> iter = queryParameters.entrySet().iterator();
			while(iter.hasNext()) {
				Entry<String, String> entry = iter.next();
				builder.appendQueryParameter(entry.getKey(), entry.getValue());
			}
		}
		return builder.build().toString();
	}

	public StringRequest prepareRequest(int method) {
		String requestUrl = buildUri();
		StringRequest stringRequest = new StringRequest(method, requestUrl, new Response.Listener<String>() {

			@Override
			public void onResponse(String response, int responseCode) {
				Log.v("into","Volley success response: "+responseCode);
				deserializeResponse(response, responseCode);
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {	
				Log.v("into","Volley error response: "+error.networkResponse.statusCode);
				if(error != null && error.networkResponse != null && error.networkResponse.data != null){
					if(error.networkResponse.statusCode == INVALID_TOKEN){
						ApiRequest.setOauthToken(null);
					}
					deserializeErrorResponse(new String(error.networkResponse.data), error.networkResponse.statusCode);
				}else{
					// TODO(Pat): Throw errors that are not network errors, eg. NullPointerException
					// TODO(Pat): Return something that is not a 500 here since that error code is
					//            misleading, eg. -1 or 0 or something of the sort.
					onFailure("", null, 500);
				}
			}
		}) {
			protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
				return getPostData();
			};

			public Map<String, String> getHeaders() {
				return requestHeaders;
			};
		};
		return stringRequest;
	}

	Map<String, String> getQueryParameters() {
		return new HashMap<String, String>();
	}

	Map<String, String> getPostData() {
		return new HashMap<String, String>();
	}

	public void onSuccess(String response, Object deserializedResponse, int responseCode) {
		apiResponseListener.onSuccess(new ApiResponse(response, deserializedResponse, responseCode));
	}

	public void onFailure(String response, Object deserializedResponse, int responseCode) {
		apiResponseListener.onFailure(new ApiResponse(response, deserializedResponse, responseCode));
	}
}