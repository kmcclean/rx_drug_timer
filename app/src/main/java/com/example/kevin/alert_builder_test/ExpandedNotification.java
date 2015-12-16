package com.example.kevin.alert_builder_test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
        pillCount.setText("Pills Remaining: " + s);
        if (p.getPillCount() < 10) {
            lowCountWarning.setText("You are almost out of pills. Please call your doctor or pharmacy.");
            callDoctor.setText("Doctor: " + p.getDoctorNo());
            callPharmacy.setText("Pharmacy: " + p.getPharmacyNo());
        }

        adb.setPositiveButton("I have taken my pill", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                PillDueActivity a = (PillDueActivity) getActivity();
                long newTime = p.getNextTimeInMillis() + (p.getIntervalLength() * 3600000);
                p.setNextTimeInMillis(newTime);
                a.pillHasBeenTaken(p);

            }
        });

        return adb.create();
    }
}
