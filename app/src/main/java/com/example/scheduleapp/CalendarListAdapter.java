package com.example.scheduleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CalendarListAdapter extends ArrayAdapter<String> {

    public CalendarListAdapter(Context context, List<String> tasks) {
        super(context, android.R.layout.simple_list_item_1, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView taskText = convertView.findViewById(android.R.id.text1);
        taskText.setText(getItem(position));

        return convertView;
    }
}
