package com.yotta.tracksit.Backend;

import org.json.JSONObject;

public class HttpInterface {
	
	//Tests
	private static String testURL 		= "http://postcatcher.in/catchers/52473382b13ef30200000274";
	
	/**
	 * FOR POST METHODS: for dispatchPOST pass: (URL, httpListener, data: JSONObject jobject)
	 * FOR GET  METHODS: for dispatchGET  pass: (URL, httpListener)
	 */
	
	public static void sendData (HttpListener _Listener,  JSONObject jobject) {
		
		HttpDispatcher.dispatchPOST(testURL, _Listener, jobject);
	}
	
}
