package com.DailyNeeds.dailyneeds.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DailyNeeds.dailyneeds.Activity.Tracking_service;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes.Assigned_services;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes.GetAllSrviceResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes.Sub_services;
import com.DailyNeeds.dailyneeds.R;

public class SubServiceAdapter extends  RecyclerView.Adapter<SubServiceAdapter.SubServiceAdapterViewholder>{

    private Context context;
    private Sub_services[] sub_services;

    public SubServiceAdapter(Context context, Sub_services[] sub_services) {
        this.context=context;
        this.sub_services=sub_services;
    }

    @NonNull
    @Override
    public SubServiceAdapter.SubServiceAdapterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.subservice, parent, false);

        return new SubServiceAdapterViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubServiceAdapter.SubServiceAdapterViewholder holder, int position) {
        holder.servicename.setText("Service :   "+sub_services[position].getService());
        holder.SubserviceName.setText("Sub Service :   "+sub_services[position].getSub_service());
        holder.price.setText("Price :   "+sub_services[position].getPrice());

    }

    @Override
    public int getItemCount() {
        return sub_services.length;
    }

    public class SubServiceAdapterViewholder extends RecyclerView.ViewHolder{

        private TextView servicename,SubserviceName,price;

        public SubServiceAdapterViewholder(@NonNull View itemView) {
            super(itemView);
            servicename=itemView.findViewById(R.id.ServiceName);
            SubserviceName=itemView.findViewById(R.id.SubServiceName);
            price=itemView.findViewById(R.id.price);

        }
    }
}
