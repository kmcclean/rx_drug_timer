package com.example.kevin.alert_builder_test;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    AlertDialog.Builder adb;
    EditText mPillNameEditText;
    EditText mIntervalEditText;
    EditText mPharmacyEditText;
    EditText mPharmacyNoEditText;
    EditText mDoctorEditText;
    EditText mDoctorNoEditText;
    EditText mInfoEditText;
    EditText mPillCountEditText;


    //TODO Learn: Create AlertDialog.Builder.
    //TODO Learn: How to set alarms.
    //TODO 1) Create the AlertDialog.
    //TODO 1b) Have the data be properly pulled from the alert dialogs.
    //TODO 2) Set the database so that the alert can be saved.
    //TODO 2a) Create the database.
    //TODO 2b) Have the pulled data go into the database.
    //TODO 3) Retrieve the information from the database.
    //TODO 3a) When the program starts, have it display the information from the database.
    //TODO 4) Show the alarms.
    //TODO 4a) Set the information into a custom display layout, have the layout display the information.
    //TODO 5) Enable edit the alarm settings.
    //TODO 6) Have the alarms go off.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = (TextView) findViewById(R.id.test_text);
        adb = new AlertDialog.Builder(this);
        adb.setView(R.layout.custom_alert_dialog_layout);
        mPillNameEditText = (EditText) findViewById(R.id.pill_name_edit_text);
        mIntervalEditText = (EditText) findViewById(R.id.interval_edit_text);
        mPharmacyEditText = (EditText) findViewById(R.id.pharmacy_edit_text);
        mPharmacyNoEditText = (EditText) findViewById(R.id.pharmacy_no_edit_text);
        mDoctorEditText = (EditText) findViewById(R.id.doctor_edit_text);
        mDoctorNoEditText = (EditText) findViewById(R.id.doctor_no_edit_text);
        mInfoEditText = (EditText) findViewById(R.id.info_edit_text);
        mPillCountEditText = (EditText) findViewById(R.id.pill_count_edit_text);

        adb.setMessage("Add a new pill");
        adb.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                    /*
                        android:id="@+id/pill_name_edit_text"
                        android:id="@+id/interval_edit_text"
                        android:id="@+id/pharmacy_edit_text"
                        android:id="@+id/pharmacy_no_edit_text"
                        android:id="@+id/doctor_edit_text"
                        android:id="@+id/doctor_no_edit_text"
                        android:id="@+id/info_edit_text"
                        android:id="@+id/pill_count_edit_text"
                     */
                        /*Toast.makeText(MainActivity.this, "You selected yes.", Toast.LENGTH_SHORT).show();
                        mTextView.setText("Yes");
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You selected no.", Toast.LENGTH_SHORT).show();
                    }
                });
                adb.create();
                adb.show();

    /*
        android:id="@+id/pill_name_edit_text"
        android:id="@+id/interval_edit_text"
        android:id="@+id/pharmacy_edit_text"
        android:id="@+id/pharmacy_no_edit_text"
        android:id="@+id/doctor_edit_text"
        android:id="@+id/doctor_no_edit_text"
        android:id="@+id/info_edit_text"
        android:id="@+id/pill_count_edit_text"
     */

        /*mPressHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adb.setView(R.layout.custom_alert_dialog_layout);
                mPillNameEditText = (EditText)findViewById(R.id.pill_name_edit_text);
                mIntervalEditText = (EditText)findViewById(R.id.interval_edit_text);
                mPharmacyEditText = (EditText)findViewById(R.id.pharmacy_edit_text);
                mPharmacyNoEditText = (EditText)findViewById(R.id.pharmacy_no_edit_text);
                mDoctorEditText = (EditText)findViewById(R.id.doctor_edit_text);
                mDoctorNoEditText = (EditText)findViewById(R.id.doctor_no_edit_text);
                mInfoEditText = (EditText)findViewById(R.id.info_edit_text);
                mPillCountEditText = (EditText)findViewById(R.id.pill_count_edit_text);

                adb.setMessage("This is a test");
                adb.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    /*
                        android:id="@+id/pill_name_edit_text"
                        android:id="@+id/interval_edit_text"
                        android:id="@+id/pharmacy_edit_text"
                        android:id="@+id/pharmacy_no_edit_text"
                        android:id="@+id/doctor_edit_text"
                        android:id="@+id/doctor_no_edit_text"
                        android:id="@+id/info_edit_text"
                        android:id="@+id/pill_count_edit_text"
                     */
                        /*Toast.makeText(MainActivity.this, "You selected yes.", Toast.LENGTH_SHORT).show();
                        mTextView.setText("Yes");
                    }
                });
                adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "You selected no.", Toast.LENGTH_SHORT).show();
                    }
                });
                adb.create();
                adb.show();
            }
        });*/
            }
        });
        adb.create();
        adb.show();
    }
}


