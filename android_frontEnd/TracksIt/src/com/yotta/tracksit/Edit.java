package com.yotta.tracksit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.yotta.tracksit.Adapters.MyFragmentPagerAdapter;
import com.yotta.tracksit.DisplayTags.MastersList;

public class Edit extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit);
		
		// Show the Up button in the action bar.
		setupActionBar();
		
		ViewPager editViewPager = (ViewPager) findViewById(R.id.editPager);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) findViewById(R.id.edit_pager_tab_strip);
        
        MyFragmentPagerAdapter pagerAdapter = new  MyFragmentPagerAdapter(getSupportFragmentManager(), "Edit");
 
        
        //viewPager.setOffscreenPageLimit(2);
        
        editViewPager.setAdapter(pagerAdapter);
        editViewPager.setPageMargin(20);
        
        pagerTabStrip.setDrawFullUnderline(true);
        pagerTabStrip.setTabIndicatorColor(0xffbb33);
 	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(this, MastersList.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, MastersList.class));
	}

}
