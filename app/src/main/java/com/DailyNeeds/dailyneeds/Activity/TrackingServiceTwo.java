package com.DailyNeeds.dailyneeds.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.Adapter.SubServiceAdapter;
import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.LocationUpdate.BackgroundLocationUpdateService;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.CompleteOrderRes.CompleteOrderRes;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse.LoginResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes.Assigned_services;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.DailyNeeds.dailyneeds.Twilio.CustomDeviceActivity;
import com.DailyNeeds.dailyneeds.location.PermissionUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackingServiceTwo extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, PermissionUtils.PermissionResultCallback {

    private StateProgressBar stateProgressBar;
    private RecyclerView recycleSubService;
    private Assigned_services assigned_services;
    private SubServiceAdapter adapter;
    String[] descriptionData = {"User Request", "Approved","Completed"};
    private ImageView Back;
    private ApiClient mApi;
    private LinearLayout CompleteLT,cancelServcieLT;
    private String orderID,vendorID;
    private TextView successfulTV;
    private ProgressDialog progressDialog;
    private Location mLastLocation;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    double latitude;
    double longitude;

    boolean isPermissionGranted;
    private GoogleApiClient mGoogleApiClient;
    private ImageView callIV;
    private ImageView trackTV;
    private String mobile="";
    private FloatingActionButton chatFab;
    private TextView username,useraddress,userpincode;
    private LinearLayout chatLT;
    private WebView trackChat;
    private DatabaseReference databaseReference;

    @Override
    protected void onResume() {
        super.onResume();
        if (isPermissionGranted){

            startService(new Intent(this, BackgroundLocationUpdateService.class).putExtra("orderID",orderID));

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //old code
        startActivity(new Intent(TrackingServiceTwo.this,MainActivity.class));
        //old code
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracking_service_two);
        stateProgressBar = findViewById(R.id.your_state_progress_bar_id);
        username=findViewById(R.id.username);
        useraddress=findViewById(R.id.useraddress);
        userpincode=findViewById(R.id.userpincode);
        chatLT=findViewById(R.id.chatLT);
        trackChat=findViewById(R.id.trackChat);
        recycleSubService = findViewById(R.id.recycleSubService);
        Back =findViewById(R.id.Back);
        trackTV =findViewById(R.id.track);
        callIV =findViewById(R.id.call);
        chatFab =findViewById(R.id.chatFab);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        recycleSubService.setLayoutManager(layoutManager);
        stateProgressBar.setStateDescriptionData(descriptionData);
        CompleteLT=findViewById(R.id.CompleteLT);
        cancelServcieLT=findViewById(R.id.cancelServcieLT);
        successfulTV=findViewById(R.id.successfulTV);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading!!!");
        location_detect();
        permissionUtils = new PermissionUtils(TrackingServiceTwo.this);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);




        try {


            if (getIntent().getStringExtra("model")!=null){
                Gson gson=new Gson();

                String gresponse=getIntent().getStringExtra("model");
                assigned_services =gson.fromJson(gresponse,Assigned_services.class);
                if (assigned_services.getSub_services().length!=0){
                    adapter=new SubServiceAdapter(this, assigned_services.getSub_services());
                    recycleSubService.setAdapter(adapter);
                }

                String name="<b>Name : </b>"+ assigned_services.getUser_first_name();
                username.setText(Html.fromHtml(name));
                useraddress.setText(Html.fromHtml("<b>Address : </b>"+assigned_services.getAddress()));
                userpincode.setText(Html.fromHtml("<b>Pincode : </b>"+assigned_services.getZipcode()));

                mobile=assigned_services.getMobile_no();
                orderID=assigned_services.getOrder_id();
                vendorID= App.getInstance().getSharedpreference().getID();
                if (assigned_services.getChat_room_id()==null || assigned_services.getChat_room_id().isEmpty() ){
                    chatFab.setVisibility(View.GONE);
                    chatLT.setVisibility(View.GONE);
                }

            }


        }catch (Exception e){

        }

        TrackingServiceTwo.this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        final String name = App.getInstance().getSharedpreference().getFIRSTNAME();
        final String chatid = assigned_services.getChat_room_id();

        //new code
        final String orderid = assigned_services.getOrder_id();
        final String vendorid = assigned_services.getId();
        final String userid = assigned_services.getUser_id();
        final String service_id = assigned_services.getService_id();

        Log.d("servicefname:","servicefname:"+assigned_services.getUser_first_name());
        Log.d("servicefname:1","servicefname:1"+name);
        Log.d("servicechatid:","servicechatid:"+assigned_services.getChat_room_id());
        Log.d("servicechatid:1","servicechatid:1"+chatid);
        Log.d("servicevendorid:","servicevendorid:"+assigned_services.getId());
        Log.d("serviceorderid:","serviceorderid:"+assigned_services.getOrder_id());
        Log.d("servicevendorid:","servicevendorid:"+assigned_services.getId());
        Log.d("serviceuserid:","serviceuserid:"+assigned_services.getUser_id());
        Log.d("serviceuserid:","serviceuserid:"+assigned_services.getService_id());



        trackChat.getSettings().setJavaScriptEnabled(true);
        trackChat.loadUrl("https://dneeds.in/chat-test");

        trackChat.getSettings().setDomStorageEnabled(true);
        trackChat.requestFocus(View.FOCUS_DOWN);

        trackChat.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus()) {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });

        trackChat.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //  trackChat.loadUrl("javascript: sessionStorage.setItem('chat_room',"+chatid+");");
                // Log.e("uuu","javascript: sessionStorage.setItem('chat_room','"+chatid+"');");

                trackChat.loadUrl("javascript: sessionStorage.setItem('chat_room','"+chatid+"');");
                trackChat.loadUrl("javascript: sessionStorage.setItem('first_name','"+name+"');");

                //new code
                trackChat.loadUrl("javascript: sessionStorage.setItem('order_id','"+orderid+"');");
                trackChat.loadUrl("javascript: sessionStorage.setItem('vendor_id','"+vendorid+"');");
                trackChat.loadUrl("javascript: sessionStorage.setItem('user_id','"+userid+"');");
                //newly added for chat notification issue fixes
                trackChat.loadUrl("javascript: sessionStorage.setItem('service_id','"+service_id+"');");



                // trackChat.loadUrl("javascript: sessionStorage.setItem('chat_room', b8eccac8685796b93e6741478f9457b3);");

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });




        chatFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //old code
