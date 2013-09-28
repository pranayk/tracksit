package com.yotta.tracksit.Adapters;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.yotta.tracksit.Rename.EditTabListFragment;
import com.yotta.tracksit.Rename.MastersTabListFragment;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private String[] MastersPageTitle = {"MASTER 1", "MASTER 2"};
	private String[] EditPageTitle = {"MASTERS", "TAGS"};
	
	public String fragmentCaller;

	public MyFragmentPagerAdapter(FragmentManager fm, String caller) {
		
		super(fm);
		this.fragmentCaller = caller;
		
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		
		
		if(this.fragmentCaller == "Masters") {
		
			Fragment mastersFragment = new MastersTabListFragment();
			Bundle arguments = new Bundle();
	        arguments.putInt("pageIndex", position);
	        mastersFragment.setArguments(arguments);
	        return mastersFragment;
		}
		else if(this.fragmentCaller == "Edit")
		{
			Fragment editFragment = new EditTabListFragment();
			Bundle arguments = new Bundle();
	        arguments.putInt("pageIndex", position);
	        editFragment.setArguments(arguments);
	        return editFragment;
	        
		}
		
		return null;
		
        
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		
		if(this.fragmentCaller == "Masters")
			return MastersPageTitle.length;
		else if(this.fragmentCaller == "Edit")
			return EditPageTitle.length;
		
		return 2;
	}	
	
	@Override
	public CharSequence getPageTitle(int position) {
        
		if(this.fragmentCaller == "Masters")
			return MastersPageTitle[position];
		else if(this.fragmentCaller == "Edit")
			return EditPageTitle[position];
		
		return null;
	}	

}
