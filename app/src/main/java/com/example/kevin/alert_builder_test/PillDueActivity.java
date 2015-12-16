package com.example.kevin.alert_builder_test;

import android.app.AlarmManager;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.widget.Toast;

//This is the activity that is called when the notification goes off. I use a different one because...
//..this way it can be shut down more easily. I would have used a dialog on its own, but that's not allowed in Android.
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

    //When a pill has been taken, this decreases the number of pills remaining, and resets the timer.
    public void pillHasBeenTaken(Pill p){

        //Since the interval is taken in hours, this increases the timer by the number of milliseconds in the selected hour amount.
        long newTime = p.getNextTimeInMillis() + (p.getIntervalLength() * 3600000);
        p.setNextTimeInMillis(newTime);

        //this shuts down the notification, since it is not needed until next time.
        NotificationManager nm = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        int cancelID = b.getInt("notification_id");
        nm.cancel(cancelID);

        //this reset the alarm.
        if(restartAlarm(p)) {
            databaseManager.pillTaken(p);
            finish();
            System.exit(0);
        }
        else {
            Toast.makeText(PillDueActivity.this, "Resetting Alarm Failed.", Toast.LENGTH_SHORT).show();
        }
    }


    //once an alarm has been finished, this will set up a new one for that drug.
    public boolean restartAlarm(Pill p ){
        try {
            AlarmManager mAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent alarmIntent = new Intent(PillDueActivity.this, PillAlarmReceiver.class);

            //For this one, I am building a unique Id for each of the individual drugs pendingIntents....
            //...With pending intents, they overwrite the existing PendingIntent if unless their is something that differentiates them...
            //...like the request code. So for mine, I have the request code linked to the id of the drug. This way, if the drug is ever...
            //...changed, then the intent should update as well. However, the ids are too long to work as ints. So instead I am parsing out...
            //...the middle nine digits (cutting off the last three and the first). This means that there will be a unique number of every second...
            //...for the next 31 years. I can only hope someone uses this program for that long.
            String s = p.getPillID().toString();
            String[] stringList = s.split("");
            String stringID = "";
            for(int i = 2; i < 11; i++){
                stringID = stringID + stringList[i];
            }
            Integer intID = Integer.parseInt(stringID);

            Bundle b = new Bundle();
            b.putParcelable("pill", p);
            alarmIntent.putExtras(b);
            b.putInt("notification_id", intID);

            PendingIntent pi = PendingIntent.getBroadcast(PillDueActivity.this, intID, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, p.getNextTimeInMillis(), pi);
            Toast.makeText(PillDueActivity.this, "Alarm Set.", Toast.LENGTH_SHORT).show();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseManager.close();
    }
}
