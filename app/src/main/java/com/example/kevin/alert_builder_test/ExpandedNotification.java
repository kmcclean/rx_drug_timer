package com.example.kevin.alert_builder_test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

//When it's time to take another pill, this calls is called by the Activity which is opened by the Notification and lets you know about it.
public class ExpandedNotification extends DialogFragment {

    AlertDialog.Builder adb;
    Pill p;

    TextView pillName;
    TextView information;
    TextView pillCount;
    TextView lowCountWarning;
    TextView callDoctor;
    TextView callPharmacy;


    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.custom_notification, null);
        pillName = (TextView) v.findViewById(R.id.notification_pill_name);
        information = (TextView) v.findViewById(R.id.notification_information);
        pillCount = (TextView) v.findViewById(R.id.notification_pill_count);
        lowCountWarning = (TextView) v.findViewById(R.id.notification_low_count_warning);
        callDoctor = (TextView) v.findViewById(R.id.notification_call_doctor);
        callPharmacy = (TextView) v.findViewById(R.id.notification_call_pharmacy);

        adb = new AlertDialog.Builder(getActivity());

        adb.setView(v);

        Bundle b = getArguments();

        p = b.getParcelable("pill");

        pillName.setText(p.getPillName());
        information.setText(p.getInformation());
        Integer i = p.getPillCount();
        String s = i.toString();
        String pillCountSetText =R.string.pill_count_set_text + s;
        pillCount.setText(pillCountSetText);

        //this is set to warn you when you are almost out of pills and allows you to call your doctor/pharmacy for a refill.
        if (p.getPillCount() < 10) {
            lowCountWarning.setText(R.string.low_count_warning_set_text);
            String callDocString = R.string.call_doctor_set_text + p.getDoctorNo().toString();
            callDoctor.setText(callDocString);
            String callPharmString = R.string.call_pharmacy_set_text + p.getPharmacyNo().toString();
            callPharmacy.setText(callPharmString);
        }

        adb.setPositiveButton("I have taken my pill", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                PillDueActivity a = (PillDueActivity) getActivity();
                a.pillHasBeenTaken(p);

            }
        });
        return adb.create();
    }
}
