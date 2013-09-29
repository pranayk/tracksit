package com.yotta.tracksit.Rename;


import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.yotta.tracksit.R;
import com.yotta.tracksit.Adapters.customAdapter;
import com.yotta.tracksit.Backend.HttpInterface;
import com.yotta.tracksit.Backend.HttpListener;
import com.yotta.tracksit.Models.Master;
import com.yotta.tracksit.Models.Tag;


public class EditTabListFragment extends ListFragment {

	final ArrayList<Master> masters = new ArrayList<Master>();
	final ArrayList<Tag> tags = new ArrayList<Tag>();
	
	Handler handler = new Handler();
	String TAG = "EditList";
	private customAdapter adapter;
	ListView lv;
	
	
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    
		super.onActivityCreated(savedInstanceState);
	    int tabNo = getArguments().getInt("pageIndex");
		
		//Loads data depending on the tab
		if(tabNo == 0)
		{	
			adapter = new customAdapter(masters, getActivity(), "Master");
			
			getListView().setSelector(R.drawable.selector_list);
		    setListAdapter(adapter);
		    
		  //Get latest components: component_update
			sendGetRequest("masters");
		}
		else if(tabNo == 1)
		{
			adapter = new customAdapter(tags, getActivity(), "Tag");
			
			
			getListView().setSelector(R.drawable.selector_list);
		    setListAdapter(adapter);
		    
		  //Get latest components: component_update
			sendGetRequest("tags");
		}
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, final int position, long id) {
	    
		Context context = getActivity();
		
		int tabNo = getArguments().getInt("pageIndex");
		
		
		AlertDialog.Builder alert = new AlertDialog.Builder(context);
		
		
		//Sets the data type depending on the tab
		if(tabNo == 0)
		{
			final Master item = masters.get(position);
			
			alert.setTitle(item.masterName);
			alert.setMessage("Rename");
			
			// Set an EditText view to get user input 
			final EditText input = new EditText(context);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  String  userInput = input.getText().toString();
			  // Do something with value!
			  item.masterName = userInput;
			  
			  masters.set(position, item);
			  
			  sendPostRequest("master", position);
			  
			  //Toast Message saying that the item has been renamed.
			  //Need to update the value in the current list.
				
			 }
			});
		}
		else if(tabNo == 1)
		{
			final Tag item = tags.get(position);
			
			
			alert.setTitle(item.tagName);
			alert.setMessage("Rename");

			
			// Set an EditText view to get user input 
			final EditText input = new EditText(context);
			alert.setView(input);

			alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
			  String  userInput = input.getText().toString();
			  // Do something with value!
			  item.tagName = userInput;
			  
			  tags.set(position, item);
			  
			  sendPostRequest("tag", position);
			  
			  
			  //Toast Message saying that the item has been renamed.
			  //Need to update the value in the current list.
				
			 }

			});
		}
		
		
		
		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		alert.show();
		
	}
	
		private void sendGetRequest(final String type) {
		// TODO Auto-generated method stub
		
		HttpListener hListener = new HttpListener() {
			
			@Override
			public void onSuccess(final String response) {
				// TODO Auto-generated method stub
				
				handler.post(new Runnable() {
	
					@Override
					public void run() {
						// TODO Auto-generated method stub
						//JSONObject jObject = new JSONObject(response);
						
						try{
							
							Log.d("EDIT LOAD", "OnSuccessLoad");
							JSONObject allComponents = new JSONObject(response);
				            
							JSONArray  mastersArray = allComponents.getJSONArray(type);
				        	
			                for (int i = 0; i < mastersArray.length(); i++) {
				                JSONObject mastersObject = mastersArray.getJSONObject(i);
				                
				                
				                if( type == "masters")
				                {
				                	Master tempMaster = new Master();
				                    
					                tempMaster.masterName = mastersObject.getString("master_name");
					                tempMaster.masterID = mastersObject.getString("master_id");
					                
					                masters.add(tempMaster);
				                }
				                else if( type == "tags")
				                {
				                	Tag tempTag = new Tag();
				                    
					                tempTag.tagName = mastersObject.getString("tag_name");
					                tempTag.tagID = mastersObject.getString("tag_id");
					                
					                tags.add(tempTag);
				                }
				            }
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
		HttpInterface.componentGetData(hListener);
	}
	
	public void sendPostRequest(String type, int position) {
		// TODO Auto-generated method stub
		
HttpListener l = new HttpListener() {
			
			@Override
			public void onSuccess(final String response) {
				// TODO Auto-generated method stub
				
				handler.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						//JSONObject jObject = new JSONObject(response);
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
			

			jObject.put("component_type", type);
			
			if( type == "master")
			{
				Master tempMaster = masters.get(position);
				
				jObject.put("component_id", tempMaster.masterID);
				jObject.put("component_name", tempMaster.masterName);
				
			}
			else if(type == "tag")
			{
				
				Tag tempTag = tags.get(position);
				
				jObject.put("component_id", tempTag.tagID);
				jObject.put("component_name", tempTag.tagName);
			}
			
			HttpInterface.sendUpdateData(l, jObject);
			
		}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e(getClass().getName(), " JSON Exception");
			e.printStackTrace();
			l.onFailure("JSON Exception");
		}
	}
		
}
	



