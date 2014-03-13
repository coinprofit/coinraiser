package com.pfives.coinraiser.activities;

import com.pfives.coinraiser.R;
import com.pfives.coinraiser.fragments.AllCampaignsFragment;
import com.pfives.coinraiser.fragments.MyCampaignsFragment;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends FragmentActivity {

	private static final String ALL_CAMPAIGNS_FRAGMENT_TAG = AllCampaignsFragment.class.getName();
	private static final String MY_CAMPAIGNS_FRAGMENT_TAG = MyCampaignsFragment.class.getName();
	public static int currentFragmentIndex = 0;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);		
		setContentView(R.layout.activity_home);		
		setupTabs();
	}

	private void setupTabs() {
	
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab allCampaignsTab = actionBar.newTab().setTabListener(
				new TabListener<AllCampaignsFragment>(this, ALL_CAMPAIGNS_FRAGMENT_TAG, AllCampaignsFragment.class));
		Bundle selectPhotosArgs = new Bundle();

		//selectPhotosArgs.putString(SelectPhotosFragment.BUCKET_NAME, currentBucket);
		//selectPhotosArgs.putSerializable(SelectPhotosFragment.PREVIOUSLY_ADDED_PHOTOS, previouslyAddedPhotoUrls);
		//selectPhotosArgs.putBoolean(SelectPhotosFragment.ARE_TABS, true);
		allCampaignsTab.setTag(selectPhotosArgs);
		TextView actionBarTabLeft = (TextView)LayoutInflater.from(this).inflate(R.layout.actionbar_tab, null);
		actionBarTabLeft.setText("All Campaigns");
		allCampaignsTab.setCustomView(actionBarTabLeft);
		actionBar.addTab(allCampaignsTab);

		Bundle selectVideosArgs = new Bundle();

		Tab myCampaignsTab = actionBar.newTab().setTabListener(new TabListener<MyCampaignsFragment>(this, MY_CAMPAIGNS_FRAGMENT_TAG, MyCampaignsFragment.class)); 
		TextView actionBarTabRight = (TextView)LayoutInflater.from(this).inflate(R.layout.actionbar_tab, null);
		actionBarTabRight.setText("My Campaigns");
		myCampaignsTab.setTag(selectVideosArgs);
		myCampaignsTab.setCustomView(actionBarTabRight);
		actionBar.addTab(myCampaignsTab);
		if(currentFragmentIndex == 1){
			actionBar.selectTab(myCampaignsTab);
		}
		
	}
	
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {

		private Fragment fragment;
		private final FragmentActivity activity;
		private final String tag;
		private final Class<T> klazz;

		public TabListener(FragmentActivity activity, String tag, Class<T> klazz) {
			this.activity = activity;
			this.tag = tag;
			this.klazz = klazz;
		}

		@Override
		public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
			FragmentManager fm = activity.getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fm.beginTransaction();
			//fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right);
			
			Fragment preInitializedFragment = fm.findFragmentByTag(tag);
			
			if(preInitializedFragment == null){
				fragment = Fragment.instantiate(activity, klazz.getName(), (Bundle)tab.getTag());
				if(tag.equals(ALL_CAMPAIGNS_FRAGMENT_TAG)){
					//fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_left);
				}
				fragmentTransaction.add(R.id.fragment_container, fragment, tag);
			}else{
				fragmentTransaction.attach(preInitializedFragment);
			}
			fragmentTransaction.commit();
		}

		@Override
		public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
			FragmentManager fm = activity.getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fm.beginTransaction();
			Fragment preInitializedFragment = fm.findFragmentByTag(tag);
			
			if (preInitializedFragment != null) {
				if(preInitializedFragment instanceof AllCampaignsFragment){
					//fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
				//}else if(preInitializedFragment instanceof SelectVideosFragment){
					//fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
				}
	            fragmentTransaction.detach(preInitializedFragment);
	        } else if (fragment != null) {
	        	fragmentTransaction.detach(fragment);
	        }
			fragmentTransaction.commit();			
		}

		@Override
		public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		}
	}

}
