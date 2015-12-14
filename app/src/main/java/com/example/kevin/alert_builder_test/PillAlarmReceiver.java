package com.example.kevin.alert_builder_test;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

public class PillAlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle b = intent.getExtras();

        Pill p  = b.getParcelable("pill");


        String notificationText = p.information + "\nNumber remaining: " + p.getPillCount();
        if(p.getPillCount() < 10){
            notificationText = notificationText + "\n You are running out of pills. Please consult your doctor or pharmacy.";
        }

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nm = (NotificationManager)context.getSystemService(ns);
        Notification.Builder nb = new Notification.Builder(context);
        nb.setSmallIcon(R.mipmap.ic_launcher);
        nb.setContentTitle(p.getPillName());
        nb.setContentText(notificationText);
        nm.notify(0, nb.build());
    }
}
