package com.yotta.tracksit.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Tag implements Parcelable {

	
	public String tagID;
	public String tagName;
	public String masterID;
	public String timestamp;
	
	//Constructor - for any non parcelable object
	public Tag(){
		
	}
	
	public Tag(Parcel in){
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(tagID);
		dest.writeString(tagName);
		dest.writeString(masterID);
		dest.writeString(timestamp);
			
	}
	
	private void readFromParcel(Parcel in) {
		 
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		tagID = in.readString();
		tagName = in.readString();
		masterID = in.readString();
		timestamp = in.readString();
		}
	
	/**
    *
    * This field is needed for Android to be able to
    * create new objects, individually or as arrays.
    *
    * This also means that you can use use the default
    * constructor to create the object and use another
    * method to hydrate it as necessary.
    *
    * I just find it easier to use the constructor.
    * It makes sense for the way my brain thinks ;-)
    *
    */
   public static final Parcelable.Creator<Tag> CREATOR =
   	new Parcelable.Creator<Tag>() {
           public Tag createFromParcel(Parcel in) {
               return new Tag(in);
           }

           public Tag[] newArray(int size) {
               return new Tag[size];
           }
       };
}
