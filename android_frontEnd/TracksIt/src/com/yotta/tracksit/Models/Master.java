package com.yotta.tracksit.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Master implements Parcelable {

	public String masterID;
	public String masterName;
	
	//Constructor - for any non parcelable object
	public Master(){
		
	}
	
	public Master(Parcel in){
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
		dest.writeString(masterID);
		dest.writeString(masterName);
			
	}
	
	private void readFromParcel(Parcel in) {
		 
		// We just need to read back each
		// field in the order that it was
		// written to the parcel
		masterID = in.readString();
		masterName = in.readString();
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
   public static final Parcelable.Creator<Master> CREATOR =
   	new Parcelable.Creator<Master>() {
           public Master createFromParcel(Parcel in) {
               return new Master(in);
           }

           public Master[] newArray(int size) {
               return new Master[size];
           }
       };
}
