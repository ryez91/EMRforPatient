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

public class Activity_Login_passwordchange extends Activity{
	
	private EditText password_setting;
	private EditText password_confirm;
	private EditText password_origin;
	private Button save_changepw;
	
	private int myID;
	private RegiInfoSource infosource;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.password_change);
		
		Bundle myBundle = this.getIntent().getExtras();
		myID= myBundle.getInt("myid");
		
		
		password_origin = (EditText)findViewById(R.id.etxt_origpassword);
		password_setting = (EditText)findViewById(R.id.etxt_password1);
		password_confirm = (EditText)findViewById(R.id.etxt_password2);
		
		save_changepw=(Button)findViewById(R.id.btn_savepw);
		
		save_changepw.setOnClickListener(changepwListener);
		
        infosource = new RegiInfoSource(this);
		infosource.open(); 
		
	}
	
	private OnClickListener changepwListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// show your password is saving
			String passwordorig;
			String password1;
			String password2;
			
			passwordorig = password_origin.getText().toString();
			RegiInfo user = infosource.getRegiInfo(myID);
			
			String passwordindb = user.getPassword();	
			password1=password_setting.getText().toString();
			password2 = password_confirm.getText().toString();
			
			if(encryption.MD5(passwordorig).equals(passwordindb)){
				if(password1.length()>7&&password1.length()<21){
					if(password1.equals(password2)){
						
						String newpasswordencrypt = encryption.MD5(encryption.MD5(password1));
						infosource.updateRegiInfo(myID, newpasswordencrypt);
						Toast.makeText(getApplicationContext(), "Saving your password...", Toast.LENGTH_LONG).show();
					    finish();
					}
				}else{
					Toast.makeText(getApplicationContext(), "Please make sure you input the same password!", Toast.LENGTH_LONG).show();
				}
			}else{
				Toast.makeText(getApplicationContext(), "Your original password is wrong! ", Toast.LENGTH_LONG).show();
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
