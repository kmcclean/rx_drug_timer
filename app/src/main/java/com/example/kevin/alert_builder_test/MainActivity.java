package com.example.kevin.alert_builder_test;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.ListActivity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends ListActivity {

    FragmentManager fm;
    DatabaseManager databaseManager;
    android.support.design.widget.FloatingActionButton fab;
    ListView lv;
    CustomListAdapter cla;
    ArrayList<Pill> savedPills;
    AlarmManager mAlarmManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        databaseManager = new DatabaseManager(this);
        fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        savedPills = databaseManager.fetchAll();
        cla = new CustomListAdapter(this, savedPills);
        lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(cla);

        //This makes sure that all the alarms are reset for your pills when you turn the program back on.
        for(Pill p : savedPills){
            createAlarm(p);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getFragmentManager();
                PillDialogNew pd = new PillDialogNew();
                pd.setTargetFragment(pd, 1);
                pd.show(fm, "TAG");
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pill p = savedPills.get(position);
                fm = getFragmentManager();
                PillDialogExists pdn = new PillDialogExists();
                Bundle b = new Bundle();
                b.putParcelable("pill", p);
                pdn.setArguments(b);
                pdn.setTargetFragment(pdn, 2);
                pdn.show(fm, "TAG1");
            }
        });
    }


    //this method adds a new pill to the system, both as an object and in the database.
    public boolean addNewPill(Long pillID, String pillName, String pharmacy, long pharmNum, String doctor, long doctorNum, int hour, int minute,  int pillCount, int interval, String info){

        long millis = turnTimeIntoMillis(hour, minute);
        if(databaseManager.addRow(pillID, pillName, pharmacy, pharmNum, doctor, doctorNum, millis, pillCount, interval, info)){
            Pill p = new Pill(pillID, pillName, pharmacy, pharmNum, doctor, doctorNum, millis, pillCount, interval, info);
            if(createAlarm(p)){
                savedPills.add(p);
                updateAdapter();
                Toast.makeText(MainActivity.this, "New pill alarm has been set.", Toast.LENGTH_SHORT).show();
                return true;
            }

            //if the update fails, this is to make sure that it isn't in the database without being turned into an alarm and added to the adapter. All or nothing.
            else{
                databaseManager.deleteRow(p.getPillID());
                Toast.makeText(MainActivity.this, "There was an error creating the alarm.", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        else{
            Toast.makeText(MainActivity.this, "There was an error adding the alarm to the database.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    //deletes the pill from the form screen.
    public void deletePill(Long pillID){
        if(databaseManager.deleteRow(pillID)) {
            int i = -1;
            for (Pill pill : savedPills) {
                if (pill.getPillID().equals(pillID)) {
                    i = savedPills.indexOf(pill);
                }
            }
            try {
                if(cancelAlarm(savedPills.get(i))) {
                    savedPills.remove(i);
                    updateAdapter();
                    Toast.makeText(MainActivity.this, "Pill alarm has been deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to delete pill alarm.", Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e){
                Toast.makeText(MainActivity.this, "Failed to delete pill alarm.", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(MainActivity.this, "There was an error deleting the alarm", Toast.LENGTH_SHORT).show();
        }
    }


    //when the user wants to edit this pill selection, this is method takes care of it.
    public void updatePill(Long pillID, String pillName, String pharmacy, long pharmNum, String doctor, long doctorNum, int hour, int minute, int pillCount, int interval, String info){

        long millis = turnTimeIntoMillis(hour, minute);
        if(databaseManager.updateRow(pillID, pillName, pharmacy, pharmNum, doctor, doctorNum, millis, pillCount, interval, info)){
            for (Pill pill: savedPills){
                if(pill.getPillID().equals(pillID)){
                    pill.setPillName(pillName);
                    pill.setPharmacyName(pharmacy);
                    pill.setPharmacyNo(pharmNum);
                    pill.setDoctorName(doctor);
                    pill.setDoctorNo(doctorNum);
                    pill.setIntervalLength(interval);
                    pill.setNextTimeInMillis(millis);
                    pill.setPillCount(pillCount);
                    pill.setInformation(info);
                    cancelAlarm(pill);
                    createAlarm(pill);
                }
            }
            updateAdapter();
            Toast.makeText(MainActivity.this, "Pill alarm updated.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Error updating pill alarm.", Toast.LENGTH_SHORT).show();
        }
    }


    //This creates the alarm that will actually show up.
    public boolean createAlarm(Pill p){
        try {

            Intent alarmIntent = new Intent(MainActivity.this, PillAlarmReceiver.class);

            Integer intID = createID(p);

            Bundle b = new Bundle();
            b.putParcelable("pill", p);
            alarmIntent.putExtras(b);
            b.putInt("notification_id", intID);

            //wraps the intent inside a pending intent, and sets it to be ready to use.
            PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, intID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, p.getNextTimeInMillis(), pi);

            Toast.makeText(MainActivity.this, "Alarm Set.", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    //this cancels the notification, so it doesn't appear after it has been deleted from the database.
    public boolean cancelAlarm(Pill p){
        try {
            Intent alarmIntent = new Intent(MainActivity.this, PillAlarmReceiver.class);

            Integer intID = createID(p);

            Bundle b = new Bundle();
            b.putParcelable("pill", p);
            alarmIntent.putExtras(b);
            b.putInt("notification_id", intID);

            //wraps the intent inside a pending intent, and sets it to be ready to use.
            PendingIntent pi = PendingIntent.getBroadcast(MainActivity.this, intID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.cancel(pi);

            Toast.makeText(MainActivity.this, "Alarm Set.", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }


    //For this id, I am building a unique Id for each of the individual pendingIntents & their corresponding notifications....
    //...With pending intents, they overwrite the existing PendingIntent if unless their is something that differentiates them...
    //...like the request code. So for mine, I have the request code linked to the id of the drug. This way, if the drug is ever...
    //...changed, then the intent should update as well. However, the ids are too long to work as ints. So instead I am parsing out...
    //...the middle nine digits (cutting off the last three and the first). This means that there will be a unique number of every second...
    //...for the next 31 years. I can only hope someone uses this program for that long.
    public int createID(Pill p){
        String s = p.getPillID().toString();
        String[] stringList = s.split("");
        String stringID = "";
        for(int i = 2; i < 11; i++){
            stringID = stringID + stringList[i];
        }
        Integer intID = Integer.parseInt(stringID);

        return intID;
    }

    //this runs the conversion into milliseconds, which is what run alarms.
    public long turnTimeIntoMillis(int hour, int minute){

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, minute);

        long l = c.getTimeInMillis();

        //since there is no day on the clock, this adds a days worth of milliSeconds to the time the current millis is behind the clock time.
        while(System.currentTimeMillis() > l){
            l = l + 86400000;
        }

        return l;
    }


    //used to close the database.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseManager.close();
    }

    //makes sure the dataset has been updated properly.
    public void updateAdapter(){
        cla.notifyDataSetChanged();
    }
}