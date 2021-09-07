package com.DailyNeeds.dailyneeds.Fragment;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.Activity.HomeNewResponse;
import com.DailyNeeds.dailyneeds.Activity.Splash;
import com.DailyNeeds.dailyneeds.Adapter.AcceptServiceAdapter;
import com.DailyNeeds.dailyneeds.Adapter.ImageAdapter;
import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.HomeResponse.Assigned_services;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.HomeResponse.HomeResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse.LoginResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.DailyNeeds.dailyneeds.Utils.Assigned_servicesNew;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFrag extends Fragment {
    private View rootView;
    private String vendorID;
    private  ApiClient mApi;
    private HomeResponse homeresponse;
    private String totalServices,activeServices,pendingServices,completeServices;
    private TextView tService,aService,pService,cService,noservice;
    private ProgressDialog progressDialog;
    private String orderID;
    private RecyclerView recycleSlide;
    private ImageAdapter adapter;
    private   RecyclerView.LayoutManager layoutManager;
    private DatabaseReference databaseReference;
    private RecyclerView acceptRecycler;
    private Switch serviceAvailable;
    private TextView Rewards;
    private  LinearSnapHelper linearSnapHelper;
    private LinearLayoutManager linearLayoutManager;
    private String OnOff="";

    String distanceValue_str="";
    String durationValue_str="";

    SharedPreferences sharedPreferences12;

    String profile_mobienoupdate="";

    String latitude_stri="";
    String longitude_stri="";




    public HomeFrag() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vendorID= App.getInstance().getSharedpreference().getID();
        Log.e("iiiiiiiii",vendorID);





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog=new ProgressDialog(getContext());
        progressDialog.setMessage("Loading!!!");

        sharedPreferences12 = getActivity().getSharedPreferences("CURRENTLATELONG", Context.MODE_PRIVATE);

        initviews();
        getHomeCards();
        clickEvent();







        return rootView;
    }

    private void ShowSuspendDialog() {


        final Dialog dialog = new Dialog(getActivity());


        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.alert);
        dialog.setCanceledOnTouchOutside(false);

      //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();

        wmlp.gravity = Gravity.CENTER;
       // wmlp.dimAmount=0.0f;
        TextView TVCancel=dialog.findViewById(R.id.TVCancel);
        dialog.show();

        TVCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

    }


    private void initviews() {

        tService=rootView.findViewById(R.id.tService);
        aService=rootView.findViewById(R.id.aService);
        pService=rootView.findViewById(R.id.pService);
        cService=rootView.findViewById(R.id.cService);
        noservice=rootView.findViewById(R.id.noservice);
        /*declineBtn=rootView.findViewById(R.id.declineBtn);
        acceptBtn=rootView.findViewById(R.id.acceptBtn);
        buttonLT=rootView.findViewById(R.id.buttonLT);
        newServiceTV=rootView.findViewById(R.id.newServiceTV);
 */       recycleSlide=rootView.findViewById(R.id.recycleSlide);
        linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(recycleSlide);
        acceptRecycler=rootView.findViewById(R.id.recyclerNewService);

       linearLayoutManager = new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recycleSlide.setLayoutManager(linearLayoutManager);
        recycleSlide.setItemAnimator(new DefaultItemAnimator());

        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        acceptRecycler.setLayoutManager(layoutManager);
        acceptRecycler.setItemAnimator(new DefaultItemAnimator());

        serviceAvailable=rootView.findViewById(R.id.availableSwitch);
        Rewards=rootView.findViewById(R.id.rewards);


    }

    //old code
    private void getHomeCards() {

        progressDialog.show();
        String token=App.getInstance().getSharedpreference().getTOKEN();
        String vendor_latitude=App.getInstance().getSharedpreference().getLATITUDE();
        String vendor_longitude=App.getInstance().getSharedpreference().getLONGITUTE();
        Log.d("homerequest:1","homerequest:1" +token);
        Log.d("homerequest:11","homerequest:11" +vendorID);
        Log.d("homerequest:111","homerequest:111" +vendor_latitude);
        Log.d("homerequest:1111","homerequest:1111" +vendor_longitude);
        getApiClient().getHomeDetail(token,vendorID,vendor_latitude,vendor_longitude).enqueue(new Callback<JsonObject>() {
//        getApiClient().getHomeDetail(token,vendorID).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("homerequest:11111","homerequest:11111"+new Gson().toJson(response.body()));

                progressDialog.dismiss();
                if (response.code()==401){

                        LoginToken("getHomeCards");
                }else {


                    try {

                        final String body = response.body().toString(); // Or use response.raw(), if want raw response string
                        JsonParser jsonParser = new JsonParser();
                        JsonObject res = jsonParser.parse(body).getAsJsonObject();
                        Log.e("get_home_cards",res.toString());

                        Gson gson = new Gson();

                        //homeresponse = gson.fromJson(jsonstr,HomeResponse.class );
                        String mJsonString=res.toString();
                        JsonParser parser = new JsonParser();
                        JsonElement mJson =  parser.parse(mJsonString);
                        homeresponse = gson.fromJson(mJson, HomeResponse.class);


                        if (homeresponse.getData().getIs_vendor_on().equalsIgnoreCase("Y")){
                            serviceAvailable.setChecked(true);
                            if (!App.getInstance().sharedPreference.getNOTIFICATION().equalsIgnoreCase("Y")){
                                sendNotification("Your Service is Available", "DNeeds" );
                                App.getInstance().sharedPreference.setNOTIFICATION("Y");
                            }

                        }
                        Log.d("homerequest:11111:","homerequest:11111:"+homeresponse.getData().getVendor_points());
                        Rewards.setText("Rewards : "+homeresponse.getData().getVendor_points());

                        totalServices = homeresponse.getData().getTotal_services();
                        pendingServices = homeresponse.getData().getTotal_pending_services();
                        activeServices = homeresponse.getData().getTotal_active_services();
                        completeServices = homeresponse.getData().getTotal_completed_services();




                        if (homeresponse.getData().getIs_vendor_suspend().equalsIgnoreCase("Y")){
                            ShowSuspendDialog();
                        }

                        App.getInstance().getSharedpreference().setSERVICES(totalServices,activeServices,pendingServices,completeServices);


                        adapter=new ImageAdapter(homeresponse.getData().getCategory_images(),getActivity());
                        recycleSlide.setAdapter(adapter);

                        final Timer timer = new Timer();
                        timer.schedule(new TimerTask() {

                            @Override
                            public void run() {

                                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (adapter.getItemCount() - 1)) {

                                    linearLayoutManager.smoothScrollToPosition(recycleSlide, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
                                }

                                else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (adapter.getItemCount() - 1)) {

                                    linearLayoutManager.smoothScrollToPosition(recycleSlide, new RecyclerView.State(), 0);
                                }
                            }
                        }, 0, 3000);


                       /* tService.setText("Your total Services :" + totalServices);
                        aService.setText("Your active Services :" + activeServices);
                        pService.setText("Your pending Services :" + pendingServices);
                        cService.setText("Your complete Services :" + completeServices);*/

                        latitude_stri = sharedPreferences12.getString("LATITUDE", "");
                        longitude_stri = sharedPreferences12.getString("LONGITUTE", "");

                        Log.d("latitude_stri1:","latitude_str1i:"+latitude_stri);
                        Log.d("latitude_stri11:","latitude_stri11:"+longitude_stri);



                        if (homeresponse.getData().getAssigned_services().length!=0){

                            acceptRecycler.setVisibility(View.VISIBLE);
                            noservice.setVisibility(View.GONE);
//                            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(orderID);
//                            if (databaseReference.child("is_vendor_accepted").equals("C")){
//
//                            }

                            //old code
                            AcceptServiceAdapter adapter=new AcceptServiceAdapter( getActivity(),homeresponse.getData().getAssigned_services());
                            acceptRecycler.setAdapter(adapter);
                            adapter.notifyDataSetChanged();


                        }else{
                            acceptRecycler.setVisibility(View.GONE);
                            noservice.setVisibility(View.VISIBLE);
                            noservice.setText("No Service Request Available !!!");
                           // Toast.makeText(getActivity(), "No service Request Available !!!", Toast.LENGTH_SHORT).show();
                        }



                    } catch (Exception e) {
                        progressDialog.dismiss();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("errrrrrrr",t.toString());

            }
        });
    }

    //new code
