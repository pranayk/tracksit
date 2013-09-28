package com.yotta.tracksit;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.yotta.tracksit.DisplayTags.MastersList;

public class MainActivity extends FragmentActivity {

	
	public final static String SELECT_EVENT = "com.yotta.TracksIt.EditCall";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mastersList();
		
		/*
		ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.pager_tab_strip);
        MyFragmentPagerAdapter pagerAdapter = new  MyFragmentPagerAdapter(getSupportFragmentManager(), "Masters");
 
        
        
        //viewPager.setOffscreenPageLimit(2);
        
        viewPager.setAdapter(pagerAdapter);
        viewPager.setPageMargin(20);
        
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(0xffbb33);
        
        */
    }

	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
				
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_edit:
	         	edit();
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	void edit()
	{
		Intent intent = new Intent(this, Edit.class);
        startActivity(intent);		
	}
	void mastersList()
	{
		Intent intent = new Intent(this, MastersList.class);
        startActivity(intent);		
	}
	
}
