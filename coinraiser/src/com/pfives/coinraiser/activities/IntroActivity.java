package com.pfives.coinraiser.activities;

import com.androauth.oauth.OAuth20Service;
import com.pfives.coinraiser.R;
import com.pfives.coinraiser.fragments.LogInFragment;
import com.pfives.coinraiser.fragments.SignUpFragment;
import com.twotoasters.android.hoot.HootResult;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class IntroActivity extends FragmentActivity {
	
	private static final String CLIENT_ID = "097ca4237fd7bf548eb5abf6901662768d21f154ba1417f79a6ad330ddde64ca";
	private static final String CLIENT_SECRET = "22a116fa2635926dcd09b55258f531e899199ceebe75fb4927a7a3876280b2f9";
	private static final String CALLBACK_URL = "coinraiser://redirect";
	private View loginButton;
	private View signupButton;
	private View go;
	private OAuth20Service service; 
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		initializeViews();
		setupListeners();
		// Initialize the Bitmonet SDK
        //Bitmonet.initialize(getApplicationContext(), API_KEY, API_SECRET, "coinraiser://initialized"); 
        // Set the address where you want to receive your Bitcoins
        //Bitmonet.setReceivingAddress("YOUR RECEIVING ADDRESS");
        // Set the transaction currency
        //Bitmonet.setTransactionCurrency("BTC");

        
	}
		
	private void initializeViews(){
		loginButton = findViewById(R.id.login);
		signupButton = findViewById(R.id.signup);
		go = findViewById(R.id.go);
	}
	
	private void setupListeners(){
		loginButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				attachLogInFragment();
			}
		});
		signupButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				attachSignUpFragment();
			}
		});
		go.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startHomeActivity();
			}
		});
	}
	
	private void startHomeActivity(){
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}
	
	private void attachLogInFragment(){
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		Fragment logInFragment = new LogInFragment();
		fragmentTransaction.replace(R.id.fragment_container, logInFragment, LogInFragment.class.getSimpleName()).commit();
	}

	
	private void attachSignUpFragment(){
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		Fragment signUpFragment = new SignUpFragment();
		fragmentTransaction.replace(R.id.fragment_container, signUpFragment, SignUpFragment.class.getSimpleName()).commit();
	}
	
	
	

}
