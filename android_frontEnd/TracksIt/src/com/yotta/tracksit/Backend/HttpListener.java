package com.yotta.tracksit.Backend;

public interface HttpListener {
	
	void onSuccess(String response);
	
	void onFailure(String response);

}
