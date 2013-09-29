package com.yotta.tracksit.DisplayTags;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.yotta.tracksit.Edit;
import com.yotta.tracksit.R;
import com.yotta.tracksit.Adapters.customAdapter;
import com.yotta.tracksit.Backend.HttpInterface;
import com.yotta.tracksit.Backend.HttpListener;
import com.yotta.tracksit.Models.Master;
import com.yotta.tracksit.Models.Tag;

public class TagsList extends Activity {

	ArrayList<Tag> tags = new ArrayList<Tag>();
	String SELECT_MASTER = "com.yotta.TracksIt.CallTags";
	
	Handler handler = new Handler();
	String TAG = "TagsList";
	private customAdapter adapter;
	ListView lv;
	Master currentMaster = new Master();
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tags_list);
		
			// Show the Up button in the action bar.
			setupActionBar();
			
			Intent i = getIntent();
			currentMaster = (Master) i.getParcelableExtra(SELECT_MASTER);
			
			adapter = new customAdapter(tags, getApplicationContext(), "Tag");
			
			populateTagsList();
			sendRequest();
	}
	
	private synchronized void populateTagsList() {

	lv = (ListView) findViewById(android.R.id.list);
	
	lv.setSelector(R.drawable.selector_list);
    lv.setAdapter(adapter);
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
	        case android.R.id.home:
	        	startActivity(new Intent(this, MastersList.class));
			default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	void edit()
	{
		Intent intent = new Intent(this, Edit.class);
		startActivity(intent);		
	}
	
	
	void sendRequest()
	{
		HttpListener hListener = new HttpListener() {
			
			@Override
			public void onSuccess(final String response) {
				// TODO Auto-generated method stub
				
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						//JSONObject jObject = new JSONObject(response);
						
						/*
						 * [{"master_tags": [], "master_id": "M1", "master_name": "wow"}, 
						 * {"master_tags": [{"tag_name": "U", "tag_id": "GHIF"}, 
						 * {"tag_name": "CK", "tag_id": "OOOO"}], "master_id": "M2", "master_name": "balls"}]
						 */
						try{
							
							Log.d("DEBUG LOAD", "OnTagsSuccessLoad");
							JSONArray allMaster = new JSONArray(response);
				        	
			                for (int i = 0; i < allMaster.length(); i++) {
				                JSONObject mastersObject = allMaster.getJSONObject(i);
				                
				               
				                if (mastersObject.getString("master_id").equals(currentMaster.masterID))
				                {
				                	JSONArray registeredTagObject = new JSONArray(mastersObject.getString("master_tags"));

					                for (int j = 0; j < registeredTagObject.length(); j++) {
					                	
					                	JSONObject tagsObject = registeredTagObject.getJSONObject(j);
					                	Tag tempMaster = new Tag();
					                	
					                	tempMaster.tagName = tagsObject.getString("tag_name");
						                tempMaster.tagID = tagsObject.getString("tag_id");
						                
						                tags.add(tempMaster);
					                }
					                
					                
					                
				                }
				                
			                }
			                //populateMastersList();
			                //lv.setAdapter(new customAdapter(masters, getApplicationContext(), "Master"));
			                Log.d("DEBUG LOAD", tags.toString());
							 adapter.notifyDataSetChanged();
						}
						catch (JSONException e) {
						    e.printStackTrace();
						}
					}
				});
			}

			@Override
			public void onFailure(String response) {
				// TODO Auto-generated method stub

				final String _response = response;

				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.e(TAG, _response);
					}
				});
			}
		};
		HttpInterface.entriesGetData(hListener);
	}

	
	@Override
	public void onBackPressed() {
		startActivity(new Intent(this, MastersList.class));
	}

}
