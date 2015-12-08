package com.example.kevin.alert_builder_test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager {


    private Context context;
    private SQLHelper helper;
    private SQLiteDatabase db;

    protected static final String DB_NAME = "testDatabase";
    protected static final int DB_VERSION = 1;
    protected static final String DB_TABLE = "testTable";

    protected static final String pillNaCol = "pillName";
    protected static final String pharmNaCol="pharmacyName";
    protected static final String pharmNumCol = "pharmacyNo";
    protected static final String doctorNaCol = "doctorName";
    protected static final String doctorNumCol = "doctorNo";
    protected static final String nextPillTimeCol = "nextPillTime";
    protected static final String intervalCol = "interval";
    protected static final String pillCountCol = "pillCount";
    protected static final String infoCol = "info";

    private static final String DBTAG = "DatabaseManager";
    private static final String SQLTAG = "SQLHelper";

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
        //helper.onCreate(db);
    }

    public void close() {
        helper.close(); //Closes the database - very important!
    }

    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper(Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        //Creates a new database to use.
        @Override
        public void onCreate(SQLiteDatabase db) {
           // db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            String createTable = "CREATE TABLE " + DB_TABLE + " (" + pillNaCol + " TEXT, " + pharmNaCol + " TEXT, " +
                    pharmNumCol + " INTEGER, " + doctorNaCol + " TEXT, " + doctorNumCol + " INTEGER, " + nextPillTimeCol + " STRING, "
                    + intervalCol + " INTEGER, " + pillCountCol + " INTEGER, " + infoCol + " TEXT);";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
            Log.w(SQLTAG, "Upgrade table - drop and recreate it");
        }
    }


    //returns all of the alarms thave have been created.
    public String fetchAllContacts() {
        String cols[] = {pillNaCol, pharmNaCol, pharmNumCol, doctorNaCol, doctorNumCol, nextPillTimeCol, intervalCol, pillCountCol, infoCol};
        Cursor cursor = db.query(DB_TABLE, cols, null, null, null, null, pillNaCol);
        cursor.moveToFirst();
        String tableRows = "";
        while (!cursor.isAfterLast()) {
            tableRows = tableRows + "pillName: " + cursor.getString(0) + " pharmacyName: " + cursor.getString(1) +
                    "pharmacyNo:" + cursor.getLong(2) + "doctorName: " + cursor.getString(3) + "doctorNo: " + cursor.getLong(4) +
                    "nextPillTime: " + cursor.getString(5) + "interval:" + cursor.getInt(6)+ "pillCount: " + cursor.getInt(7) +
                    "info: " + cursor.getString(8) + "\n";
            cursor.moveToNext();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return tableRows;
    }

    public ArrayList<ArrayList<String>> fetchDisplay(){
        String cols[] = {pillNaCol, nextPillTimeCol, infoCol};
        ArrayList<ArrayList<String>> displayList = new ArrayList();
        Cursor cursor = db.query(DB_TABLE, cols, null, null, null, null, nextPillTimeCol);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            ArrayList<String> pillDisplay = new ArrayList<>();
            pillDisplay.add(0, cursor.getString(0));
            pillDisplay.add(1, cursor.getString(1));
            pillDisplay.add(2, cursor.getString(2));
            displayList.add(pillDisplay);

            cursor.moveToNext();
        }
        if(!cursor.isClosed()){
            cursor.close();
        }
        return displayList;
    }

    //adds a new alarm to the system.
    public boolean addRow(String pillName, String pharmacy, long pharmNum, String doctor, long doctorNum, String time, int interval, int pillCount, String info) {
        ContentValues newContact = new ContentValues();
        newContact.put(pillNaCol, pillName);
        newContact.put(pharmNaCol, pharmacy);
        newContact.put(pharmNumCol, pharmNum);
        newContact.put(doctorNaCol, doctor);
        newContact.put(doctorNumCol, doctorNum);
        newContact.put(nextPillTimeCol, time);
        newContact.put(intervalCol, interval);
        newContact.put(pillCountCol, pillCount);
        newContact.put(infoCol, info);
        try {
            db.insertOrThrow(DB_TABLE, null, newContact);
            return true;
        } catch (Exception e) {
            Log.e(DBTAG, "Error inserting new data into table", e);
            return false;
        }
    }
/*
    public String getPhonesForName(String name) {
        String cols[] = {phoneCol};
        String whereClause = nameCol + "=" + name + "'";
        Cursor cursor = db.query(DB_TABLE, cols, whereClause, null, null, null, null);
        cursor.moveToFirst();
        String phoneNumbers = "";
        while (!cursor.isLast()) {
            phoneNumbers = phoneNumbers + cursor.getLong(0) + "\n";
            cursor.moveToNext();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return phoneNumbers;
    }*/
}