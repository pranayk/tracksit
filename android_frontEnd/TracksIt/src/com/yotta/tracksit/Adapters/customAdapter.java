package com.yotta.tracksit.Adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.yotta.tracksit.R;
import com.yotta.tracksit.Models.Master;
import com.yotta.tracksit.Models.Tag;


public class customAdapter extends BaseAdapter implements ListAdapter {

	private ArrayList<?> customData;
    Context customContext;
    String model;
	
	public customAdapter(ArrayList<?> data, Context c, String model){
        customData = data;
        customContext = c;
        this.model = model;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return customData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return customData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View  customView = convertView;
        if ( customView == null)
        {
           LayoutInflater vi = (LayoutInflater) customContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           customView = vi.inflate(R.layout.edit_list, null);
        }

        TextView item = (TextView)customView.findViewById(R.id.ItemName);
        
        if(this.model == "Master")
        {
        	Master tempMaster = (Master) customData.get(position);
            item.setText(tempMaster.masterName);

        }
        else if(this.model == "Tag")
        {
        	Tag tempTag = (Tag) customData.get(position);
            item.setText(tempTag.tagName);
        }
        
       
        
       return customView;
	}
	
	
	
}


