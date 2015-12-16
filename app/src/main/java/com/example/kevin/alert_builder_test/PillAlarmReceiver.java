package com.example.kevin.alert_builder_test;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.WakefulBroadcastReceiver;

//When it is time for the alarm to go off, it is received by this class, which then notifies the person of the alarm, and...
//...set it up so that they can see information about the pill they need to take.
public class PillAlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle b = intent.getExtras();

        Pill p  = b.getParcelable("pill");
        int notificationId = b.getInt("notification_id");

        Bundle newBundle = new Bundle();
        newBundle.putParcelable("pill", p);
        newBundle.putInt("notification_id", notificationId);

        Intent notificationIntent = new Intent(context, PillDueActivity.class);
        notificationIntent.putExtras(newBundle);
        notificationIntent.putExtra("bundle", newBundle);

        PendingIntent notification = PendingIntent.getActivity(context, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //This sets the notification that will appear when it is time for someone to take their pills.
        NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder nb = new Notification.Builder(context);
        nb.setSmallIcon(R.mipmap.ic_launcher);
        nb.setContentTitle(p.getPillName());
        nb.setContentText(p.getInformation());
        nb.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        nb.setContentIntent(notification);
        nm.notify(notificationId, nb.build());
    }
}
