package com.example.emr;

import com.example.elecmr.RegiInfo;
import com.example.elecmr.RegiInfoSource;
import com.example.trustworthy.encryption;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_register_general extends Activity{
	
	
	private EditText firstname;
	private EditText lastname;
	private EditText gender;
	private EditText DOB;
	private EditText SSN;
	private EditText insuranceID;
	private EditText address;
	private EditText phone;
	private EditText allergies;
	private EditText medicalhistory;
	private Button save_general;
	
	private RegiInfoSource infosource;
	
	private String myname, key;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		Log.d("MainActivity", "onCreat()");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_general);
		
		Bundle myBundle = this.getIntent().getExtras();
		myname = myBundle.getString("registername");
		key = myBundle.getString("key");
		firstname = (EditText)findViewById(R.id.etxt_firstname);
		lastname = (EditText)findViewById(R.id.etxt_lastname);
		gender =(EditText)findViewById(R.id.etxt_etxt_gender);
		DOB = (EditText)findViewById(R.id.etxt_DOB);
		SSN = (EditText)findViewById(R.id.etxt_SSN);
		
		insuranceID = (EditText)findViewById(R.id.etxt_insuranceID);
		address = (EditText)findViewById(R.id.etxt_address);
		phone = (EditText)findViewById(R.id.etxt_phone);
		allergies =(EditText)findViewById(R.id.etxt_allergies);
		medicalhistory =(EditText)findViewById(R.id.etxt_medhis);
		
		save_general = (Button)findViewById(R.id.save_general);
		
		save_general.setOnClickListener(save_generallinstener);
		
		infosource = new RegiInfoSource(this);
		infosource.open();
		
		RegiInfo mydata = infosource.searchsameNameRegiInfo(myname);
		id = mydata.getId();
	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	// click save, check all fields are finished, then update to the database
	 //go back to the main_menu
	private OnClickListener save_generallinstener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			String strFN = encryption.aesEncrypt(firstname.getText().toString(), key);
			String strLN = encryption.aesEncrypt(lastname.getText().toString(),key);
			String strGen = encryption.aesEncrypt(gender.getText().toString(),key);
			String strDOB = encryption.aesEncrypt(DOB.getText().toString(),key);
			String strSSN = encryption.aesEncrypt(SSN.getText().toString(),key);
			String strIID = encryption.aesEncrypt(insuranceID.getText().toString(),key);
			String strAdd = encryption.aesEncrypt(address.getText().toString(),key);
			String strPhone = encryption.aesEncrypt(phone.getText().toString(),key);
			String strAllergy = encryption.aesEncrypt(allergies.getText().toString(),key);
			String strMH = encryption.aesEncrypt(medicalhistory.getText().toString(),key);
			
			

			
			RegiInfo memberInfo = infosource.updateBasicInfo(id, strFN, strLN, strDOB, strSSN, strGen, strIID, strAdd, strPhone, strAllergy, strMH);
			Intent myIntent = new Intent(getApplicationContext(),Page_setlog.class);
			startActivity(myIntent);
			
		}
	};


}
