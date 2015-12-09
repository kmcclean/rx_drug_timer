package com.example.kevin.alert_builder_test;

import android.app.FragmentManager;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    FragmentManager fm;
    DatabaseManager databaseManager;
    android.support.design.widget.FloatingActionButton fab;
    ListView lv;
    CustomListAdapter cla;
    ArrayList<Pill> savedPills;

     //TODO Learn: Create AlertDialog.Builder.
   //TODO Learn: How to set alarms.
    //TODO 1) COMPLETED - Create the AlertDialog.
    //TODO 1b) COMPLETED - Have the data be properly pulled from the alert dialogs.
    //TODO 2) COMPLETED - Set the database so that the alert can be saved.
    //TODO 2a) COMPLETED - Create the database.
    //TODO 2b) COMPLETED - Have the alarm added into the database.
    //TODO 3) COMPLETED - Retrieve the information from the database.
    //TODO 3a) COMPLETED - Show the pills in a list at the start screen
    //TODO 3a) When the program starts, have it display the information from the database.
    //TODO 4) Show the alarms.
    //TODO 4a) Set the information into a custom display layout, have the layout display the information.
    //TODO 5) Enable edit the alarm settings.
    //TODO 6) Have the alarms go off.
    //TODO 7) Input validation.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseManager = new DatabaseManager(this);
        fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        savedPills = databaseManager.fetchAll();
        cla = new CustomListAdapter(this, savedPills);
        lv = (ListView) findViewById(android.R.id.list);
        lv.setAdapter(cla);
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
                b.putString("name",p.getPillName());
                b.putString("pharmacy", p.getPharmacyName());
                b.putLong("pharmNo", p.getPharmacyNo());
                b.putString("docName", p.getDoctorName());
                b.putLong("docNo", p.getDoctorNo());
                b.putString("nextTime", p.getNextPillTime());
                b.putInt("count", p.getPillCount());
                b.putInt("length", p.getIntervalLength());
                b.putLong("id", p.getPillID());
                b.putString("info", p.getInformation());
                pdn.setArguments(b);
                pdn.setTargetFragment(pdn, 2);
                pdn.show(fm, "TAG1");
            }
        });
    }

    //this method exists at the moment to test out the database and make sure everything is working properly.
    public boolean addNewPill(Long pillID, String pillName, String pharmacy, long pharmNum, String doctor, long doctorNum, String time, int interval, int pillCount, String info){
        if(databaseManager.addRow(pillID, pillName, pharmacy, pharmNum, doctor, doctorNum, time, interval, pillCount, info)){
            Pill p = new Pill(pillID, pillName, pharmacy, pharmNum, doctor, doctorNum, time, interval, pillCount, info);
            savedPills.add(p);
            Toast.makeText(MainActivity.this, "New pill alarm has been set.", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(MainActivity.this, "There was an error creating the alarm.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void deletePill(Long pillID){

        if(databaseManager.deleteRow(pillID)) {
            for (Pill pill : savedPills) {
                if (pillID == pill.getPillID()) {
                    savedPills.remove(pill);
                }
            }
            Toast.makeText(MainActivity.this, "Pill alarm has been deleted", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "There was an error deleting the alarm", Toast.LENGTH_SHORT).show();
        }
    }

    public void updatePill(Long pillID, String pillName, String pharmacy, long pharmNum, String doctor, long doctorNum, String time, int interval, int pillCount, String info){
        if(databaseManager.updateRow(pillID, pillName, pharmacy, pharmNum, doctor, doctorNum, time, interval, pillCount, info)){
            for (Pill pill: savedPills){
                if(pillID == pill.getPillID()){
                    pill.setPillName(pillName);
                    pill.setPharmacyName(pharmacy);
                    pill.setPharmacyNo(pharmNum);
                    pill.setDoctorName(doctor);
                    pill.setDoctorNo(doctorNum);
                    pill.setIntervalLength(interval);
                    pill.setNextPillTime(time);
                    pill.setPillCount(pillCount);
                    pill.setInformation(info);
                }
            }
            Toast.makeText(MainActivity.this, "Pill alarm updated.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(MainActivity.this, "Error updating pill alarm.", Toast.LENGTH_SHORT).show();
        }


    }

    public void updateAdapter(){
   //     CustomListAdapter cla = new CustomListAdapter(this, databaseManager.fetchDisplay());
   //    lv.setAdapter(cla);
        cla.notifyDataSetChanged();
    }
}