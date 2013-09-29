package com.yotta.ardroidbt;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yotta.http.HttpInterface;
import com.yotta.http.HttpListener;
import com.yotta.models.TimeTask;

public class MainActivity extends Activity implements OnClickListener{
	
	Button Connect;
    Button Stop;
    Button startAgain;
    TextView Result;
    //private String dataToSend;
   
    private static final String TAG = "Yotta";
    private BluetoothAdapter mBluetoothAdapter = null;
    private BluetoothSocket btSocket = null;
    private OutputStream outStream = null;
    private static String address = "00:12:11:23:04:62"; //"00:07:80:99:57:64"
    private static final UUID MY_UUID = UUID
                    .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private InputStream inStream = null;
	Handler handler = new Handler();
	
	byte delimiter = 10; // '\n'
	byte carrReturn = 13; // '\r'
	
	boolean stopWorker = false;
	int readBufferPosition = 0;
	byte[] readBuffer = new byte[1024];
	
	private Context context;
	Handler handler2 = new Handler();
	TimeTask backgroundSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            
            context = getApplicationContext();
            
            Connect = (Button) findViewById(R.id.connect);
            Stop = (Button) findViewById(R.id.stop);
            startAgain = (Button) findViewById(R.id.startAgain);
            Result = (TextView) findViewById(R.id.result);

            Connect.setOnClickListener(this);
            Stop.setOnClickListener(this);

