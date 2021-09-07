package com.DailyNeeds.dailyneeds.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.DailyNeeds.dailyneeds.Activity.AddServiceActivity;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyServiceFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyServiceFrag extends Fragment {
    private View rootView;
    private TextView addSerTV,noitemTV;
    private RecyclerView newServiceRecycle;
    private NewServiceRecycleAdapter adapter;
    private  ApiClient mApi;
    private GetAllVendorService vendorService;
    private FloatingActionButton addServiceFAB;
    private ProgressDialog progressDialog;
    public MyServiceFrag() {
        // Required empty public constructor
    }


    public static MyServiceFrag newInstance(String param1, String param2) {
        MyServiceFrag fragment = new MyServiceFrag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=inflater.inflate(R.layout.fragment_chat, container, false);

        newServiceRecycle=rootView.findViewById(R.id.newServiceRecycle);
        noitemTV=rootView.findViewById(R.id.noitemTV);
        addServiceFAB =rootView.findViewById(R.id.addFab);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading!!!");


        addServiceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), AddServiceActivity.class).putExtra("service","service"));


            }
        });
        getNewServiceAPI();








        return rootView;
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
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
                        newServiceRecycle.setLayoutManager(layoutManager);
                        adapter=new NewServiceRecycleAdapter(getActivity(),response.body());
                        newServiceRecycle.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                    }else{
                        newServiceRecycle.setVisibility(View.GONE);
                        noitemTV.setVisibility(View.VISIBLE);
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