//    private void getHomeCards() {
//
//        progressDialog.show();
//        String token=App.getInstance().getSharedpreference().getTOKEN();
//        String vendor_latitude=App.getInstance().getSharedpreference().getLATITUDE();
//        String vendor_longitude=App.getInstance().getSharedpreference().getLONGITUTE();
//        Log.d("homerequest:1","homerequest:1" +token);
//        Log.d("homerequest:11","homerequest:11" +vendorID);
//        Log.d("homerequest:111","homerequest:111" +vendor_latitude);
//        Log.d("homerequest:1111","homerequest:1111" +vendor_longitude);
//        getApiClient().getHomeDetail(token,vendorID,vendor_latitude,vendor_longitude).enqueue(new Callback<HomeNewResponse>() {
//            @Override
//            public void onResponse(Call<HomeNewResponse> call, Response<HomeNewResponse> response) {
//
//                Log.d("homerequest:11111","homerequest:11111"+new Gson().toJson(response.body()));
//
//                progressDialog.dismiss();
//                if (response.code()==401){
//
//                    LoginToken("getHomeCards");
//                }else {
//
//
//                    try {
//
//                        final String body = response.body().toString(); // Or use response.raw(), if want raw response string
//                        JsonParser jsonParser = new JsonParser();
//                        JsonObject res = jsonParser.parse(body).getAsJsonObject();
//                        Log.e("get_home_cards",res.toString());
//
//                        Gson gson = new Gson();
//
//                        //homeresponse = gson.fromJson(jsonstr,HomeResponse.class );
//                        String mJsonString=res.toString();
//                        JsonParser parser = new JsonParser();
//                        JsonElement mJson =  parser.parse(mJsonString);
//                        homeresponse = gson.fromJson(mJson, HomeResponse.class);
//
//
//                        if (homeresponse.getData().getIs_vendor_on().equalsIgnoreCase("Y")){
//                            serviceAvailable.setChecked(true);
//                            if (!App.getInstance().sharedPreference.getNOTIFICATION().equalsIgnoreCase("Y")){
//                                sendNotification("Your Service is Available", "DNeeds" );
//                                App.getInstance().sharedPreference.setNOTIFICATION("Y");
//                            }
//
//                        }
//                        Log.d("homerequest:11111:","homerequest:11111:"+homeresponse.getData().getVendor_points());
//                        Rewards.setText("Rewards : "+homeresponse.getData().getVendor_points());
//
//                        totalServices = homeresponse.getData().getTotal_services();
//                        pendingServices = homeresponse.getData().getTotal_pending_services();
//                        activeServices = homeresponse.getData().getTotal_active_services();
//                        completeServices = homeresponse.getData().getTotal_completed_services();
//
//
//
//
//                        if (homeresponse.getData().getIs_vendor_suspend().equalsIgnoreCase("Y")){
//                            ShowSuspendDialog();
//                        }
//
//                        App.getInstance().getSharedpreference().setSERVICES(totalServices,activeServices,pendingServices,completeServices);
//
//
//                        adapter=new ImageAdapter(homeresponse.getData().getCategory_images(),getActivity());
//                        recycleSlide.setAdapter(adapter);
//
//                        final Timer timer = new Timer();
//                        timer.schedule(new TimerTask() {
//
//                            @Override
//                            public void run() {
//
//                                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() < (adapter.getItemCount() - 1)) {
//
//                                    linearLayoutManager.smoothScrollToPosition(recycleSlide, new RecyclerView.State(), linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1);
//                                }
//
//                                else if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (adapter.getItemCount() - 1)) {
//
//                                    linearLayoutManager.smoothScrollToPosition(recycleSlide, new RecyclerView.State(), 0);
//                                }
//                            }
//                        }, 0, 3000);
//
//
//                       /* tService.setText("Your total Services :" + totalServices);
//                        aService.setText("Your active Services :" + activeServices);
//                        pService.setText("Your pending Services :" + pendingServices);
//                        cService.setText("Your complete Services :" + completeServices);*/
//
//                        latitude_stri = sharedPreferences12.getString("LATITUDE", "");
//                        longitude_stri = sharedPreferences12.getString("LONGITUTE", "");
//
//                        Log.d("latitude_stri1:","latitude_str1i:"+latitude_stri);
//                        Log.d("latitude_stri11:","latitude_stri11:"+longitude_stri);
//
//
//
//                        if (homeresponse.getData().getAssigned_services().length!=0){
//
//                            acceptRecycler.setVisibility(View.VISIBLE);
//                            noservice.setVisibility(View.GONE);
////                            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(orderID);
////                            if (databaseReference.child("is_vendor_accepted").equals("C")){
////
////                            }
//
//                            //old code
//                            AcceptServiceAdapter adapter=new AcceptServiceAdapter( getActivity(),homeresponse.getData().getAssigned_services());
//                            acceptRecycler.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//
//
//                        }else{
//                            acceptRecycler.setVisibility(View.GONE);
//                            noservice.setVisibility(View.VISIBLE);
//                            noservice.setText("No Service Request Available !!!");
//                            // Toast.makeText(getActivity(), "No service Request Available !!!", Toast.LENGTH_SHORT).show();
//                        }
//
//
//
//                    } catch (Exception e) {
//                        progressDialog.dismiss();
//                    }
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<HomeNewResponse> call, Throwable t) {
//                progressDialog.dismiss();
//                Log.e("errrrrrrr",t.toString());
//
//            }
//        });
//    }

    private void sendNotification(String message, String title) {


        Intent intent = new Intent(getActivity(), Splash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(getActivity(), channelId)
                        .setSmallIcon(R.drawable.ic_loginn)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        //.setPriority(NotificationManager.IMPORTANCE_LOW)
                        .setSound(null)
                        .setContentIntent(pendingIntent);



        NotificationManager notificationManager =
                 (NotificationManager)getActivity().getSystemService(getActivity().NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Learning",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private int createID() {

        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }

    private void LoginToken(final String ApiName) {

        String mobilestr=App.getInstance().getSharedpreference().getPHONE();
        String pass=App.getInstance().getSharedpreference().getPASSWORD();

        String loginflag="mobile";

        getApiClient().LoginMobile(mobilestr,pass,loginflag).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("homerequest:11","homerequest:11"+new Gson().toJson(response.body()));



                LoginResponse   loginResponse=response.body();

                if (loginResponse!=null && loginResponse.getStatus()!=null){

                    try {

                        if ( loginResponse.getStatus().equalsIgnoreCase("success")){

                            if (loginResponse.getData().getUser()[0]!=null){

                                App.getInstance().getSharedpreference().setTOKEN("Bearer "+loginResponse.getData().getAccess_token());
                                Log.e("sssssssss","Bearer "+loginResponse.getData().getAccess_token());
                                if (ApiName.equalsIgnoreCase("getHomeCards")){
                                    getHomeCards();
                                }else if (ApiName.equalsIgnoreCase("OnOff")){
                                    IsServiceAvailableAPI();
                                }



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

    private void clickEvent() {

        serviceAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                IsServiceAvailableAPI();

            }
        });

    }

    private void IsServiceAvailableAPI() {
        String vendorID=App.getInstance().getSharedpreference().getID();

        if (serviceAvailable.isChecked()){
            OnOff="Y";
            App.getInstance().sharedPreference.setNOTIFICATION("Y");
            sendNotification("Your Service is Available", "DNeeds" );
        }else{
            OnOff="N";
            App.getInstance().sharedPreference.setNOTIFICATION("N");
                NotificationManager nMgr =  ( NotificationManager ) getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE );
                nMgr.cancel(0);

        }
        String token=App.getInstance().getSharedpreference().getTOKEN();


        getApiClient().VendorOnOFF(token,OnOff,vendorID).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("homerequest:111","homerequest:1"+new Gson().toJson(response.body()));


                if (response.code()==401){

                    LoginToken("OnOff");
                }else {


                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

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
