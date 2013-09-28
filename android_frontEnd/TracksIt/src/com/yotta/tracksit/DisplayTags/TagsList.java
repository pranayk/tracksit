package com.yotta.tracksit.DisplayTags;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.yotta.tracksit.Edit;
import com.yotta.tracksit.R;
import com.yotta.tracksit.Adapters.customAdapter;
import com.yotta.tracksit.Models.Tag;

public class TagsList extends Activity {

	
	String SELECT_MASTER = "com.yotta.TracksIt.CallTags";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tags_list);
		
			// Show the Up button in the action bar.
			setupActionBar();
				
		
			Intent i = getIntent();
			ArrayList<Tag> tags = i.getParcelableArrayListExtra(SELECT_MASTER);
			
			ListView lv = (ListView) findViewById(android.R.id.list);
			
			lv.setSelector(R.drawable.selector_list);
		    lv.setAdapter(new customAdapter(tags, this, "Tag"));
	}
		

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tags_list, menu);
		return true;
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

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
}
