package com.DailyNeeds.dailyneeds.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.Services;
import com.DailyNeeds.dailyneeds.R;


public class ServiceArrayAdapter extends ArrayAdapter<Services> {

    private Services[] items;
    private Activity context;

    public ServiceArrayAdapter(Activity context,int resourceID,int textviewID, Services[] items) {
        super(context,resourceID,textviewID,items);

        this.items = items;
        this.context = context;
        this.notifyDataSetChanged();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getDropDownView(position, convertView, parent);

        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.spinner_dropdown, null);
        }
        TextView lbl = (TextView) v.findViewById(R.id.spinnerdropTV);
        lbl.setText(items[position].getService());

        return v;
    }

    @Override
    public Services getItem(int position) {
        return items[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.spinner_dropdown,parent,false);

        TextView textView=view.findViewById(R.id.spinnerdropTV);
        textView.setText(items[position].getService());
        return view;
    }
}