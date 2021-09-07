package com.DailyNeeds.dailyneeds.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.Sub_services;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes.GetAllVendorService;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes.Vendor_services;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewServiceRecycleAdapter extends RecyclerView.Adapter<NewServiceRecycleAdapter.newServiceViewholder> {

    private  Context context;
    private GetAllVendorService vendorService;
    private Vendor_services[] services;
    private  ApiClient mApi;
    private ProgressDialog progressDialog;


    public NewServiceRecycleAdapter(Context context, GetAllVendorService vendorService) {


        this.vendorService=vendorService;
        services=vendorService.getData().getVendor_services();
        this.context = context;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading!!!");
    }

    @NonNull
    @Override
    public newServiceViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_service, viewGroup, false);
        return new newServiceViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final newServiceViewholder viewHolder, final int i) {

        // viewHolder.ad_imageview.setImageResource(adRecycleritems.get(i));

        viewHolder.category1.setText("Category  :");
        viewHolder.subcategory1.setText("Subcategory :");
        viewHolder.service1.setText("Service :");

        viewHolder.category.setText(services[i].getCategory());
        viewHolder.subcategory.setText(services[i].getSub_category());
        viewHolder.service.setText(services[i].getService());

        viewHolder.deleteServiceLT.setVisibility(View.VISIBLE);
        viewHolder.deleteServiceLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ArrayList<Vendor_services>  arrayList= ArrayUtils.toArrayList(services);
                getServiceDeleteAPI(services[i].getId(),arrayList,i);
            }
        });



    }

    private void getServiceDeleteAPI(final String id, final ArrayList<Vendor_services> arrayList, final int position) {

        progressDialog.show();
       getApiClient().deleteVendorService(id).enqueue(new Callback<JsonObject>() {
           @Override
           public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
               try{

                   progressDialog.dismiss();
                   JSONObject json = new JSONObject(response.body().toString());
                   if (json.has("status")) {
                       String a = json.getString("status");
                       if (a.equalsIgnoreCase("success")) {

                        arrayList.remove(services[position]);
                        services=new Vendor_services[arrayList.size()];
                        arrayList.toArray(services);
                        notifyDataSetChanged();




                       }

                   }
               }catch (Exception e){

                   progressDialog.dismiss();
               }



           }

           @Override
           public void onFailure(Call<JsonObject> call, Throwable t) {
               progressDialog.dismiss();
           }
       });


    }

    private ApiClient getApiClient() {

        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
    }


    @Override
    public int getItemCount() {
        return services.length;
    }

    public class newServiceViewholder extends RecyclerView.ViewHolder{

        private TextView category,subcategory,service,category1,subcategory1,service1;
        private LinearLayout deleteServiceLT;

        public newServiceViewholder(@NonNull View itemView) {
            super(itemView);

            category=itemView.findViewById(R.id.serviceUserName);
            subcategory=itemView.findViewById(R.id.serviceName);
            service=itemView.findViewById(R.id.serviceDate);
            category1=itemView.findViewById(R.id.tvName);
            subcategory1=itemView.findViewById(R.id.tvServicename);
            service1=itemView.findViewById(R.id.tvDate);
            deleteServiceLT=itemView.findViewById(R.id.deleteServiceLT);
        }
    }
}
