package com.yotta.tracksit.Rename;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import com.yotta.tracksit.R;
import com.yotta.tracksit.Adapters.customAdapter;
import com.yotta.tracksit.Models.Tag;

public class MastersTabListFragment extends ListFragment {
	
	private String[] tagsMaster1 = {"tagA", "tagB", "tagC"};
	private String[] tagsMaster2 = {"tagE", "tagF", "tagG"};
	
	final ArrayList<Tag> tags = new ArrayList<Tag>();
	
	
	@Override
	  public void onActivityCreated(Bundle savedInstanceState) {
	    
		super.onActivityCreated(savedInstanceState);
	    
		int tabNo = getArguments().getInt("pageIndex");
		
		//Loads data depending on the tab
	    for(int i=0; i<3; i++) {
	    	Tag tagDetail = new Tag();
	    	if(tabNo == 0)
				tagDetail.tagName = tagsMaster1[i];
	    	else if(tabNo == 1)
	    		  tagDetail.tagName = tagsMaster2[i];
		    tags.add(tagDetail);
		}
    
	    getListView().setSelector(R.drawable.selector_list);
	    setListAdapter(new customAdapter(tags, getActivity(), "Tag"));

	  }
	
}
