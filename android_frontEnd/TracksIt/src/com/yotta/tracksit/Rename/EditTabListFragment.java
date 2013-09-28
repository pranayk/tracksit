package com.yotta.tracksit.Rename;


import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.yotta.tracksit.R;
import com.yotta.tracksit.Adapters.customAdapter;
import com.yotta.tracksit.Models.Master;
import com.yotta.tracksit.Models.Tag;


public class EditTabListFragment extends ListFragment {

	
	private String[] MastersName = {"masterA", "masterB", "masterC"};
	private String[] TagsName = {"tagA", "tagB", "tagC"};
	
	final ArrayList<Master> masters = new ArrayList<Master>();
	final ArrayList<Tag> tags = new ArrayList<Tag>();
	  
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    
		
		super.onActivityCreated(savedInstanceState);
	    
		int tabNo = getArguments().getInt("pageIndex");
		
		
		//Loads data depending on the tab
		if(tabNo == 0)
		{
		    for(int i=0; i<3; i++) {
		         Master masterDetail = new Master();
		         masterDetail.masterName = MastersName[i];
		         masters.add(masterDetail);
		    }
		    
		    getListView().setSelector(R.drawable.selector_list);
		    setListAdapter(new customAdapter(masters, getActivity(), "Master"));
		    
		}
		else if(tabNo == 1)
		{
			for(int i=0; i<3; i++) {
		         Tag tagDetail = new Tag();
		         tagDetail.tagName = TagsName[i];
		         tags.add(tagDetail);
		    }
			
			getListView().setSelector(R.drawable.selector_list);
		    setListAdapter(new customAdapter(tags, getActivity(), "Tag"));
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
		
	
}



