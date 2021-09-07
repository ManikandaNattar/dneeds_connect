package com.DailyNeeds.dailyneeds.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.Subcategories;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.Subcategories;
import com.DailyNeeds.dailyneeds.R;


public class SubCategoryArrayAdapter extends ArrayAdapter<Subcategories> {

    Subcategories[] items;
    private Activity context;


    public SubCategoryArrayAdapter(Activity context, int resourceID, int textviewID, Subcategories[] items) {
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
            v = inflater.inflate(R.layout.spinerlayout, null);
        }
        TextView lbl = (TextView) v.findViewById(R.id.adapterTV);
        lbl.setText(items[position].getSub_category());

        return v;
    }

    @Override
    public Subcategories getItem(int position) {
        return items[position];
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null)
            view = LayoutInflater.from(context).inflate(R.layout.spinerlayout,parent,false);

        TextView textView=view.findViewById(R.id.adapterTV);
        textView.setText(items[position].getSub_category());
        return view;
    }

}