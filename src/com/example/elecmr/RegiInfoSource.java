package com.example.elecmr;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RegiInfoSource {
	
	private SQLiteDatabase db;
	private InfoSQLiteHelper dbHelper;
	
	private String[] allColumns = {InfoSQLiteHelper.COLUMN_ID, InfoSQLiteHelper.COLUMN_USERNAME,InfoSQLiteHelper.COLUMN_PASSWORD, InfoSQLiteHelper.COLUMN_FIRSTNAME, InfoSQLiteHelper.COLUMN_LASTNAME, InfoSQLiteHelper.COLUMN_DOB, InfoSQLiteHelper.COLUMN_DOB, InfoSQLiteHelper.COLUMN_SSN,
			InfoSQLiteHelper.COLUMN_GENDER, InfoSQLiteHelper.COLUMN_IID, InfoSQLiteHelper.COLUMN_ADDRESS, InfoSQLiteHelper.COLUMN_PHONE, InfoSQLiteHelper.COLUMN_ALLERGIES, InfoSQLiteHelper.COLUMN_MEDICALHIS};
	
	private String[] regiColumns = {InfoSQLiteHelper.COLUMN_ID, InfoSQLiteHelper.COLUMN_USERNAME, InfoSQLiteHelper.COLUMN_PASSWORD};

	private String[] basicInfoColumns= {InfoSQLiteHelper.COLUMN_FIRSTNAME, InfoSQLiteHelper.COLUMN_LASTNAME, InfoSQLiteHelper.COLUMN_DOB, InfoSQLiteHelper.COLUMN_DOB, InfoSQLiteHelper.COLUMN_SSN, InfoSQLiteHelper.COLUMN_GENDER, InfoSQLiteHelper.COLUMN_IID, InfoSQLiteHelper.COLUMN_ADDRESS, 
			InfoSQLiteHelper.COLUMN_PHONE, InfoSQLiteHelper.COLUMN_ALLERGIES, InfoSQLiteHelper.COLUMN_MEDICALHIS};
	
	
	public RegiInfoSource(Context context){
		dbHelper = new InfoSQLiteHelper(context);
		
	}
	
	public void open() throws SQLException{
		db = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	public RegiInfo createRegiInfo(String name, String password){
		ContentValues values = new ContentValues();
		values.put(InfoSQLiteHelper.COLUMN_USERNAME, name);
		values.put(InfoSQLiteHelper.COLUMN_PASSWORD, password);
		int insertId =(int) db.insert(InfoSQLiteHelper.TABLE_REGI, null, values);
		Cursor cursor = db.query(InfoSQLiteHelper.TABLE_REGI, allColumns, InfoSQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
	
		cursor.moveToFirst();
		RegiInfo newMemberInfo = cursorToRegiInfo(cursor);
		cursor.close();
		return newMemberInfo;
	}
	
	public RegiInfo getRegiInfo(int id){
		Cursor cursor = db.query(InfoSQLiteHelper.TABLE_REGI, regiColumns, InfoSQLiteHelper.COLUMN_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		RegiInfo newMemberInfo = cursorToRegiInfo(cursor);
		cursor.close();
		return newMemberInfo;
	}
	
	public RegiInfo getRegiInfo(String username){
		Cursor cursor = db.query(InfoSQLiteHelper.TABLE_REGI, regiColumns, InfoSQLiteHelper.COLUMN_USERNAME + " = " + username, null, null, null, null);
		cursor.moveToFirst();
		
			RegiInfo newMemberInfo = cursorToRegiInfo(cursor);
			cursor.close();
			return newMemberInfo;

		
	}
	
	public RegiInfo updateRegiInfo(int id, String password){
		ContentValues values = new ContentValues();
		values.put(InfoSQLiteHelper.COLUMN_PASSWORD, password);
		

		db.update(InfoSQLiteHelper.TABLE_REGI, values, InfoSQLiteHelper.COLUMN_ID + "=" + id, null);
		
		return null;
	}
	
	public void deleteRegiInfo(int id){
		System.out.println("Info deleted with id: " + id);
		db.delete(InfoSQLiteHelper.TABLE_REGI, InfoSQLiteHelper.COLUMN_ID + "=" + id, null);
		
	}
	
	
	//search the username
	public RegiInfo searchsameNameRegiInfo(String username){
		//Cursor cursor = db.rawQuery("SELECT * FROM " + InfoSQLiteHelper.TABLE_REGI + " WHERE " + InfoSQLiteHelper.COLUMN_USERNAME + " ='" + username +"'", null);
		
		Cursor cursor = db.query(InfoSQLiteHelper.TABLE_REGI, regiColumns, InfoSQLiteHelper.COLUMN_USERNAME + " = '" + username + "'", null, null, null, null);
		cursor.moveToFirst();
		
		if(cursor.getCount() == 0){
			cursor.close();
			return null;
			// search the same username
		}else{
			RegiInfo basicinfo = cursorToRegiInfo(cursor);
			cursor.close();
			return basicinfo;
		}
		
	
		
	}
	
	//get the username and password, return in memberInfo
	private RegiInfo cursorToRegiInfo(Cursor cursor){
		RegiInfo memberInfo= new RegiInfo();
		memberInfo.setId(cursor.getInt(0));
		memberInfo.setUsername(cursor.getString(1));
		memberInfo.setPassword(cursor.getString(2));
		return memberInfo;
		
	}
	
	//finish the records
	
	public RegiInfo updateBasicInfo(int id, String firstname, String lastname, String dob, String ssn, 
			String gender, String insuranceid, String address, String phone, String allergies, String medicalhistory){
		
		ContentValues values = new ContentValues();
		values.put(InfoSQLiteHelper.COLUMN_FIRSTNAME, firstname);
		values.put(InfoSQLiteHelper.COLUMN_LASTNAME, lastname);
		values.put(InfoSQLiteHelper.COLUMN_DOB, dob);
		values.put(InfoSQLiteHelper.COLUMN_SSN, ssn);
		values.put(InfoSQLiteHelper.COLUMN_GENDER, gender);
		values.put(InfoSQLiteHelper.COLUMN_IID, insuranceid);
		values.put(InfoSQLiteHelper.COLUMN_ADDRESS, address);
		values.put(InfoSQLiteHelper.COLUMN_PHONE, phone);
		values.put(InfoSQLiteHelper.COLUMN_ALLERGIES, allergies);
		values.put(InfoSQLiteHelper.COLUMN_MEDICALHIS, medicalhistory);
		
		//find ID, update
		
		/*
		 *  int id=sqdb.update("table_name",values,"bookid=? and booktype=?",array); where array contains 5 and comic
		 */
		db.update(InfoSQLiteHelper.TABLE_REGI, values, InfoSQLiteHelper.COLUMN_ID + "=" + id, null);
		
		return null;
	}
	
	public RegiInfo getBasicInfo(int id){
		
		Cursor cursor = db.query(InfoSQLiteHelper.TABLE_REGI, basicInfoColumns, InfoSQLiteHelper.COLUMN_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		RegiInfo newBasicInfo = cursorToBasicInfo(cursor);
		cursor.close();
		return newBasicInfo;
	}

	private RegiInfo cursorToBasicInfo(Cursor cursor) {
		// TODO Auto-generated method stub
		RegiInfo basicInfo= new RegiInfo();
		//basicInfo.setId(cursor.getInt(0));
		basicInfo.setFirstname(cursor.getString(0));
		basicInfo.setLastname(cursor.getString(1));
		basicInfo.setDob(cursor.getString(2));
		basicInfo.setSsn(cursor.getString(4));
		basicInfo.setGender(cursor.getString(5));
		basicInfo.setInid(cursor.getString(6));
		basicInfo.setAddress(cursor.getString(7));
		basicInfo.setPhone(cursor.getString(8));
		basicInfo.setAllergies(cursor.getString(9));
		basicInfo.setMedicalhistory(cursor.getString(10));
		
		return basicInfo;
		
	}
	
	//listView to get basic information
	public List<RegiInfo> getBasicInfoaslist(int id){
		List<RegiInfo> regi_info = new ArrayList<RegiInfo>();
		
		Cursor cursor = db.query(InfoSQLiteHelper.TABLE_REGI, basicInfoColumns, InfoSQLiteHelper.COLUMN_ID + " = " + id, null, null, null, null);
		cursor.moveToFirst();
		
		while(!cursor.isAfterLast()){
			RegiInfo basicinfo = cursorToBasicInfo(cursor);
			regi_info.add(basicinfo);
			cursor.moveToNext();
		}
		cursor.close();
		return regi_info;
	}
}
