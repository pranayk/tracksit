package com.yotta.tracksit.Backend;

import org.json.JSONObject;

public class HttpInterface {
	
	//Tests
	private static String testURL 		= "http://postcatcher.in/catchers/52477ce4b13ef30200000794";
	private static String componentGetAllURL = "http://tracksit-backend.appspot.com/component_getall/";
	private static String componentUpdateURL = "http://tracksit-backend.appspot.com/component_update/";
	private static String entriesGetURL = "http://tracksit-backend.appspot.com/entries_get/";
	
	
	/**
	 * FOR POST METHODS: for dispatchPOST pass: (URL, httpListener, data: JSONObject jobject)
	 * FOR GET  METHODS: for dispatchGET  pass: (URL, httpListener)
	 */
	
	public static void sendData (HttpListener _Listener,  JSONObject jobject) {
		
		HttpDispatcher.dispatchPOST(testURL, _Listener, jobject);
	}
	
public static void componentGetData (HttpListener _Listener) {
		
		HttpDispatcher.dispatchGET(componentGetAllURL, _Listener);
	}

public static void sendUpdateData (HttpListener _Listener,  JSONObject jobject) {
	
	HttpDispatcher.dispatchPOST(componentUpdateURL, _Listener, jobject);
}

public static void entriesGetData(HttpListener _Listener) {
	HttpDispatcher.dispatchGET(entriesGetURL, _Listener);
	
}

	
	
}
