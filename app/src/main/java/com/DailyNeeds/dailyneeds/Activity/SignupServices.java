package com.DailyNeeds.dailyneeds.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.DailyNeeds.dailyneeds.Adapter.NewServiceRecycleAdapter;
import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes.GetAllVendorService;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupServices extends AppCompatActivity {

    private RecyclerView newServiceRecycle;
    private NewServiceRecycleAdapter adapter;
    private  ApiClient mApi;
    private GetAllVendorService vendorService;
    private FloatingActionButton addServiceFAB;
    private ProgressDialog progressDialog;
    private LinearLayout finishLT;
    private ImageView Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_services);
        initviews();

        getNewServiceAPI();

        addServiceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignupServices.this, AddServiceActivity.class));
                finish();
            }
        });

        finishLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignupServices.this, Login.class));
                finish();

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initviews() {

        finishLT = findViewById(R.id.finishLT);
        newServiceRecycle=findViewById(R.id.SignSerivceRecycler);
        addServiceFAB =findViewById(R.id.AddServiceFab);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading!!!");
        Back=findViewById(R.id.Back);
    }

    private void getNewServiceAPI() {
        progressDialog.show();
        String vendorID= App.getInstance().getSharedpreference().getID();
        getApiClient().getAllVendorService(vendorID).enqueue(new Callback<GetAllVendorService>() {
            @Override
            public void onResponse(Call<GetAllVendorService> call, Response<GetAllVendorService> response) {
                try {
                    progressDialog.dismiss();
                    if (response.body().getStatus().equalsIgnoreCase("success")){
                        LinearLayoutManager layoutManager = new LinearLayoutManager(SignupServices.this, LinearLayoutManager.VERTICAL,false);
                        newServiceRecycle.setLayoutManager(layoutManager);
                        adapter=new NewServiceRecycleAdapter(SignupServices.this,response.body());
                        newServiceRecycle.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }else{
                        newServiceRecycle.setVisibility(View.GONE);
                    }

                }catch (Exception e){
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onFailure(Call<GetAllVendorService> call, Throwable t) {
                progressDialog.dismiss();
            }
        });



    }


    private ApiClient getApiClient(){
        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
    }
}