//                startActivity(new Intent(Tracking_service.this,ChatActivity.class).putExtra("name",name)
//                .putExtra("chatid",chatid));
                //new code
                startActivity(new Intent(TrackingServiceTwo.this,ChatActivity.class).putExtra("name",name)
                        .putExtra("chatid",chatid).putExtra("order_id",orderid).putExtra("vendor_id",vendorid).putExtra("user_id",userid));
            }
        });


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //old code
                startActivity(new Intent(TrackingServiceTwo.this,MainActivity.class));
                //old code
                finish();
            }
        });


        cancelServcieLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();

                cancelserviceAPI();


            }
        });

        CompleteLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CompleteService();
            }
        });

        callIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrackingServiceTwo.this, CustomDeviceActivity.class).putExtra("mobile",mobile));
            }
        });

        //

        if (assigned_services.getIs_order_completed().equalsIgnoreCase("Y")){

            stateProgressBar.setAllStatesCompleted(true);
            CompleteLT.setVisibility(View.GONE);
            //old code
            //chatFab.setVisibility(View.GONE);
            //new code
            chatFab.setVisibility(View.GONE);
            chatLT.setVisibility(View.GONE);
            successfulTV.setVisibility(View.VISIBLE);
            callIV.setVisibility(View.GONE);
            trackTV.setVisibility(View.GONE);
            stopService(new Intent(TrackingServiceTwo.this, BackgroundLocationUpdateService.class));
        }



        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        trackTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_detect();
