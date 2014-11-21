package com.example.test_bluetooth;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.emr.R;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.bluetooth.*;

public class BluetoothActivity extends Activity {

	private BluetoothAdapter mBluetoothAdapter;
	private int REQUEST_ENABLE_BT=3;
	private int REQ_BT_ENABLE;
	private int REQUEST_DISCOVERABLE_BT=3;
	private int REQUEST_DEVICELIST = 4;
	private Button connect;
	private Button disconnect;
	private Button discoverable;
	private Button send;
	private TextView show;
	private ArrayAdapter<String> mPairedDevicesArrayAdapter;
	private ListView mConversationView;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;
    private BluetoothChatService sentcontent;
    private StringBuffer mOutStringBuffer;
    private EditText mOutEditText;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;
    private String mConnectedDeviceName = null;
    public static final String DEVICE_NAME = "device_name";
    public static final String TOAST = "toast";
    public static final String Message ="abc";
    public static final String Firstname_re="111";
    public static final String Lastname_re="222";
    private ArrayAdapter<String> mConversationArrayAdapter;
    private List<String> mConversationList;
    
    String readMessage;
    private int id;
    private String message;
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		setupUI();
	    /* mConversationView = (ListView) findViewById(R.id.listView1);
	     mConversationView.setAdapter(mConversationArrayAdapter);*/


        Bundle myBundle = this.getIntent().getExtras();
		message = myBundle.getString("message");
		id = myBundle.getInt("myid");
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//		 Toast.makeText(this, "Bluetooth begin", Toast.LENGTH_LONG).show();
		 
		 if (mBluetoothAdapter == null) {
	            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
	            finish();
	            return;
	        }
		 
