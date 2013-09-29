package com.yotta.http;

import java.util.Hashtable;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import android.util.Log;

public class HttpDispatcher extends Thread {
	
	final static int GET = 1;
	final static int POST = 2;
	//final static int GetImage = 3;
	static Hashtable<Integer, HttpDispatcher> networkThreads = new Hashtable<Integer, HttpDispatcher>();
	static int count = 0;
	
	private String mRequestURI;
	private int mHTTPMethod;
	private HttpListener mListener;
	private JSONObject mJObject;
	private int mPosition;
	
	public HttpDispatcher (String requestURI, int HTTPMethod, HttpListener Listener, JSONObject _jobject) {
		
		mRequestURI = requestURI;
		mHTTPMethod = HTTPMethod;
		mListener = Listener;
		mJObject = _jobject;
		count += 1;
	}
	
	public HttpDispatcher (String requestURI, int HTTPMethod, HttpListener Listener, int position) {
		
		mRequestURI = requestURI;
		mHTTPMethod = HTTPMethod;
		mListener = Listener;
		count += 1;
		mPosition = position;
	}
	
	public void run() {
		
		//remove this later
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    
	    
	    //Calling onSuccess from everywhere in Http connection. 
	    //TODO handle the exceptions properly once the api is ready
		if (mHTTPMethod == GET)
		{
			try 
			{
	            HttpGet httpget = new HttpGet(mRequestURI);
	            HttpResponse rp = httpclient.execute(httpget);
	            
	            if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	            {
	                    String response = EntityUtils.toString(rp.getEntity());
	                    mListener.onSuccess(response);
	                    
	            }
	            else
	            {
	            	String response = EntityUtils.toString(rp.getEntity());
	                mListener.onFailure(response);
	            }
	            
		    } catch (Exception e) {
		            Log.e("HttpDispatcherGet", "Error in GET", e);
		            mListener.onFailure("Exception Thrown in Get");
		            e.printStackTrace();
		    }
		}
		else if (mHTTPMethod == POST)
		{
			
		    HttpPost httppost = new HttpPost(mRequestURI);
		    try 
		    {
		    	httppost.setEntity(new StringEntity(mJObject.toString(), "UTF8"));
		    	httppost.setHeader("Content-type", "application/json");
		    	HttpResponse rp = httpclient.execute(httppost);
		    	
		    	if(rp.getStatusLine().getStatusCode() == HttpStatus.SC_CREATED 
		    			|| rp.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	            {
	                    String response = EntityUtils.toString(rp.getEntity());
	                    mListener.onSuccess(response);
	                    
	            }
	            else
	            {
	            	String response = EntityUtils.toString(rp.getEntity());
	                mListener.onFailure(response);
	            }
		    	
		    } catch (Exception e) {
		    	Log.e("HttpDispatcherPost", "Error in POST", e);
		    	mListener.onFailure("Exception Thrown in Post");
		    	e.printStackTrace();
		    }
		}
	}
	
	public static void dispatchPOST (String request, HttpListener Listener, JSONObject jobject) {
		
		HttpDispatcher postRequest = new HttpDispatcher(request, POST, Listener, jobject);
		
		// Add this to the hasttable 
		networkThreads.put(count, postRequest);
		
		// Call the run
		postRequest.start();
	}
	
	public static void dispatchGET (String request, HttpListener Listener) {
		
		HttpDispatcher getRequest = new HttpDispatcher(request, GET, Listener, 0);
		
		// Add this to the hasttable 
		networkThreads.put(count, getRequest);
		
		// Call the run
		getRequest.start();
	}

}

