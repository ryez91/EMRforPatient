package com.example.emr;

import java.util.List;

import com.example.elecmr.RegiInfo;
import com.example.elecmr.RegiInfoSource;
import com.example.test_bluetooth.BluetoothActivity;
import com.example.test_bluetooth.DeviceListActivity;
import com.example.trustworthy.encryption;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Activity_Homepage extends Activity{
	
	//private final String TAG = "Base Activity";
	
	private Button communication;
	private Button records;
	//private Button changepw;
	private Button btnhplogoff;
	
	private RegiInfoSource infosource; 
	private int id;
	
	private String myname;
	private RegiInfo mydata;
	private String key;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);
		
		Bundle myBundle = this.getIntent().getExtras();
		myname = myBundle.getString("username");
		key = myBundle.getString("key");
		
		//test username
		Toast.makeText(getApplicationContext(), myname, Toast.LENGTH_LONG).show();
		
		records = (Button)findViewById(R.id.btn_record);
		communication = (Button)findViewById(R.id.communication);
		//changepw = (Button)findViewById(R.id.btn_passwordmanagement);
		btnhplogoff = (Button)findViewById(R.id.btn_homepagelogoff);
		

        infosource = new RegiInfoSource(this);
		infosource.open(); 
		
		RegiInfo b =infosource.searchsameNameRegiInfo(myname);
		id = b.getId();
		
		List<RegiInfo> information = infosource.getBasicInfoaslist(id);
		mydata = information.get(0);
		
		records.setOnClickListener(recordjumpListener);
		communication.setOnClickListener(communicationListener);
		//changepw.setOnClickListener(changepwListener);
		btnhplogoff.setOnClickListener(hplogoffListener);
		
	}
	
	private OnClickListener recordjumpListener = new OnClickListener(){
		@Override
		public void onClick(View arg0) {
			//jump to show Record Page
			
			Intent myIntent = new Intent(getApplicationContext(),Activity_Login_recordshow.class);
			Bundle bundle = new Bundle();
			bundle.putInt("myID", id);
			bundle.putString("key", key);
			myIntent.putExtras(bundle);
			startActivity(myIntent);
		}
	};
	
	private OnClickListener communicationListener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// jump to communication page
			String FNsend = encryption.aesDecrypt(mydata.getFirstname(), key);
			String LNsend = encryption.aesDecrypt(mydata.getLastname(), key);
			String DOBsend =encryption.aesDecrypt(mydata.getDob(),key);
			String SSNsend = encryption.aesDecrypt(mydata.getSsn(),key);
			String GENDERsend = encryption.aesDecrypt(mydata.getGender(),key);
			String IIDsend = encryption.aesDecrypt(mydata.getInid(),key);
			String ADDsend = encryption.aesDecrypt(mydata.getAddress(),key);
			String PHONEsend = encryption.aesDecrypt(mydata.getPhone(),key);
			String ALLERGIESsend = encryption.aesDecrypt(mydata.getAllergies(),key);
			String MHsend = encryption.aesDecrypt(mydata.getMedicalhistory(),key);
			String message = FNsend +",,,"+LNsend +",,," + DOBsend +",,," + SSNsend +",,," + GENDERsend +",,," + IIDsend +",,," + ADDsend +",,," + PHONEsend
					 +",,," + ALLERGIESsend  +",,," + MHsend;
			//
			
			Intent myIntent = new Intent(getApplicationContext(),BluetoothActivity.class);
			Bundle bundle = new Bundle();
			bundle.putInt("myid", id);
			bundle.putString("key", key);
			bundle.putString("message", message);
			myIntent.putExtras(bundle);
			startActivity(myIntent);
			
		}
		
		
	};
	/*
	private OnClickListener changepwListener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			// jump to changepassword page
			
			Intent myIntent = new Intent(getApplicationContext(),Activity_Login_passwordchange.class);
			Bundle bundle = new Bundle();
			bundle.putInt("myid", id);
			bundle.putString("key", key);
			myIntent.putExtras(bundle);
			startActivity(myIntent);
			
		}
		
		
	};
	*/
	
	private OnClickListener hplogoffListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// jump to logoff
			
			Intent myIntent = new Intent(getApplicationContext(), Logoff.class);
			startActivity(myIntent);
			
		}
		
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
