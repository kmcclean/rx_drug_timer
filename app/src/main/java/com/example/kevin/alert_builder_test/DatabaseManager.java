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

    protected static final String pillIDCol = "pillID";
    protected static final String pillNaCol = "pillName";
    protected static final String pharmNaCol="pharmacyName";
    protected static final String pharmNumCol = "pharmacyNo";
    protected static final String doctorNaCol = "doctorName";
    protected static final String doctorNumCol = "doctorNo";
    protected static final String nextPillHourCol = "nextPillHour";
    protected static final String nextPillMinuteCol = "nextPillMinute";
    protected static final String intervalCol = "interval";
    protected static final String pillCountCol = "pillCount";
    protected static final String infoCol = "info";

    private static final String DBTAG = "DatabaseManager";
    private static final String SQLTAG = "SQLHelper";

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
        helper.onCreate(db);
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
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            String createTable = "CREATE TABLE " + DB_TABLE + " (" + pillIDCol + " INTEGER, " + pillNaCol + " TEXT, " + pharmNaCol + " TEXT, " +
                    pharmNumCol + " INTEGER, " + doctorNaCol + " TEXT, " + doctorNumCol + " INTEGER, " + nextPillHourCol + " INTEGER, " + nextPillMinuteCol + " INTEGER, "
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
    public ArrayList<Pill> fetchAll() {
        String cols[] = {pillIDCol, pillNaCol, pharmNaCol, pharmNumCol, doctorNaCol, doctorNumCol, nextPillHourCol, nextPillMinuteCol, intervalCol, pillCountCol, infoCol};
        Cursor cursor = db.query(DB_TABLE, cols, null, null, null, null, pillNaCol);
        cursor.moveToFirst();
        ArrayList<Pill> pillArrayList = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            //String id = cursor.getString(0);
            //Long idLong = Long.parseLong(id);
            Pill p = new Pill(cursor.getLong(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getLong(3),
                    cursor.getString(4),
                    cursor.getLong(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getInt(8),
                    cursor.getInt(9),
                    cursor.getString(10));
            pillArrayList.add(p);
            cursor.moveToNext();
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return pillArrayList;
    }

    public boolean updateRow(Long pillID, String pillName, String pharmacy, long pharmNum, String doctor, long doctorNum, int hour, int minute, int interval, int pillCount, String info) {
        ContentValues updatePill = new ContentValues();
        updatePill.put(pillNaCol, pillName);
        updatePill.put(pharmNaCol, pharmacy);
        updatePill.put(pharmNumCol, pharmNum);
        updatePill.put(doctorNaCol, doctor);
        updatePill.put(doctorNumCol, doctorNum);
        updatePill.put(nextPillHourCol, hour);
        updatePill.put(nextPillMinuteCol, minute);
        updatePill.put(intervalCol, interval);
        updatePill.put(pillCountCol, pillCount);
        updatePill.put(infoCol, info);
        try {
            db.update(DB_TABLE, updatePill, pillID.toString(), null);
            return true;
        } catch (Exception e) {
            Log.e(DBTAG, "Error updating table into table", e);
            return false;
        }
    }

    //adds a new alarm to the system.
    public boolean addRow(Long pillID, String pillName, String pharmacy, long pharmNum, String doctor, long doctorNum, int hour, int minute, int interval, int pillCount, String info) {
        ContentValues newPill = new ContentValues();
        newPill.put(pillIDCol, pillID);
        newPill.put(pillNaCol, pillName);
        newPill.put(pharmNaCol, pharmacy);
        newPill.put(pharmNumCol, pharmNum);
        newPill.put(doctorNaCol, doctor);
        newPill.put(doctorNumCol, doctorNum);
        newPill.put(nextPillHourCol, hour);
        newPill.put(nextPillMinuteCol, minute);
        newPill.put(intervalCol, interval);
        newPill.put(pillCountCol, pillCount);
        newPill.put(infoCol, info);
        try {
            db.insertOrThrow(DB_TABLE, null, newPill);
            return true;
        } catch (Exception e) {
            Log.e(DBTAG, "Error inserting new data into table", e);
            return false;
        }
    }

    public boolean deleteRow(Long pillID){
        try {
            //String s = ("DELETE FROM " + DB_TABLE + " WHERE " + pillIDCol + " = " + pillID);
            //db.execSQL(s);
            db.delete(DB_TABLE, pillIDCol + " = " + pillID, null);
            return true;
        }
        catch (Exception e){
            Log.e(DBTAG, "Error deleting row");
            return false;
        }
    }
}