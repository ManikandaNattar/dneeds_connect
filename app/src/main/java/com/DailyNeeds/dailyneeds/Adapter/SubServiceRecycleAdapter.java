package com.DailyNeeds.dailyneeds.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.Sub_services;
import com.DailyNeeds.dailyneeds.R;

import java.util.ArrayList;


public class SubServiceRecycleAdapter extends RecyclerView.Adapter<SubServiceRecycleAdapter.SubServcieAdapterViewHolder> {


    public interface OnItemCheckListener {
        void onItemCheck(String check,int pos);
        void onItemUncheck(String uncheck,int pos);
    }


    private OnItemCheckListener checkListener;
    Context context;
    Sub_services[] sub_services;

    public SubServiceRecycleAdapter(Sub_services[] sub_services, Context context,OnItemCheckListener checkListener) {

        this.sub_services=sub_services;
        this.checkListener=checkListener;

        this.context = context;
    }

    @NonNull
    @Override
    public SubServcieAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.subservicelist, viewGroup, false);
        return new SubServcieAdapterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubServcieAdapterViewHolder viewHolder, final int i) {

        // viewHolder.ad_imageview.setImageResource(adRecycleritems.get(i));
       viewHolder.checkBox.setText(sub_services[i].getSub_service());

        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((CheckBox) v).isChecked()) {
                    //Case 1

                    checkListener.onItemCheck(sub_services[i].getId(),i);
                    Log.e("cccccc","clicked");
                }else{
                    checkListener.onItemUncheck(sub_services[i].getId(),i);
                }

            }
        });



    }


    @Override
    public int getItemCount() {
        return sub_services.length;
    }

    public class SubServcieAdapterViewHolder extends RecyclerView.ViewHolder{

        private CheckBox checkBox;

        public SubServcieAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox=itemView.findViewById(R.id.subserviceCheckbox);
        }
    }
}
