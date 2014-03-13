package com.pfives.coinraiser.fragments;

import com.androauth.api.CoinBaseApi;
import com.androauth.oauth.OAuth20Service;
import com.androauth.oauth.OAuth20Token;
import com.androauth.oauth.OAuth20Service.OAuth20ServiceCallback;
import com.bitmonet.BitmonetOAuthStatusListener;
import com.bitmonet.BitmonetPaymentStatusListener;
import com.pfives.coinraiser.R;
import com.pfives.coinraiser.activities.IntroActivity;
import com.pfives.coinraiser.api.ApiRequest.ApiResponseListener;
import com.pfives.coinraiser.api.SignUpRequest;
import com.pfives.coinraiser.application.CoinRaiserApplication;
import com.pfives.coinraiser.responses.ApiResponse;
import com.twotoasters.android.hoot.HootResult;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class SignUpFragment extends Fragment {

	private static final String CLIENT_ID = "097ca4237fd7bf548eb5abf6901662768d21f154ba1417f79a6ad330ddde64ca";
	private static final String CLIENT_SECRET = "22a116fa2635926dcd09b55258f531e899199ceebe75fb4927a7a3876280b2f9";
	private static final String CALLBACK_URL = "coinraiser://redirect";
	private Button coinbaseButton;
	private Button signupButton;
	private OAuth20Service service;
	private WebView webview;
	private TextView emailField;
	private TextView nameField;
	private TextView passwordField;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeViews();
		setupListeners();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_signup, container, false);
	}
	
	private void initializeViews(){
		View v = getView();
		coinbaseButton = (Button)v.findViewById(R.id.coinbase_connect);
		webview = (WebView)v.findViewById(R.id.webview);
		signupButton = (Button)v.findViewById(R.id.signup_button);
		emailField = (TextView)v.findViewById(R.id.email);
		nameField = (TextView)v.findViewById(R.id.name);
		passwordField = (TextView)v.findViewById(R.id.password);
	}

	private void setupListeners(){
		signupButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				signupUser();
			}
		});
		coinbaseButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				requestAuthorization();
			}
		});
	}
	
	private void signupUser(){
		Log.v("into","Going to sign up a user");
		String name = nameField.getText().toString();
		String email = emailField.getText().toString();
		String password = passwordField.getText().toString();
		if(name != null && email != null && password != null){
			new SignUpRequest(name, email, password).post(new ApiResponseListener() {
				
				@Override
				public void onSuccess(ApiResponse response) {
					Log.v("into","sign up succeeded: "+response.getResponseString());	
				}
				
				@Override
				public void onFailure(ApiResponse result) {
					Log.v("into","sign up failed: "+result.getResponseString());
				}
			});
		}else{
			//toast message
		}
	}
	
	private void startAuthentication(){
		Log.v("into","Going for it...");
		
		SharedPreferences prefs = CoinRaiserApplication.getInstance().getApplicationPreferences();
		final SharedPreferences.Editor editor = prefs.edit();
		
		
		service = OAuth20Service.newInstance(new CoinBaseApi(), CLIENT_ID, CLIENT_SECRET, new OAuth20ServiceCallback() {

			@Override
			public void onOAuthAccessTokenReceived(OAuth20Token token) {
				Log.v("into","Got it!: "+token.getAccessToken());
				editor.putString("access_token", token.getAccessToken());
				editor.putString("refresh_token", token.getRefreshToken()); 
				editor.commit();
				//getInfo(token); 
			}


			@Override
			public void onAccessTokenRequestFailed(HootResult result) {
				Log.v("into","Failed to get access token: "+result.getResponseString());
				
			}			
		});
		service.setApiCallback(CALLBACK_URL);
		service.setScope("all");
		service.setDuration("permanent");
		getUserVerification();	
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void getUserVerification(){
		webview.setVisibility(View.VISIBLE);
		webview.getSettings().setJavaScriptEnabled(true);
		webview.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.v("into","Should Override Url Loading: "+url);
				// Checking for our successful callback
				if(url.startsWith(CALLBACK_URL)) {
					webview.setVisibility(View.GONE);					
					service.getOAuthAccessToken(url);

				}
				return super.shouldOverrideUrlLoading(view, url);
			}

		});
		Log.v("into","Loading up the webview...");
		webview.loadUrl(service.getAuthorizeUrl());
	}
	
	public void requestAuthorization(){
		startAuthentication();
	}
	
}
