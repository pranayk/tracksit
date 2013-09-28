package com.yotta.tracksit.DisplayTags;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yotta.tracksit.Edit;
import com.yotta.tracksit.R;
import com.yotta.tracksit.Adapters.customAdapter;
import com.yotta.tracksit.Backend.HttpInterface;
import com.yotta.tracksit.Backend.HttpListener;
import com.yotta.tracksit.Models.Master;
import com.yotta.tracksit.Models.Tag;

public class MastersList extends ListActivity {

	
	ArrayList<Master> masters = new ArrayList<Master>();
	private String[] MastersName = {"masterA", "masterB", "masterC"};
	private String[] TagsName = {"tagA", "tagB", "tagC"};
	ArrayList<Tag> tags = new ArrayList<Tag>();
	
	String SELECT_MASTER = "com.yotta.TracksIt.CallTags";
	
	Handler handler = new Handler();
	String TAG = "MastersList";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_masters_list);
		sendRequest();
		populateMastersList();
	}

	private void populateMastersList() {
		
		
		//Populating Masters List
		for(int i = 0; i<3; i++)
		{
			 Master masterDetail = new Master();
	         masterDetail.masterName = MastersName[i];
	         masters.add(masterDetail);
		}
		
		ListView lv = (ListView) findViewById(android.R.id.list);

		
		lv.setSelector(R.drawable.selector_list);
	    lv.setAdapter(new customAdapter(masters, this, "Master"));
	
	    

		// React to user clicks on item
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		     public void onItemClick(AdapterView<?> parentAdapter, View view, int position, long id) {
		    	 
		    	 //Populating Tags List for Master
		    	 for(int i = 0; i<3; i++)
		 		{
		 			 Tag tagDetail = new Tag();
		 	         tagDetail.tagName = TagsName[i];
		 	         tags.add(tagDetail);
		 		}
		    		
		    	 Intent intent = new Intent(MastersList.this, TagsList.class);
		 		 intent.putParcelableArrayListExtra(SELECT_MASTER, tags);
		         
		    	 startActivity(intent);
		         
		     }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.masters_list, menu);
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

	public void sendRequest() {

		HttpListener l = new HttpListener() {

			
			@Override
			public void onSuccess(final String response) {
				// TODO Auto-generated method stub
				
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						//JSONObject jObject = new JSONObject(response);
						Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
						
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
			

			JSONObject jObject = new JSONObject();
			try {
				jObject.put("M1", "MAC SUKX, SO DOES WINDOWS :) - LONG LIVE LINUX");
				HttpInterface.sendData(l, jObject);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e(getClass().getName(), " JSON Exception");
				e.printStackTrace();
				l.onFailure("JSON Exception");
		}
	}

}
