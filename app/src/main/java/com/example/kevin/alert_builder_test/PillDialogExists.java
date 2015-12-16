package com.example.kevin.alert_builder_test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;

public class PillDialogExists extends DialogFragment{

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
        Long id;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.custom_alert_dialog_layout, null);
            adb = new AlertDialog.Builder(getActivity());
            adb.setView(v);


            Bundle b = getArguments();

            Pill p = b.getParcelable("pill");

            id = p.getPillID();

            mPillNameEditText = (EditText) v.findViewById(R.id.pill_name_edit_text);
            mPillNameEditText.setText(p.getPillName());

            mIntervalEditText = (EditText) v.findViewById(R.id.interval_edit_text);
            Integer interval = p.getIntervalLength();
            mIntervalEditText.setText(interval.toString());

            mPharmacyEditText = (EditText) v.findViewById(R.id.pharmacy_edit_text);
            mPharmacyEditText.setText(p.pharmacyName);

            mPharmacyNoEditText = (EditText) v.findViewById(R.id.pharmacy_no_edit_text);
            Long pharmacyNumber = p.getPharmacyNo();
            mPharmacyNoEditText.setText(pharmacyNumber.toString());

            mDoctorEditText = (EditText) v.findViewById(R.id.doctor_edit_text);
            mDoctorEditText.setText(p.getDoctorName());

            mDoctorNoEditText = (EditText) v.findViewById(R.id.doctor_no_edit_text);
            Long doctorNumber = p.doctorNo;
            mDoctorNoEditText.setText(doctorNumber.toString());

            mInfoEditText = (EditText) v.findViewById(R.id.info_edit_text);
            mInfoEditText.setText(p.getInformation());

            mPillCountEditText = (EditText) v.findViewById(R.id.pill_count_edit_text);
            Integer count = p.getPillCount();
            mPillCountEditText.setText(count.toString());

            mTimePicker = (TimePicker) v.findViewById(R.id.first_drug_time_picker);

            adb.setMessage("Edit a new pill");

            //this takes all of the information that has been stored and sends it off to the database.
            adb.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //calls the mainActivity method to update the pill information. While best practice seems...
                    //to be to run the onActivityResult in the mainActivity, this was not working, so running it in...
                    //this form is the current solution.
                    MainActivity a = (MainActivity) getActivity();
                    a.updatePill(id, mPillNameEditText.getText().toString(),
                            mPharmacyEditText.getText().toString(),
                            Long.parseLong(mPharmacyNoEditText.getText().toString()),
                            mDoctorEditText.getText().toString(),
                            Long.parseLong(mDoctorNoEditText.getText().toString()),
                            mTimePicker.getCurrentHour(),
                            mTimePicker.getCurrentMinute(),
                            Integer.parseInt(mPillCountEditText.getText().toString()),
                            Integer.parseInt(mIntervalEditText.getText().toString()),
                            mInfoEditText.getText().toString());
                }
            });

            adb.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MainActivity a = (MainActivity) getActivity();
                    a.deletePill(id);
                }
            });

            adb.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            return adb.create();
        }
}
