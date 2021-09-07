package com.DailyNeeds.dailyneeds.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.DailyNeeds.dailyneeds.Adapter.ServiceAdapter;
import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse.LoginResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes.GetAllSrviceResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// MyServices
public class Services extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View rootView;
    private  ApiClient mApi;
    private ProgressDialog progressDialog;
    private RecyclerView recycleService;
    private ServiceAdapter adapter;
    private TextView noserviceTV;


    public Services() {
        // Required empty public constructor
    }


    public static Services newInstance(String param1, String param2) {
        Services fragment = new Services();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        rootView= inflater.inflate(R.layout.fragment_myorder, container, false);
        initViews();

        getAllServices();
        return rootView;


    }

    private void  getAllServices() {



    progressDialog.show();
    String token=App.getInstance().getSharedpreference().getTOKEN();
    String vendorID= App.getInstance().getSharedpreference().getID();
    getApiClient().getAllServices(token,vendorID).enqueue(new Callback<GetAllSrviceResponse>() {
        @Override
        public void onResponse(Call<GetAllSrviceResponse> call, Response<GetAllSrviceResponse> response) {

            Log.d("serviceid:","serviceid:"+new Gson().toJson(response.body()));

            progressDialog.dismiss();

            if (response.code()==401){
                LoginToken("getAllServices");
            }else {
                try {

                    GetAllSrviceResponse service = response.body();
                    if (service != null && service.getStatus().equalsIgnoreCase("success")) {
                        recycleService.setVisibility(View.VISIBLE);
                        noserviceTV.setVisibility(View.GONE);
                        adapter = new ServiceAdapter(service, getActivity());
                        recycleService.setAdapter(adapter);
                    }else{
                        recycleService.setVisibility(View.GONE);
                        noserviceTV.setVisibility(View.VISIBLE);
                    }

                } catch (Exception e) {
                    progressDialog.dismiss();
                    Log.e("eeeeeeeee", e.toString());

                }

            }
        }

        @Override
        public void onFailure(Call<GetAllSrviceResponse> call, Throwable t) {
            progressDialog.dismiss();
            Log.e("eeeeeeeee",t.toString());

        }
    });



    }

    private void LoginToken(String getAllServices) {

        String mobilestr=App.getInstance().getSharedpreference().getPHONE();
        String pass=App.getInstance().getSharedpreference().getPASSWORD();

        String loginflag="mobile";

        getApiClient().LoginMobile(mobilestr,pass,loginflag).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                LoginResponse   loginResponse=response.body();

                if (loginResponse!=null && loginResponse.getStatus()!=null){

                    try {

                        if ( loginResponse.getStatus().equalsIgnoreCase("success")){

                            if (loginResponse.getData().getUser()[0]!=null){

                                App.getInstance().getSharedpreference().setTOKEN("Bearer "+loginResponse.getData().getAccess_token());
                                Log.e("sssssssss","Bearer "+loginResponse.getData().getAccess_token());
                               getAllServices();
                            }

                        }

                    }catch (Exception e){

                    }

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });

    }

    private void initViews() {

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading!!!");
        recycleService=rootView.findViewById(R.id.recycle_Service);
         LinearLayoutManager  layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        recycleService.setLayoutManager(layoutManager);
        noserviceTV=rootView.findViewById(R.id.noserviceTV);
        noserviceTV.setVisibility(View.GONE);


    }

    private ApiClient getApiClient(){
        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
    }
}
