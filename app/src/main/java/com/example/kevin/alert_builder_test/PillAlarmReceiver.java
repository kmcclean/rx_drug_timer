package com.example.kevin.alert_builder_test;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Kevin on 12/11/2015.
 */
public class PillAlarmReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle b = intent.getExtras();

        Long id = b.getLong("pillID");

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nm = (NotificationManager)context.getSystemService(ns);
        Notification.Builder nb = new Notification.Builder(context);
        nb.setSmallIcon(R.mipmap.ic_launcher);
        nb.setContentTitle(id.toString());
        nb.setContentText("This is a test of notifications");
        nm.notify(0, nb.build());

    }
}
