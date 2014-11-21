package com.example.emr;

import com.example.elecmr.RegiInfo;
import com.example.elecmr.RegiInfoSource;
import com.example.trustworthy.encryption;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_Login_recordsedit extends Activity{
	
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
	private Button save_edit;
	
	private int id;
	private String key;
	private RegiInfoSource infosource;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_existingrecord);
		
		Bundle myBundle = this.getIntent().getExtras();
		id = myBundle.getInt("myID");
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
		
		
		save_edit = (Button)findViewById(R.id.btn_savedit);
		
		infosource = new RegiInfoSource(this);
		infosource.open();
		
		
		RegiInfo userprofile = infosource.getBasicInfo(id);
		firstname.setText(encryption.aesDecrypt(userprofile.getFirstname(), key));
		lastname.setText(encryption.aesDecrypt(userprofile.getLastname(),key));
		gender.setText(encryption.aesDecrypt(userprofile.getGender(),key));
		DOB.setText( encryption.aesDecrypt(userprofile.getDob(),key));
		SSN.setText(encryption.aesDecrypt(userprofile.getSsn(),key));
		insuranceID.setText(encryption.aesDecrypt(userprofile.getInid(),key));
		address.setText(encryption.aesDecrypt(userprofile.getAddress(),key));
		phone.setText(encryption.aesDecrypt(userprofile.getPhone(),key));
		allergies.setText(encryption.aesDecrypt(userprofile.getAllergies(),key));
		medicalhistory.setText(encryption.aesDecrypt(userprofile.getMedicalhistory(),key));
		
		
		save_edit.setOnClickListener(save_editinfo);
		
	}
	
	private OnClickListener save_editinfo= new OnClickListener(){

		@Override
		public void onClick(View v) {
			//update database
			//toast "Saving.."
			// once finish edit, jump to Activity_Login_recordshow.class
			/*
			Intent myIntent = new Intent(getApplicationContext(), Activity_Login_recordshow.class);
			startActivity(myIntent);
			finish();
			*/
			String FNedit= firstname.getText().toString();
			String LNedit = lastname.getText().toString();
			String GENDERedit = gender.getText().toString();
			String DOBedit =DOB.getText().toString();
			String SSNedit = SSN.getText().toString();
			String IIDedit = insuranceID.getText().toString();
			String ADDedit = address.getText().toString();
			String PHONEedit =phone.getText().toString();
			String ALLERGIESedit = allergies.getText().toString();
			String MHedit = medicalhistory.getText().toString();
			
			//if name contains " "
			if(DOBedit.length()==10){
				if(SSNedit.length()==9){
					if(GENDERedit.length()==6 ||GENDERedit.length()==4){
					
						if(IIDedit.length()==9){
							if(PHONEedit.length()==10){
								FNedit = encryption.aesEncrypt(FNedit, key);
								LNedit = encryption.aesEncrypt(LNedit, key);
								GENDERedit = encryption.aesEncrypt(GENDERedit, key);
								DOBedit =encryption.aesEncrypt(DOBedit, key);
								SSNedit = encryption.aesEncrypt(SSNedit, key);
								IIDedit = encryption.aesEncrypt(IIDedit, key);
								ADDedit = encryption.aesEncrypt(ADDedit, key);
								PHONEedit = encryption.aesEncrypt(PHONEedit, key);
								ALLERGIESedit = encryption.aesEncrypt(ALLERGIESedit, key);
								MHedit = encryption.aesEncrypt(MHedit, key);
								RegiInfo myeditedInfo = infosource.updateBasicInfo(id, FNedit, LNedit, DOBedit, SSNedit, GENDERedit, IIDedit, ADDedit, PHONEedit, ALLERGIESedit, MHedit);
								Toast.makeText(getApplicationContext(), "Updating...", Toast.LENGTH_LONG).show();
								finish();
							}else{
								Toast.makeText(getApplicationContext(), "Your phone number is invalid!", Toast.LENGTH_LONG).show();
							}
							
						}else{
							Toast.makeText(getApplicationContext(), "Your insurance ID is invalid!", Toast.LENGTH_LONG).show();
						}
						
						
					}else{Toast.makeText(getApplicationContext(), "Gender input is in valid", Toast.LENGTH_LONG).show();}
				
				}else{Toast.makeText(getApplicationContext(), "Your SSN is in valid", Toast.LENGTH_LONG).show();}
				
			}else{
				Toast.makeText(getApplicationContext(), "Your DOB input is invalid!", Toast.LENGTH_LONG).show();
			}
			
			
		}
		
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
		
	}

}