		 String status;
		 mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
				// TODO Auto-generated method stub
		if (mBluetoothAdapter.isEnabled()) {
				String mydeviceaddress = mBluetoothAdapter.getAddress();
				Toast.makeText(BluetoothActivity.this, "Bluetooth is available", Toast.LENGTH_LONG).show();	
				String mydevicename = mBluetoothAdapter.getName();
				status = mydevicename + ";" + mydeviceaddress;
				Toast.makeText(BluetoothActivity.this, status, Toast.LENGTH_LONG).show();

			}
		else{
				 status = "Bluetooth is not Enabled.";
				 Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE); 
			     startActivityForResult(enableBtIntent, REQ_BT_ENABLE);
				 }
				
				 Toast.makeText(BluetoothActivity.this, status, Toast.LENGTH_LONG).show();
		
		 
	}
	
	
	
	private void setupChat() {
		

	     
		send=(Button) findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			//	sendMessage(Message);
				
				sendMessage(message);
			}
			
		 });
		 sentcontent = new BluetoothChatService(this, mHandler);

	        // Initialize the buffer for outgoing messages
	     mOutStringBuffer = new StringBuffer("");
		
	}
	
	 @Override
	    public void onStart() {
	        super.onStart();
	        if (mBluetoothAdapter.isEnabled()) {
	        if (sentcontent == null) setupChat();}
	 }
	 
	 @Override
	    public synchronized void onResume() {
	        super.onResume();
	        if (sentcontent != null) {
	            // Only if the state is STATE_NONE, do we know that we haven't started already
	            if (sentcontent.getState() == BluetoothChatService.STATE_NONE) {
	              // Start the Bluetooth chat services
	            	sentcontent.start();
	            }
	        }
	 }
	 @Override
	    public synchronized void onPause() {
	        super.onPause();
	       
	    }

	    @Override
	    public void onStop() {
	        super.onStop();
	        
	    }

	 @Override  
     protected void onDestroy() {  
       super.onDestroy();  
       if (sentcontent != null) sentcontent.stop();
     }  
	 
	 private void sendMessage(String Message) {
	        // Check that we're actually connected before trying anything
	        if (sentcontent.getState() != BluetoothChatService.STATE_CONNECTED) {
	            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_SHORT).show();
	            return;
	        }

	        // Check that there's actually something to send
	        if (Message.length() > 0) {
	        	//Message = Message + "\0";
	            // Get the message bytes and tell the BluetoothChatService to write
	            byte[] send = Message.getBytes();
	            sentcontent.write(send);

	            // Reset out string buffer to zero and clear the edit text field
	            mOutStringBuffer.setLength(0);
	           // address=mBluetoothAdapter
	            connectDevice=mConnectedDeviceName+address;
	            show.setText(connectDevice);
	        }
	    }
	 
	 protected void setupUI(){
		 mConversationList=new ArrayList<String>();
		 mConversationArrayAdapter = new ArrayAdapter<String>(this, R.layout.message,mConversationList);

	     mConversationView = (ListView) findViewById(R.id.listView1);
	     mConversationView.setAdapter(mConversationArrayAdapter);
		 
		 show = (TextView) findViewById(R.id.show); 
		 connect = (Button) findViewById(R.id.select);
		 
		 disconnect = (Button) findViewById(R.id.disconnect);
		 discoverable = (Button) findViewById(R.id.discover);
		 
		 connect.setOnClickListener(new OnClickListener() {
				@Override
			public void onClick(View arg0) {
	//				Toast.makeText(MainActivity.this, "blueeeee", Toast.LENGTH_LONG).show();
					// TODO Auto-generated method stub
					Intent myIntent = new Intent(BluetoothActivity.this,DeviceListActivity.class);
					startActivityForResult(myIntent, REQUEST_DEVICELIST);		  		
				}
		 }
			
			);
			
		//	disconnect.setVisibility(View.GONE);
			disconnect.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					 mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
					// TODO Auto-generated method stub
					
					mBluetoothAdapter.disable();  
		            //out.append("TURN_OFF BLUETOOTH");  
		            Toast.makeText(BluetoothActivity.this, "TURNING_OFF BLUETOOTH", Toast.LENGTH_LONG).show();	
					
				}
			}
			
			);
			
			//discoverable.setVisibility(View.GONE);
			discoverable.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
					// TODO Auto-generated method stub
				if (!mBluetoothAdapter.isDiscovering()) {  
		                  //out.append("MAKING YOUR DEVICE DISCOVERABLE");  
		           Toast.makeText(BluetoothActivity.this, "MAKING YOUR DEVICE DISCOVERABLE", Toast.LENGTH_LONG).show();  
		  
		           Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE); 
		           enableBtIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
		           startActivityForResult(enableBtIntent, REQUEST_DISCOVERABLE_BT);  
		                    
		            }
				}
			}
			
			);	
		 
	 }
	 
	 private final Handler mHandler = new Handler() {
		 
		 
	        @Override
	        public void handleMessage(Message msg) {
	            switch (msg.what) {
	            case MESSAGE_STATE_CHANGE:
	                switch (msg.arg1) {
	                case BluetoothChatService.STATE_CONNECTED:
	                	 mConversationList.clear();
	                	 mConversationArrayAdapter.notifyDataSetChanged();
	                    break;
	                case BluetoothChatService.STATE_CONNECTING:
	                   
	                    break;
	                case BluetoothChatService.STATE_LISTEN:
	                case BluetoothChatService.STATE_NONE:
	                    
	                    break;
	                }
	                break;
	      
	            case MESSAGE_WRITE:
	                byte[] writeBuf = (byte[]) msg.obj;
	                // construct a string from the buffer
	                String writeMessage = new String(writeBuf);
	                mConversationList.add(writeMessage);
               	 	mConversationArrayAdapter.notifyDataSetChanged();
	                break;
	           case MESSAGE_READ:
	                byte[] readBuf = (byte[]) msg.obj;
	                // construct a string from the valid bytes in the buffer
	   //             String readMessage = new String(readBuf, 0, msg.arg1);
	                readMessage = new String(readBuf, 0, msg.arg1);
	                String[] strings=readMessage.split(",,,");
	                for(int i =0;i<10;i++){
	                mConversationList.add(strings[i]);
	                
               	 	mConversationArrayAdapter.notifyDataSetChanged();
               	 	
               	 	
               	 	
	                }

	                break;
	            case MESSAGE_DEVICE_NAME:
	                // save the connected device's name
	                mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
	                Toast.makeText(getApplicationContext(), "Connected to "
	                               + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
	                
	                break;
	            case MESSAGE_TOAST:
	                Toast.makeText(getApplicationContext(), msg.getData().getString(TOAST),
	                               Toast.LENGTH_SHORT).show();
	                break;
	      
	            }
	        }
	    };
	    
	    @Override
		protected void onActivityResult (int requestCode, int resultCode, Intent data){
			if(resultCode == Activity.RESULT_OK){
				connectDevice(data, true);
			}
		}
	    String connectDevice;
	    String address;
	    private void connectDevice(Intent data, boolean secure) {
	        // Get the device MAC address
	        address = data.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
	        // Get the BluetoothDevice object
	        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
	        mConnectedDeviceName=device.getName().toString();
	        // Attempt to connect to the device
	        sentcontent.connect(device, secure);
	        connectDevice = mConnectedDeviceName+address;
	        show.setText(connectDevice);
	    }
		 
}
