package com.example.emr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
 class Activity_register_medical_main extends Activity{
	
private ExpandableListView expListView;
private ArrayList<String> listDataHeader;
private HashMap<String, List<String>> listDataChild;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_medical_list_view);
	
	expListView = (ExpandableListView) findViewById(R.id.expandableListView1);
	prepareListData();
	
	Activity_register_medical_expandablelist listAdapter = new Activity_register_medical_expandablelist(this, listDataHeader, listDataChild);
    
	// setting list adapter
    expListView.setAdapter(listAdapter);}

	
	private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding child data
        listDataHeader.add("Medications");
        listDataHeader.add("Allergics");
        listDataHeader.add("Diagnoses");
 
        // Adding child data
        List<String> Medications = new ArrayList<String>();
        Medications.add("1111");
        Medications.add("2222");
        
 
        List<String> Allergics = new ArrayList<String>();
        Allergics.add("1111");
        Allergics.add("2222");
        Allergics.add("3333");
        
 
        List<String> Diagnoses = new ArrayList<String>();
        Diagnoses.add("2 ");
        Diagnoses.add("12");

 
        listDataChild.put(listDataHeader.get(0), Medications); // Header, Child data
        listDataChild.put(listDataHeader.get(1), Allergics);
        listDataChild.put(listDataHeader.get(2), Diagnoses);
    }
}
