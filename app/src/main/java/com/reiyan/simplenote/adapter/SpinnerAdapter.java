package com.reiyan.simplenote.adapter;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reiyan.simplenote.R;


public class SpinnerAdapter extends BaseAdapter {

    private Activity activity;
    private String[] data;

    public SpinnerAdapter(Activity activity, String[] data) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
            convertView = LayoutInflater.from(activity).inflate(R.layout.spinner_item, null);

        TextView txt_repeat = convertView.findViewById(R.id.txt_repeat);
        txt_repeat.setText(data[position]);

        return convertView;
    }
}