            CheckBt();
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            Log.e("Yotta", device.toString());

    }

    @Override
    public void onClick(View control) {
            switch (control.getId()) {
            case R.id.connect:
                    Connect();
                    break;
            case R.id.stop:
            		backgroundSync.stop();
                    break;
            case R.id.startAgain:
        		backgroundSync.start();
                break;
            }
    }

    private void CheckBt() {
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (!mBluetoothAdapter.isEnabled()) {
                    Toast.makeText(getApplicationContext(), "Bluetooth Disabled !",
                                    Toast.LENGTH_SHORT).show();
            }

            if (mBluetoothAdapter == null) {
                    Toast.makeText(getApplicationContext(),
                                    "Bluetooth null !", Toast.LENGTH_SHORT)
                                    .show();
            }
    }
   
            public void Connect() {
                    Log.d(TAG, address);
                    BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
                    
                    Log.d(TAG, "Connecting to ... " + device);
                    mBluetoothAdapter.cancelDiscovery();
                    try {
                            btSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
                            btSocket.connect();
                            Log.d(TAG, "Connection made.");
                            Log.d(TAG, mBluetoothAdapter.getBondedDevices().toString()+" Rush");
                            //writeData("Writing from App");
                            beginListenForData();
                    } catch (IOException e) {
                            try {
                                    btSocket.close();
                            } catch (IOException e2) {
                                    Log.d(TAG, "Unable to end the connection");
                            }
                            Log.d(TAG, "Socket creation failed");
                    }
            }
   
    private void writeData(String data) {
            try {
                    outStream = btSocket.getOutputStream();
            } catch (IOException e) {
                    Log.d(TAG, "Bug BEFORE Sending stuff", e);
            }

            String message = data;
            byte[] msgBuffer = message.getBytes();

            try {
                    outStream.write(msgBuffer);
            } catch (IOException e) {
                    Log.d(TAG, "Bug while sending stuff", e);
            }
    }

    @Override
    protected void onDestroy() {
            super.onDestroy();
   
                    try {
                    	if(btSocket != null)
                            btSocket.close();
                    } catch (IOException e) {
                    }
    }
   
    public void beginListenForData()   {
    	Log.d(TAG, "Now listening to device");
             try {
                            inStream = btSocket.getInputStream();
                    } catch (IOException e) {
                    }
             
            //Thread workerThread = new Thread(new Runnable()
            backgroundSync = new TimeTask(new Runnable() {
            	
                public void run()
                {                
                   //while(!stopWorker)
                   //{
                	Log.d(TAG, "inside loop run");
                        try
                        {
                        	//final long tempTime = System.currentTimeMillis();
                            int bytesAvailable = inStream.available();
                            //inStream.mark(bytesAvailable);
                            //final ArrayList<String> input = new ArrayList<String>();
                            final JSONArray jList = new JSONArray();
                            final String dateTime = getUTCtime(); 
                            if(bytesAvailable > 0)
                            {
                            	Log.d(TAG, "bytes available");
                                byte[] packetBytes = new byte[bytesAvailable];
                                inStream.read(packetBytes);
                                
                                for(int i=0;i<bytesAvailable;i++)
                                {
                                    byte b = packetBytes[i];
                                    //int test = (int)packetBytes[i];
                                    //Log.d("Byte: ", ""+ test);
                                    if(b == delimiter && b != carrReturn) //|| b != carrReturn
                                    {
                                        byte[] encodedBytes = new byte[readBufferPosition];
                                        //if(encodedBytes.length != 0) // readBufferPosition != 0
                                        //{
	                                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
	                                        if(encodedBytes.length != 0)
	                                        {
	                                        	//Log.d("IN TEXT APPEND: ", ":)");
		                                        final String data = new String(encodedBytes, "US-ASCII");
		                                        final JSONObject jObj = new JSONObject();
		                                        readBufferPosition = 0;
		                                        handler.post(new Runnable()
		                                        {
		                                            public void run()
		                                            {
		                                                    Log.d("MasterData", data);
		                                                    if(Result.getText().toString().equals("..")) {
		                                                            Result.setText(data);
		                                                    } else {
		                                                            Result.append("\n"+data);
		                                                    }
		                                                    //input.add(data);
		                                                    //JSON stuff
		                                                    try {
																jObj.put("tag_timestamp", dateTime);
																jObj.put("tag_id", data);
		                                                    /*boolean check = true;
		                                                    for(int i=0; i<jList.length(); i++) {
		                                                    	if(jList.getJSONObject(i).equals(jObj))
		                                                    		check = false;
		                                                    }
		                                                    if(check)*/
		                                                    	jList.put(jObj);
		                                                    } catch (JSONException e) {
																// TODO Auto-generated catch block
																e.printStackTrace();
															}
		                                            }
		                                        });
	                                        }
                                        //}
                                    }
                                    else
                                    {
                                    	if(b != 0 && b != carrReturn && (( b>=48 && b<=57 ) || ( b>=65 && b<=90 ) || ( b>=97 && b<=122 )))
                                    		readBuffer[readBufferPosition++] = b;
                                    	//else
                                    		
                                    }
                                    /*try
                                    {
                                    	inStream.reset();
                                    }
                                    catch (IOException ex)
                                    {
                                        //stopWorker = true;
                                    	Log.d(TAG, "IOException: "+ ex );
                                    }*/
                                    	
                                    
                                }
                                sendRequest(jList);
                            }
                            //inStream.reset();
                            
                            //stopWorker = true;
                        }
                        catch (IOException ex)
                        {
                            //stopWorker = true;
                        	Log.e(TAG, "IOException: "+ ex );
                        }
                   //}
                }
            });
            
            backgroundSync.start();
        }
    
    public String getUTCtime() {
    	//Time in GMT
    	//Date nDate = new Date();
    //	try {
    		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	dateFormatGmt.setTimeZone(TimeZone.getTimeZone("UTC"));

        	//Local time zone   
        	//SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	
			//nDate =  dateFormatLocal.parse( dateFormatGmt.format(new Date()) );
	//	} catch (ParseException e) {
	//		// TODO Auto-generated catch block
	//		e.printStackTrace();
	//	}
    	return dateFormatGmt.format(new Date());
    }
    
	public void sendRequest(JSONArray jList) {

		HttpListener l = new HttpListener() {

			
			@Override
			public void onSuccess(final String response) {
				// TODO Auto-generated method stub
				
				handler2.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						//JSONObject jObject = new JSONObject(response);
						Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
						
					}

				});

			}

			@Override
			public void onFailure(String response) {
				// TODO Auto-generated method stub

				final String _response = response;

				handler2.post(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Log.e(TAG, _response);

					}

				});
			}

		};
			
		// add elements to ArrayList, including duplicates
		//HashSet<String> hashSet = new HashSet<String>();
		//hashSet.addAll(newList);
		//newList.clear();
		//newList.addAll(hashSet);
			JSONObject jObject = new JSONObject();
			try {
				jObject.put("master_id", "M1");
				jObject.put("tags", jList);
				
				//jObject.
				HttpInterface.sendData(l, jObject);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e(getClass().getName(), " JSON Exception");
				e.printStackTrace();
				l.onFailure("JSON Exception");
		}
	}
}
