package com.example.emr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Page_firstscreen extends Activity{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstscreen); 
        
        new Handler().postDelayed(new Runnable() {
        	private int SPLASH_TIME_OUT = 1500;
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
 
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(Page_firstscreen.this, Page_setlog.class);
                startActivity(i);
 
                // close this activity
                finish();
            }
        }, 1500);
    }
       

     
    @Override
    protected void onDestroy() {
         
        super.onDestroy();
         
    }
	

}
