package com.example.kevin.alert_builder_test;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    FragmentManager fm;
    DatabaseManager databaseManager;
    android.support.design.widget.FloatingActionButton fab;
    ListView lv;


    //TODO Learn: Create AlertDialog.Builder.
    //TODO Learn: How to set alarms.
    //TODO 1) COMPLETED - Create the AlertDialog.
    //TODO 1b) COMPLETED - Have the data be properly pulled from the alert dialogs.
    //TODO 2) COMPLETED - Set the database so that the alert can be saved.
    //TODO 2a) COMPLETED - Create the database.
    //TODO 2b) COMPLETED - Have the alarm added into the database.
    //TODO 3) COMPLETED - Retrieve the information from the database.
    //TODO 3a) Show the pills in a list at the start screen
    //TODO 3a) When the program starts, have it display the information from the database.
    //TODO 4) Show the alarms.
    //TODO 4a) Set the information into a custom display layout, have the layout display the information.
    //TODO 5) Enable edit the alarm settings.
    //TODO 6) Have the alarms go off.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //mTextView = (TextView) findViewById(R.id.test_text);
        databaseManager = new DatabaseManager(this);
        fab = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);



        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getFragmentManager();
                PillDialog pd = new PillDialog();
                pd.setTargetFragment(pd, 1);
                pd.show(fm, "TAG");
            }
        });

        CustomListAdapter cla = new CustomListAdapter(this, databaseManager.fetchDisplay());
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(cla);

    }

    //this method exists at the moment to test out the database and make sure everything is working properly.
    public void changeTextView(String pillName, String pharmacy, long pharmNum, String doctor, long doctorNum, String time, int interval, int pillCount, String info){
        databaseManager.addRow(pillName, pharmacy, pharmNum, doctor, doctorNum, time, interval, pillCount, info);
        mTextView.setText(databaseManager.fetchAllContacts());
    }
}