/*
                Intent notificationIntent = new Intent(Tracking_service.this, LocationUpdate.class);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,0, 10000,pendingIntent);*/
                try {

                    String latitude2=assigned_services.getLat();
                    String longitude2=assigned_services.getLon();

                    Log.d("mapmap111:","mapmap111:"+latitude2);
                    Log.d("mapmap1111:","mapmap1111:"+longitude2);

                    String uri = "http://maps.google.com/maps?f=d&hl=en&saddr="+latitude+","+longitude+"&daddr="+latitude2+","+longitude2;

                    Log.d("mapmap11111:","mapmap11111:"+uri);

                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(Intent.createChooser(intent, "Select an application"));


                }catch (ActivityNotFoundException e){

                    Uri uri=Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
                    Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }


        });
    }

    private void cancelserviceAPI() {


        final String vendorID=App.getInstance().getSharedpreference().getID();
        String token= App.getInstance().getSharedpreference().getTOKEN();
        progressDialog.show();
        getApiClient().RejectService(token,orderID,vendorID).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.body()!=null)
                    Log.e("ress",response.body().toString());
                if (response.code()==401){
                    LoginToken("cancelService");

                }else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("user").child(orderID);
                    databaseReference.child("is_vendor_accepted").setValue("V");
                    databaseReference.child("is_order_placed").setValue("Y");
                    //new code
                    databaseReference.child("order_id").setValue(orderID);
                    final String userid = assigned_services.getUser_id();
                    databaseReference.child("user_id").setValue(userid);
                    databaseReference.child("shownPopup").setValue("0");
                    //new code
                    Log.d("oneoneone111:","oneoneone111:"+orderID+" / "+userid);
                    onBackPressed();
                    progressDialog.dismiss();

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();

            }
        });




    }

    private void buildGoogleApiClient() {


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(TrackingServiceTwo.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        location_detect();
        super.onStart();
    }

    private void location_detect() {

        try {
            getLocation();

            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
                //   startService(new Intent(this, BackgroundLocationUpdateService.class).putExtra("orderID",orderID));

                Log.d("mapmap1:","mapmap1:"+latitude);
                Log.d("mapmap11:","mapmap11:"+longitude);


            } else {

                Log.d("", "Couldn't get the location. Make sure location is enabled on the device");
                //showToast("Couldn't get the location. Make sure location is enabled on the device");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLocation() {

        if (isPermissionGranted) {

            try {
                //old code
//                mLastLocation = LocationServices.FusedLocationApi
//                        .getLastLocation(mGoogleApiClient);

                //new code
                FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

                fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                    if (location != null) {
                        // Logic to handle location object
                        mLastLocation = location;
                    } else {
                        // Handle null case or Request periodic location update https://developer.android.com/training/location/receive-location-updates
                    }
                });


            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }


    }

    private boolean checkPlayServices() {


        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
            }
            return false;
        }
        return true;

    }

    private void CompleteService() {
        progressDialog.show();
        String token=App.getInstance().getSharedpreference().getTOKEN();
        getApiClient().CompleteService(token,orderID,vendorID).enqueue(new Callback<CompleteOrderRes>() {
            @Override
            public void onResponse(Call<CompleteOrderRes> call, Response<CompleteOrderRes> response) {

                progressDialog.dismiss();
                if (response.code()==401){
                    LoginToken("CompleteService");
                }else {


                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        stateProgressBar.setAllStatesCompleted(true);
                        CompleteLT.setVisibility(View.GONE);
                        //old code
                        //chatFab.setVisibility(View.GONE);
                        //new code
                        chatFab.setVisibility(View.GONE);
                        chatLT.setVisibility(View.GONE);
                        trackTV.setVisibility(View.GONE);
                        callIV.setVisibility(View.GONE);
                        successfulTV.setVisibility(View.VISIBLE);
                        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(orderID);
                        databaseReference.child("is_vendor_accepted").setValue("D");

                        //new code
                        databaseReference.child("order_id").setValue(orderID);
                        final String userid = assigned_services.getUser_id();
                        databaseReference.child("user_id").setValue(userid);
                        databaseReference.child("shownPopup").setValue("0");
                        //new code

                        Log.d("oneoneone1111:","oneoneone1111:"+orderID+" / "+userid);

                        stopService(new Intent(TrackingServiceTwo.this, BackgroundLocationUpdateService.class));
                        Toast.makeText(TrackingServiceTwo.this, "Service Completed Successfully !!!", Toast.LENGTH_SHORT).show();


                    }

                }

            }

            @Override
            public void onFailure(Call<CompleteOrderRes> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void LoginToken(String apiDetail) {

        String mobileStr=App.getInstance().getSharedpreference().getPHONE();
        String pass=App.getInstance().getSharedpreference().getPASSWORD();

        String loginflag="mobile";

        getApiClient().LoginMobile(mobileStr,pass,loginflag).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                LoginResponse   loginResponse=response.body();

                if (loginResponse!=null && loginResponse.getStatus()!=null){

                    try {

                        if ( loginResponse.getStatus().equalsIgnoreCase("success")){

                            if (loginResponse.getData().getUser()[0]!=null){

                                App.getInstance().getSharedpreference().setTOKEN("Bearer "+loginResponse.getData().getAccess_token());
                                Log.e("sssssssss","Bearer "+loginResponse.getData().getAccess_token());
                                if (apiDetail.equalsIgnoreCase("CompleteService")){
                                    CompleteService();
                                }else if (apiDetail.equalsIgnoreCase("cancelService")){
                                    cancelserviceAPI();
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

    private ApiClient getApiClient(){
        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
    }

    @Override
    public void PermissionGranted(int request_code) {
        isPermissionGranted = true;
    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
