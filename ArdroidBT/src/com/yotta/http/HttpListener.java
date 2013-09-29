package com.yotta.http;

public interface HttpListener {
	
	void onSuccess(String response);
	
	void onFailure(String response);

}
