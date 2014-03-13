package com.pfives.coinraiser.fragments;

import com.pfives.coinraiser.R;
import com.pfives.coinraiser.activities.HomeActivity;
import com.pfives.coinraiser.api.ApiRequest.ApiResponseListener;
import com.pfives.coinraiser.api.LogInRequest;
import com.pfives.coinraiser.application.CoinRaiserApplication;
import com.pfives.coinraiser.responses.ApiResponse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class LogInFragment extends Fragment {

	private EditText emailField;
	private EditText passwordField;
	private Button loginButton;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initializeViews();
		setupListeners();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_login, container, false);
	}

	private void initializeViews() {
		View v = getView();
		emailField = (EditText)v.findViewById(R.id.email);
		passwordField = (EditText)v.findViewById(R.id.password);
		loginButton = (Button)v.findViewById(R.id.login_button);
	}

	private void setupListeners() {
		loginButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				loginUser();
			}
		});
	}

	private void loginUser() {
		Log.v("into","Going to login");
		String email = emailField.getText().toString();
		String password = passwordField.getText().toString();

		if(email != null && password != null) {
			new LogInRequest(email, password).get(new ApiResponseListener() {

				@Override
				public void onSuccess(ApiResponse response) {
					Log.v("into", "Successfully loggie in: " + response.getResponseString());
					saveUser("bob");
				}

				@Override
				public void onFailure(ApiResponse result) {
					Log.v("into", "Failed  ogged in" + result.getResponseString());
				}
			});
		}
	}
	
	private void saveUser(String name){
		SharedPreferences.Editor editor = CoinRaiserApplication.getInstance().getApplicationPreferences().edit();
		editor.putString(CoinRaiserApplication.USERNAME, name).commit();
		
		startHomeActivity();
	}
	
	private void startHomeActivity(){
		Intent intent = new Intent(getActivity(), HomeActivity.class);
		startActivity(intent);
	}

}
