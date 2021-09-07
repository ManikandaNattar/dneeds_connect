package com.DailyNeeds.dailyneeds.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.Adapter.CategoryArrayAdapter;
import com.DailyNeeds.dailyneeds.Adapter.ServiceArrayAdapter;
import com.DailyNeeds.dailyneeds.Adapter.SubCategoryArrayAdapter;
import com.DailyNeeds.dailyneeds.Adapter.SubServiceRecycleAdapter;
import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AddSerRes.AddService;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.AllCategoryServiceRes;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.Categories;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.Services;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.Sub_services;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.Subcategories;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddServiceActivity extends AppCompatActivity implements SubServiceRecycleAdapter.OnItemCheckListener {

    private  ApiClient mApi;
    private AllCategoryServiceRes resmodel;
    private Spinner catSpinner,subcatSpinner,serviceSpinner;

    private ProgressDialog progressDialog;

    private Services[] services;
    private  Subcategories[] subcategories;

    private SubCategoryArrayAdapter adapter;
    private  ServiceArrayAdapter serviceArrayAdapter;
    private RecyclerView subserRecycle;
    private SubServiceRecycleAdapter recycleAdapter;
    private Sub_services[] sub_services;
    private LinearLayout addServiceLT;
    private String vendor_id,cat_id="",sub_cat_id="",service_id="",sub_service_ids="";
    private Set<String> addSet;
    private Set<String> removeSet;
    private StringBuilder addBuild;
    private StringBuilder removeBuild;
    List<String> insertID;
    List<String> deleteID;
    private TextView subserviceTV;
    private String service="";
    private ImageView Back;
    private LinearLayout subCatLT,SerLT,SubServLT;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading!!!");
        catSpinner=findViewById(R.id.catSpinner);
        Back=findViewById(R.id.Back);
        subcatSpinner=findViewById(R.id.subcatSpinner);
        serviceSpinner=findViewById(R.id.serviceSpinner);
        subserRecycle=findViewById(R.id.subserRecycle);
        addServiceLT=findViewById(R.id.addServiceLT);
        subserviceTV=findViewById(R.id.subserviceTV);
        subCatLT=findViewById(R.id.subCatLT);
        SerLT=findViewById(R.id.SerLT);
        SubServLT=findViewById(R.id.SubServLT);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        subserRecycle.setLayoutManager(layoutManager);

        insertID =new ArrayList<>(5);
        insertID.add(0,"");

        if (getIntent().getStringExtra("service")!=null){
            service=getIntent().getStringExtra("service");
        }
        deleteID =new ArrayList<>();
        addBuild=new StringBuilder();
        removeBuild=new StringBuilder();
        addSet = new HashSet<String>();
        removeSet=new HashSet<>();
        getAllSubCategoryAPI();

        Adapters();
        addServiceLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    addServiceAPI(); 



                
            }
        });


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void addServiceAPI() {

        StringBuilder sb=new StringBuilder();
        for (String a : insertID) {
            sb.append(a);
        }
        sub_service_ids=sb.toString();

        String vendorID= App.getInstance().getSharedpreference().getID();
        String catid=cat_id;
        String sucatid=sub_cat_id;
        String serviceid=service_id;
        String subserids=sub_service_ids;

        Log.e("iiii",subserids);

        if (!sub_service_ids.isEmpty() && !service_id.isEmpty()){

            progressDialog.show();
            getApiClient().AddServiceApi(vendorID,cat_id,sub_cat_id,service_id,sub_service_ids).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        Log.e("responseee",response.body().toString());
                        progressDialog.dismiss();
                        JSONObject json = new JSONObject(response.body().toString());
                        if (json.has("status")){
                            String a=json.getString("status");
                            if (a.equalsIgnoreCase("success")){

                                Toast.makeText(AddServiceActivity.this, "Registered Succesfully !!", Toast.LENGTH_SHORT).show();
                                if (service.equalsIgnoreCase("service")){
                                    startActivity(new Intent(AddServiceActivity.this,MainActivity.class));
                                    finish();
                                }else{
                                   /* startActivity(new Intent(AddServiceActivity.this, Login.class));
                                    finish();*/

                                    startActivity(new Intent(AddServiceActivity.this, SignupServices.class));

                                }

                            }else{

                                Toast.makeText(AddServiceActivity.this, "kindly choose sub service !!", Toast.LENGTH_SHORT).show();
                            }
                        }


                    }catch (Exception e){
                        progressDialog.dismiss();

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("eee",t.toString());
                    progressDialog.dismiss();
                    Toast.makeText(AddServiceActivity.this, "Try again later!!", Toast.LENGTH_SHORT).show();
                }
            });



        }else{
            Toast.makeText(AddServiceActivity.this, "select sub service!! ", Toast.LENGTH_SHORT).show();
        }



    }

    private void Adapters() {



        catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (position!=0){
                    subCatLT.setVisibility(View.VISIBLE);
                    int j=resmodel.getData().getSubcategories().length;
                    cat_id= resmodel.getData().getCategories()[position-1].getId();

                    List<Subcategories> list=new ArrayList<>();
                    list.clear();
                    for (int i=0;i<j;i++) {

                        if (resmodel.getData().getCategories()[position-1].getId().equals(resmodel.getData().getSubcategories()[i].getCat_id())) {

                            list.add(resmodel.getData().getSubcategories()[i]);
                        }

                    }

                    subcategories=new Subcategories[list.size()];
                    list.toArray(subcategories);



                    List<Subcategories> arraylist=new ArrayList<>(Arrays.asList(subcategories));
                    Subcategories sub=new Subcategories();

                    sub.setSub_category("Select Category");

                    arraylist.add(0,sub);

                    Subcategories[] subcategories2=arraylist.toArray(new Subcategories[arraylist.size()]);
                    subcategories = subcategories2;
                    //sub category adapter

                    adapter= new SubCategoryArrayAdapter(AddServiceActivity.this,R.layout.spinerlayout,R.id.adapterTV,subcategories);
                    subcatSpinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }else{

                    subCatLT.setVisibility(View.GONE);
                    SerLT.setVisibility(View.GONE);
                    SubServLT.setVisibility(View.GONE);
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });


        subcatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position!=0){

                    SerLT.setVisibility(View.VISIBLE);


                    int j=resmodel.getData().getServices().length;
                    sub_cat_id=subcategories[position].getId();

                    List<Services> list=new ArrayList<>();
                    Services services1=new Services();
                    services1.setService("Select Service");
                    list.add(0,services1);
                    for (int i=0;i<j;i++) {

                        if (subcategories[position].getId().equals(resmodel.getData().getServices()[i].getSub_cat_id())) {

                            list.add(resmodel.getData().getServices()[i]);
                        }

                    }

                    services=new Services[list.size()];
                    list.toArray(services);

                    //sub category adapter

                    if (services.length>0){

                        serviceSpinner.setVisibility(View.VISIBLE);
                        subserRecycle.setVisibility(View.VISIBLE);
                        subserviceTV.setVisibility(View.VISIBLE);

                        serviceArrayAdapter= new ServiceArrayAdapter(AddServiceActivity.this,R.layout.spinner_dropdown,R.id.spinnerdropTV,services);
                        serviceSpinner.setAdapter(serviceArrayAdapter);
                        serviceArrayAdapter.notifyDataSetChanged();

                    }else{
                        serviceSpinner.setVisibility(View.GONE);
                        subserRecycle.setVisibility(View.GONE);
                        subserviceTV.setVisibility(View.GONE);


                    }


                }else{

                    SerLT.setVisibility(View.GONE);
                    SubServLT.setVisibility(View.GONE);
                }





            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        serviceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position!=0){

                    SubServLT.setVisibility(View.VISIBLE);
                    List<Sub_services> list=new ArrayList<>();
                    list.clear();
                    int j=resmodel.getData().getSub_services().length;

                    service_id=services[position].getId();

                    for (int i=0;i<j;i++) {

                        if (services[position].getId().equals(resmodel.getData().getSub_services()[i].getService_id())) {

                            list.add(resmodel.getData().getSub_services()[i]);
                        }

                    }

                    sub_services=new Sub_services[list.size()];
                    list.toArray(sub_services);
                    if (sub_services.length>0){
                        subserRecycle.setVisibility(View.VISIBLE);
                        subserviceTV.setVisibility(View.VISIBLE);
                        recycleAdapter=new SubServiceRecycleAdapter(sub_services, AddServiceActivity.this, (SubServiceRecycleAdapter.OnItemCheckListener) AddServiceActivity.this);
                        subserRecycle.setAdapter(recycleAdapter);
                        recycleAdapter.notifyDataSetChanged();
                    }else {
                        subserRecycle.setVisibility(View.GONE);
                        subserviceTV.setVisibility(View.GONE);
                    }

                }else{
                    SubServLT.setVisibility(View.GONE);

                }



               /* adapter = new ServiceAdapter(service, getActivity());
                recycleService.setAdapter(adapter);*/

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void getAllSubCategoryAPI() {

        progressDialog.show();
        getApiClient().getAllCategService().enqueue(new Callback<AllCategoryServiceRes>() {
            @Override
            public void onResponse(Call<AllCategoryServiceRes> call, Response<AllCategoryServiceRes> response) {

                Log.d("responseone:","responeone:"+new Gson().toJson(response.body()));


                progressDialog.dismiss();
                try {

                    resmodel=response.body();

                    // cateory Adapter
                    CategoryArrayAdapter catAdapter;
                    Categories[] categories=resmodel.getData().getCategories();

                    List<Categories> arraylist=new ArrayList<>(Arrays.asList(categories));
                    Categories categories1=new Categories();

                    categories1.setCategory("Select Category");
                    categories1.setDisplay_order("displayordedr");

                    arraylist.add(0,categories1);

                    Categories[] categories2=arraylist.toArray(new Categories[arraylist.size()]);

                    catAdapter=new CategoryArrayAdapter(AddServiceActivity.this,R.layout.spinerlayout,R.id.adapterTV,categories2);
                    catSpinner.setAdapter(catAdapter);
                    catAdapter.notifyDataSetChanged();




                }catch (Exception e){

                }

            }

            @Override
            public void onFailure(Call<AllCategoryServiceRes> call, Throwable t) {
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


        Set set=new HashSet();

    @Override
    public void onItemCheck(String check,int pos) {

        insertID.add(check+",");

    }

    @Override
    public void onItemUncheck(String uncheck,int pos) {


        insertID.remove(uncheck+",");
    }
}
