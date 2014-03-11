package com.pfives.coinraiser.activities;

import com.pfives.coinraiser.R;
import com.pfives.coinraiser.R.id;
import com.pfives.coinraiser.R.layout;
import com.pfives.coinraiser.fragments.LogInFragment;
import com.pfives.coinraiser.fragments.SignUpFragment;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class IntroActivity extends FragmentActivity {

	private View loginButton;
	private View signupButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intro);
		initializeViews();
		setupListeners();
	}
		
	private void initializeViews(){
		loginButton = findViewById(R.id.login);
		signupButton = findViewById(R.id.signup);
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
