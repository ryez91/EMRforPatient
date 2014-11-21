package com.example.emr;

import java.io.UnsupportedEncodingException;
import java.security.*;

import com.example.elecmr.RegiInfo;
import com.example.elecmr.RegiInfoSource;
import com.example.trustworthy.encryption;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Page_setlog extends Activity{
	private EditText ID;
	private EditText password;
	private Button signin;
	private Button register; 
	
	private RegiInfoSource infosource;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logsign);
		
/*		
		Thread background = new Thread() {
            public void run() {
                 
                try {
                    // Thread will sleep for 5 seconds
                    sleep(2*1000);
                     
                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),Page_firstscreen.class);
                    startActivity(i);
                     
                    //Remove activity
                    finish();
                     
                } catch (Exception e) {
                 
                }
            }*/
       
        // start thread
     //   background.start();
        
        
        ID = (EditText)findViewById(R.id.ID);
        password = (EditText)findViewById(R.id.password_setting);
        signin = (Button)findViewById(R.id.signin);
        register = (Button)findViewById(R.id.register);
        
        infosource = new RegiInfoSource(this);
		infosource.open(); 
        
        signin.setOnClickListener(signinlistener);
        register.setOnClickListener(registerlistener);
        
	}
	
	 @Override
	    protected void onDestroy() {
	         
	        super.onDestroy();
	         
	    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private OnClickListener signinlistener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
		/*
			byte[] bytesOfMessage=null;
			try {
				bytesOfMessage = password.getText().toString().getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			MessageDigest md=null;
			try {
				md = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			byte[] thedigest = md.digest(bytesOfMessage);
			String decodedpass=null;
			try {
				decodedpass = new String(thedigest, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			String user=preferences.getString("USERNAME","");
			String pass=preferences.getString("PASSWORD","");
			if(!user.equals(ID.getText().toString())||!pass.equals(decodedpass)){
				Toast.makeText(Page_setlog.this,
						"UserID or Password wrong!", Toast.LENGTH_LONG).show();}
			*/
			
			String loginname;
			String loginpassword;
			loginname = ID.getText().toString();
			loginpassword = password.getText().toString();
			String encryptpw = encryption.MD5(encryption.MD5(loginpassword));
			String pwaskey = encryption.MD5("11111"+encryption.MD5(loginpassword)+"22222");
			
			//if the user is valid, jump to the homepage	
			RegiInfo user = infosource.searchsameNameRegiInfo(loginname);
				if(user == null){
					Toast.makeText(getApplicationContext(), "Username isn't available", Toast.LENGTH_LONG).show();
				}else{
					//if(user.getPassword().equals(loginpassword)){
					if(user.getPassword().equals(encryptpw)){
						Intent myIntent = new Intent(getApplicationContext(),Activity_Homepage.class);
						Bundle bundle = new Bundle();
						bundle.putString("username", loginname);
						bundle.putString("key", pwaskey);
						myIntent.putExtras(bundle);
						startActivity(myIntent);
					}
					else
						Toast.makeText(getApplicationContext(), "Password Wrong", Toast.LENGTH_SHORT).show();
				}
				
			//}
				

		}
	
	};
	
	private OnClickListener registerlistener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			Intent myIntent = new Intent(getApplicationContext(),Activity_register_first.class);
			Toast.makeText(Page_setlog.this,
					"register is clicked", Toast.LENGTH_LONG).show();
			startActivity(myIntent);
			
		}
	};
}
