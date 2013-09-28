package com.yotta.tracksit;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.yotta.tracksit.Adapters.MyFragmentPagerAdapter;

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
        //  pagerTabStrip.setBackgroundColor(color.background_light);
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
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
