package com.example.kevin.alert_builder_test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class PillDialogNew extends DialogFragment {


    AlertDialog.Builder adb;
    EditText mPillNameEditText;
    EditText mPharmacyEditText;
    EditText mPharmacyNoEditText;
    EditText mDoctorEditText;
    EditText mDoctorNoEditText;
    TimePicker mTimePicker;
    EditText mPillCountEditText;
    EditText mIntervalEditText;
    EditText mInfoEditText;
    String time;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.custom_alert_dialog_layout, null);
        adb = new AlertDialog.Builder(getActivity());
        adb.setView(v);
        mPillNameEditText = (EditText) v.findViewById(R.id.pill_name_edit_text);
        mIntervalEditText = (EditText) v.findViewById(R.id.interval_edit_text);
        mPharmacyEditText = (EditText) v.findViewById(R.id.pharmacy_edit_text);
        mPharmacyNoEditText = (EditText) v.findViewById(R.id.pharmacy_no_edit_text);
        mDoctorEditText = (EditText) v.findViewById(R.id.doctor_edit_text);
        mDoctorNoEditText = (EditText) v.findViewById(R.id.doctor_no_edit_text);
        mInfoEditText = (EditText) v.findViewById(R.id.info_edit_text);
        mPillCountEditText = (EditText) v.findViewById(R.id.pill_count_edit_text);
        mTimePicker = (TimePicker) v.findViewById(R.id.first_drug_time_picker);
        adb.setMessage("Add a new pill");


        adb.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                //this takes all of the information that has been stored and sends it off to the database.
                time = Integer.toString(mTimePicker.getCurrentHour()) + ":" + Integer.toString(mTimePicker.getCurrentMinute());

                //calls the mainActivity method which runs the info. While best practice seems...
                //to be to run the onActivityResult in the mainActivity, this was not working, so running it in...
                //this form is the current solution.
                Calendar c = Calendar.getInstance();
                Long id = c.getTimeInMillis();
                MainActivity a = (MainActivity)getActivity();
                if(a.addNewPill(id, mPillNameEditText.getText().toString(),
                        mPharmacyEditText.getText().toString(),
                        Long.parseLong(mPharmacyNoEditText.getText().toString()),
                        mDoctorEditText.getText().toString(),
                        Long.parseLong(mDoctorNoEditText.getText().toString()),
                        time,
                        Integer.parseInt(mPillCountEditText.getText().toString()),
                        Integer.parseInt(mIntervalEditText.getText().toString()),
                        mInfoEditText.getText().toString())){
                        a.updateAdapter();
                }

            }
        });

        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return adb.create();
    }
}
