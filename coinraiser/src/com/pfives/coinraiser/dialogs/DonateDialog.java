package com.pfives.coinraiser.dialogs;

import com.pfives.coinraiser.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class DonateDialog extends DialogFragment{

	
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		
		View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_donate, null);
		builder.setView(v);
		
		Button pledgeButton = (Button)v.findViewById(R.id.pledge_button);
		
		pledgeButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				makeDonationRequest();
			}
		});
		return builder.create();
		
	}	
	
	private void makeDonationRequest(){
		//on success dismiss
	}
}
