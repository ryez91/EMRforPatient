package com.example.emr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.elecmr.RegiInfo;
import com.example.elecmr.RegiInfoSource;
import com.example.trustworthy.encryption;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class Activity_Login_recordshow extends Activity{
	
	ListView listrecord;
	Button butedit;
	Button butback;
	TextView texthome;
	private int id;
	private String key;
	private RegiInfoSource infosource;
	private final String[] list_titles = {"First Name", "Last Name", "Date of Birth", "Gender", "SSN",
			"Insurance ID", "Address", "Phone", "Allergies", "Medical History"};
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_show);
		
		Bundle myBundle = this.getIntent().getExtras();
		id = myBundle.getInt("myID");
		key = myBundle.getString("key");
		butedit =(Button)findViewById(R.id.btn_edrecords);
		butback = (Button)findViewById(R.id.btn_back);
		listrecord = (ListView)findViewById(R.id.listView_record);
		//texthome = (TextView)findViewById(R.id.txt_home);
		
		butedit.setOnClickListener(butforeditListener);
		butback.setOnClickListener(butbackListener);
		
		/*
		infosource = new RegiInfoSource(this);
		infosource.open(); 
		
		
		List<RegiInfo> information = infosource.getBasicInfoaslist(id);
		RegiInfo info = information.get(0);
		
		String[] list_content = new String[10];
		list_content[0] = info.getFirstname();
		list_content[1] = info.getLastname();
		list_content[2] = info.getDob();
		list_content[3] = info.getGender();
		list_content[4] = info.getSsn();
		list_content[5] = info.getInid();
		list_content[6] = info.getAddress();
		list_content[7] = info.getPhone();
		list_content[8] = info.getAllergies();
		list_content[9] = info.getMedicalhistory();
		
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String,String>>();
		for(int i=0; i<10; i++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", list_titles[i]);
			map.put("content", list_content[i]);
			mylist.add(map);
		}
		
		SimpleAdapter myRecord = new SimpleAdapter(this, mylist, R.layout.listrecord,new String[] {"title", "content"},
				new int[]{R.id.txt_recordhint, R.id.txt_recordetails});
		
		listrecord.setAdapter(myRecord);
		*/
	}
	
	@Override
	public void onStart(){
		super.onStart();
		
		infosource = new RegiInfoSource(this);
		infosource.open(); 
		
		
		List<RegiInfo> information = infosource.getBasicInfoaslist(id);
		RegiInfo info = information.get(0);
		
		String[] list_content = new String[10];
		list_content[0] = encryption.aesDecrypt(info.getFirstname(), key);
		list_content[1] = encryption.aesDecrypt(info.getLastname(),key);
		list_content[2] = encryption.aesDecrypt(info.getDob(),key);
		list_content[3] = encryption.aesDecrypt(info.getGender(),key);
		list_content[4] = encryption.aesDecrypt(info.getSsn(),key);
		list_content[5] = encryption.aesDecrypt(info.getInid(),key);
		list_content[6] = encryption.aesDecrypt(info.getAddress(),key);
		list_content[7] = encryption.aesDecrypt(info.getPhone(),key);
		list_content[8] = encryption.aesDecrypt(info.getAllergies(),key);
		list_content[9] = encryption.aesDecrypt(info.getMedicalhistory(),key);
		
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String,String>>();
		for(int i=0; i<10; i++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", list_titles[i]);
			map.put("content", list_content[i]);
			mylist.add(map);
		}
		
		SimpleAdapter myRecord = new SimpleAdapter(this, mylist, R.layout.listrecord,new String[] {"title", "content"},
				new int[]{R.id.txt_recordhint, R.id.txt_recordetails});
		
		listrecord.setAdapter(myRecord);
		
		
	}
	
	private OnClickListener butforeditListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// jump to edit record interface
			
			Intent myIntent = new Intent(getApplicationContext(), Activity_Login_recordsedit.class);
			//Intent myIntent = new Intent(getApplicationContext(), EditRecord_list.class);
			Bundle bundle = new Bundle();
			bundle.putInt("myID", id);
			bundle.putString("key", key);
			myIntent.putExtras(bundle);
			startActivity(myIntent);
		}
		
	};
	
	private OnClickListener butbackListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// jump to edit record interface
			
			/*Intent myIntent = new Intent(getApplicationContext(), Activity_Homepage.class);
			startActivity(myIntent);*/
			finish();
		}
		
	};
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
