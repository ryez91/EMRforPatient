package com.example.emr;

import android.app.Activity;

public class Functions extends Activity{
	
	public boolean nospace(String s){
		char[] c =s.toCharArray();
		for(int i =0; i< c.length;i++){
			char a = c[i];
			String aa = String.valueOf(a);
			if(aa.equals(" ")){
				return false;
			}
		}
		return true;
	}

}
