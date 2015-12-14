package com.example.kevin.alert_builder_test;

import android.app.Activity;
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

    ArrayList<Pill> displayList;

    public CustomListAdapter(Activity a, ArrayList<Pill> alals){
        super(a, R.layout.custom_list_adapter_view, alals);
        this.displayList = alals;
        this.activity = a;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View v = inflater.inflate(R.layout.custom_list_adapter_view, null, true);
        Pill p = displayList.get(position);
        mPillName = (TextView)v.findViewById(R.id.pill_name_adapter_view_text_view);
        mPillName.setText(p.getPillName());
        mNextTimeToTake = (TextView)v.findViewById(R.id.next_time_to_take_adapter_view_text_view);
        //mNextTimeToTake.setText(p.getNextPillHour());
        mInfo = (TextView)v.findViewById(R.id.info_adapter_view_text_view);
        mInfo.setText(p.getInformation());
        return v;
    }

}
