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
import android.widget.TextView;
import android.widget.Toast;

public class Activity_register_first extends Activity{
	
	private EditText emailID;
	private EditText password_setting;
	private EditText password_confirm;
	private Button btnsignup;
	private Button btncancel;
	
	private RegiInfoSource infosource;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_first);
		
		emailID = (EditText)findViewById(R.id.etxt_email);
		password_setting = (EditText)findViewById(R.id.etxt_password1);
		password_confirm = (EditText)findViewById(R.id.etxt_password2);
		
		btnsignup=(Button)findViewById(R.id.btn_signup);
		btncancel = (Button)findViewById(R.id.btn_cancel);
		
		
		btnsignup.setOnClickListener(next1listener);
		btncancel.setOnClickListener(cancelListener);
		
		infosource = new RegiInfoSource(this);
		infosource.open();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private OnClickListener next1listener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			String emailname = emailID.getText().toString();
			String password1 = password_setting.getText().toString();
			String password2 = password_confirm.getText().toString();
			
			
			if(emailname.length()>5){
				RegiInfo user =infosource.searchsameNameRegiInfo(emailname);
				if(user ==null){
					// username space?
					if(password1.length()>7 &&password1.length()<21){
						if(password1.equals(password2)){
							//MD5 encryption
							String passwordencrypt_s = encryption.MD5(encryption.MD5(password1));
							
							String pwaskey = encryption.MD5("11111"+encryption.MD5(password1)+"22222");
					
							
							RegiInfo memberInfo = infosource.createRegiInfo(emailname, passwordencrypt_s); //password1 should be changed to MD5 value
							Intent myIntent = new Intent(getApplicationContext(),Activity_register_general.class);
							Bundle bundle = new Bundle();
							bundle.putString("registername", emailname);
							bundle.putString("key", pwaskey);
							myIntent.putExtras(bundle);
							startActivity(myIntent);
						}
						
					}else{
						Toast.makeText(getApplicationContext(), "Your password length should bewteen 8 to 20. ", Toast.LENGTH_LONG).show();
					}
				}else{
					Toast.makeText(getApplicationContext(), "Username exists!", Toast.LENGTH_LONG).show();
				}
				
			}else{
				Toast.makeText(getApplicationContext(), "Your username length should be >5", Toast.LENGTH_LONG).show();
			}
			

		}
	};
	
	private OnClickListener cancelListener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			
			Intent myIntent = new Intent(getApplicationContext(),Page_setlog.class);
			startActivity(myIntent);
			
		}
	};
	


}

