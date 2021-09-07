package com.DailyNeeds.dailyneeds.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DailyNeeds.dailyneeds.Activity.Tracking_service;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes.Assigned_services;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes.GetAllSrviceResponse;
import com.DailyNeeds.dailyneeds.R;
import com.google.gson.Gson;

import java.util.ArrayList;


public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServcieAdapterViewHolder> {
    Context context;
    GetAllSrviceResponse allSrviceResponse;
    Assigned_services[] services;

    public ServiceAdapter(GetAllSrviceResponse allSrviceResponse,Context context) {

        this.allSrviceResponse=allSrviceResponse;
        services=allSrviceResponse.getData().getAssigned_services();

        this.context = context;
    }

    @NonNull
    @Override
    public ServcieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.serviceadapter, viewGroup, false);
        return new ServcieAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServcieAdapterViewHolder viewHolder, final int i) {

       // viewHolder.ad_imageview.setImageResource(adRecycleritems.get(i));
        if (services[i].getUser_first_name()==null){
            viewHolder.serviceUserName.setText(" : Name");
        }else{
            viewHolder.serviceUserName.setText(" : "+services[i].getUser_first_name());
        }

        viewHolder.serviceName.setText(" : "+services[i].getService());
        viewHolder.serviceDate.setText(" : "+services[i].getService_request_date());
        viewHolder.serviceOrderid.setText(" : "+services[i].getOrder_no());
        if (services[i].getIs_order_completed().equalsIgnoreCase("Y")){
            viewHolder.status.setText("Completed");
        }else{
            viewHolder.status.setText("In Progress");
            viewHolder.status.setTextColor(Color.parseColor("#D81B60"));
        }


        viewHolder.serivceLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();

                String myJson = gson.toJson(services[i]);
                context.startActivity(new Intent(context, Tracking_service.class).putExtra("model",myJson));

            }
        });
    }


    @Override
    public int getItemCount() {
        return services.length;
    }

    public class ServcieAdapterViewHolder  extends RecyclerView.ViewHolder{

        private LinearLayout serivceLT;
        private TextView serviceOrderid,serviceUserName,serviceName,serviceDate,status;

        public ServcieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            serivceLT=itemView.findViewById(R.id.serivceLT);
            serviceUserName=itemView.findViewById(R.id.serviceUserName);
            serviceName=itemView.findViewById(R.id.serviceName);
            serviceDate=itemView.findViewById(R.id.serviceDate);
            serviceOrderid=itemView.findViewById(R.id.ServiceOrderid);

            status=itemView.findViewById(R.id.statusTV);
        }
    }
}
