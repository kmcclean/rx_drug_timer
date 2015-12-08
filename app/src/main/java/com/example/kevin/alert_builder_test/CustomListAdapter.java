package com.example.kevin.alert_builder_test;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter {

    Activity activity;
    TextView mPillName;
    TextView mNextTimeToTake;
    TextView mInfo;

    ArrayList<ArrayList<String>> displayList;

    public CustomListAdapter(Activity a, ArrayList<ArrayList<String>> alals){
        super(a, R.layout.custom_list_adapter_view, alals);
        this.displayList = alals;
        this.activity = a;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_list_adapter_view, null, true);
        ArrayList<String> pillData = displayList.get(position);
        mPillName = (TextView)v.findViewById(R.id.pill_name_adapter_view_text_view);
        mPillName.setText(pillData.get(0));
        mNextTimeToTake = (TextView)v.findViewById(R.id.next_time_to_take_adapter_view_text_view);
        mNextTimeToTake.setText(pillData.get(1));
        mInfo = (TextView)v.findViewById(R.id.info_adapter_view_text_view);
        mInfo.setText(pillData.get(2));
        return v;
    }


}
