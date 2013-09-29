package com.yotta.http;

import org.json.JSONObject;

public class HttpInterface {
	
	//Tests
	private static String URL 		= "http://tracksit-backend.appspot.com/entries_update/";
	private static String testURL 		= "http://postcatcher.in/catchers/5247ad36b13ef3020000097c";
	
	/**
	 * FOR POST METHODS: for dispatchPOST pass: (URL, httpListener, data: JSONObject jobject)
	 * FOR GET  METHODS: for dispatchGET  pass: (URL, httpListener)
	 */
	
	public static void sendData (HttpListener _Listener,  JSONObject jobject) {
		
		HttpDispatcher.dispatchPOST(URL, _Listener, jobject);
	}
	
}
