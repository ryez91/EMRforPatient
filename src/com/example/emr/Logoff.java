package com.example.emr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Logoff extends Activity{
	
	Button btnlogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logoff);
		btnlogin = (Button)findViewById(R.id.btn_login);
		btnlogin.setOnClickListener(loginListener);
	}
	
	private OnClickListener loginListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent myIntent = new Intent(getApplicationContext(),Page_setlog.class);
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
