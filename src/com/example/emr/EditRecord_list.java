package com.example.emr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.elecmr.RegiInfo;
import com.example.elecmr.RegiInfoSource;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class EditRecord_list extends Activity{
	
	ListView editrecordaslist;
	Button buttonsave;
	TextView texthome;
	private int id;
	
	private RegiInfoSource infosource;
	private final String[] list_titles = {"First Name", "Last Name", "Date of Birth", "Gender", "SSN",
			"Insurance ID", "Address", "Phone", "Allergies", "Medical History"};
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listeditmyrecord);
		
		Bundle myBundle = this.getIntent().getExtras();
		id = myBundle.getInt("myID");
		
		buttonsave =(Button)findViewById(R.id.btn_edrecords);
		editrecordaslist =(ListView)findViewById(R.id.list_editmyrecordnew);
		
		infosource = new RegiInfoSource(this);
		infosource.open(); 
		
		
		List<RegiInfo> information = infosource.getBasicInfoaslist(id);
		RegiInfo info = information.get(0);
		
		String[] edit_list_content = new String[10];
		edit_list_content[0] = info.getFirstname();
		edit_list_content[1] = info.getLastname();
		edit_list_content[2] = info.getDob();
		edit_list_content[3] = info.getGender();
		edit_list_content[4] = info.getSsn();
		edit_list_content[5] = info.getInid();
		edit_list_content[6] = info.getAddress();
		edit_list_content[7] = info.getPhone();
		edit_list_content[8] = info.getAllergies();
		edit_list_content[9] = info.getMedicalhistory();
		
		ArrayList<HashMap<String, String>> mylist = new ArrayList<HashMap<String,String>>();
		for(int i=0; i<10; i++){
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("title", list_titles[i]);
			map.put("content", edit_list_content[i]);
			mylist.add(map);
		}
		
		SimpleAdapter myRecord = new SimpleAdapter(this, mylist, R.layout.list_rowelement,new String[] {"title", "content"},
				new int[]{R.id.txt_editlist_title, R.id.txt_editmyrecordnew});
		
		editrecordaslist.setAdapter(myRecord);
		
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
		

}
