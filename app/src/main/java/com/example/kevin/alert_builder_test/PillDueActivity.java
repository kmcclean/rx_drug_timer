package com.example.kevin.alert_builder_test;

import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PillDueActivity extends AppCompatActivity {

    FragmentManager fm;
    DatabaseManager databaseManager;
    Bundle b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseManager = new DatabaseManager(this);
        fm = getFragmentManager();

        ExpandedNotification en = new ExpandedNotification();
        b = getIntent().getBundleExtra("bundle");

        en.setArguments(b);
        en.setTargetFragment(en, 2);
        en.show(fm, "TAG1");
    }

    public void pillHasBeenTaken(Pill p){
        databaseManager.pillTaken(p);
        NotificationManager nm = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        int cancelID = b.getInt("notification_id");
        nm.cancel(cancelID);
        finish();
        System.exit(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseManager.close();
    }
}
