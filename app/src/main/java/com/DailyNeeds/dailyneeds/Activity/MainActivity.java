package com.DailyNeeds.dailyneeds.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.Manifest;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.Adapter.NewServiceRecycleAdapter;
import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.Fragment.MyServiceFrag;
import com.DailyNeeds.dailyneeds.Fragment.HomeFrag;
import com.DailyNeeds.dailyneeds.Fragment.Services;
import com.DailyNeeds.dailyneeds.Fragment.ProfileFrag;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.HomeResponse.Assigned_services;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.UserDetail.UserDetail;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes.GetAllVendorService;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes.VendorServiceOff;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.DailyNeeds.dailyneeds.Service.FirebasePushNotificationService;
import com.DailyNeeds.dailyneeds.location.PermissionUtils;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,PermissionUtils.PermissionResultCallback {

    BottomNavigationView navigationView;
    private FrameLayout frameLayout;
    NavigationView navigationDrawer;
    TextView order_Counts,dname,demail,dvendorno;
     private View headerview;
    public static final String NOTIFY_ACTIVITY_ACTION = "notify_activity";
    private BroadcastReceiver broadcastReciver;

    private Location mLastLocation;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private ApiClient mApi;
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    double latitude;
    double longitude;
    boolean isPermissionGranted;
    private GoogleApiClient mGoogleApiClient;
    private UserDetail userDetail;
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    ImageView logout;

    String distanceValue,durationValue;

    Context context;

    private static final int REQUEST_LOCATION = 1;
    Button btnGetLocation;
    TextView showLocation;
    LocationManager locationManager;
    String latitude1, longitude1;


    @Override
    protected void onStart() {
        super.onStart();

        location_detect();

    }

    public class getDistancenDirection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection mUrlConnection = null;
            StringBuilder mJsonResults = new StringBuilder();
            try {
                String distUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + 9.9252 + "," + 78.1198 + "&destinations=" + 9.8433 + "," + 78.4809 + "&sensor=false&mode=driving&key="+
                        "AIzaSyAGI9aRbEg4DcjOdZN42o4gmZurNOFNZOo";

                Log.v("mapdirectionurl","url="+distUrl);

                URL url = new URL(distUrl);
                mUrlConnection = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(mUrlConnection.getInputStream());


                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    mJsonResults.append(buff, 0, read);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } finally {
                if (mUrlConnection != null) {
                    mUrlConnection.disconnect();
                }
            }

            Log.v("directionresult","result="+ mJsonResults.toString());
            try {
                JSONObject jsonObject = new JSONObject(mJsonResults.toString());
                JSONArray rows = jsonObject.optJSONArray("rows");
                if (rows != null) {
                    for (int i = 0; i < rows.length(); i++) {
                        JSONObject rowObj = rows.getJSONObject(i);
                        JSONArray elements = rowObj.getJSONArray("elements");
                        for (int j = 0; j < elements.length(); j++) {
                            JSONObject eleObj = elements.getJSONObject(j);
                            JSONObject distance = eleObj.getJSONObject("distance");
                            distanceValue = distance.getString("text");
                            JSONObject duration = eleObj.getJSONObject("duration");
                            durationValue = duration.getString("text");
                            break;
                        }
                        break;
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            // String distance = response.replace(" km", "");
//            if (from == null)
//                getRides(response);
//            distancetxt.setText(distanceValue + "(" + durationValue + ")");

            String distanceValue_str = distanceValue;
            String durationValue_str = durationValue;
            Log.d("distanceValue_str:1","distanceValue_str:1"+distanceValue_str);
            Log.d("durationValue_str:1","durationValue_str:1"+durationValue_str);
            SharedPreferences sharedPreferencesOne = context.getSharedPreferences("DISTANCEANDDURATION", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor102 = sharedPreferencesOne.edit();
            editor102.putString("DISTANCE", distanceValue_str);
            editor102.putString("DURATION", durationValue_str);
            editor102.commit();



        }
    }

    private void location_detect() {

        try {
            getLocation();

            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();

                Log.d("latitude:","latitude:"+String.valueOf(latitude));
                Log.d("latitude:1","latitude:1"+String.valueOf(longitude));



            } else {

                Log.d("", "Couldn't get the location. Make sure location is enabled on the device");
                //showToast("Couldn't get the location. Make sure location is enabled on the device");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReciver);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finish();

    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

//        new getDistancenDirection().execute();

        initViews();

        getUserDetails();

        //new code
//        getOneService();
        //new code



        // check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        // define your fragments here
        final FragmentManager fragmentManager = getSupportFragmentManager();

        final HomeFrag homeFrag = new HomeFrag();
        final Services services = new Services();
        final MyServiceFrag chatFrag = new MyServiceFrag();
        final ProfileFrag profileFrag = new ProfileFrag();

        /* BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // Extract data included in the Intent
                String message = intent.getStringExtra("message");

                //do other stuff here
            });*/

        broadcastReciver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getStringExtra(MainActivity.NOTIFY_ACTIVITY_ACTION)!=null){

                    String message = intent.getStringExtra((MainActivity.NOTIFY_ACTIVITY_ACTION));
                    Log.e("mmm",message);
                    final FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
                    final HomeFrag homeFrag = new HomeFrag();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout, homeFrag).commit();

                }
            }
        };

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                  Fragment fragment=null;
                switch (item.getItemId()) {
                    case R.id.home:
                        // do something here
                        fragment=homeFrag;
                        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
                        return true;
                    case R.id.order:
                        // do something here
                        fragment= services;
                        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
                        return true;
                    case R.id.chat:
                        // do something here
                        fragment=chatFrag;
                        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
                        return true;

                    case R.id.profile:
                        // do something here
                        fragment=profileFrag;
                        fragmentManager.beginTransaction().replace(R.id.frame_layout, fragment).commit();
                        return true;


                }


                return true;

            }





        });
        navigationView.setSelectedItemId(R.id.home);

                navigationDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();


                if (id == R.id.about) {

                    if (App.getInstance().IsInternetAvailable()){
                        startActivity(new Intent(MainActivity.this,Webviewload.class).putExtra("headertitle","About")
                                .putExtra("url","https://dneeds.in/about.html"));

                    }else{
                        Toast.makeText(MainActivity.this, "Check Your Internet Connection!!", Toast.LENGTH_SHORT).show();
                    }


                } else if (id == R.id.terms) {

                    if (App.getInstance().IsInternetAvailable()){

                        startActivity(new Intent(MainActivity.this,Webviewload.class).putExtra("headertitle","Terms and Condition")
                                .putExtra("url","https://dneeds.in/terms_and_conditions.html"));

                    }else{
                        Toast.makeText(MainActivity.this, "Check Your Internet Connection!!", Toast.LENGTH_SHORT).show();
                    }


                } else if (id == R.id.policy) {

                    if (App.getInstance().IsInternetAvailable()){

                        startActivity(new Intent(MainActivity.this,Webviewload.class).putExtra("headertitle","Privacy and Policy")
                                .putExtra("url","https://dneeds.in/privacy_policy.html"));

                    }else{
                        Toast.makeText(MainActivity.this, "Check Your Internet Connection!!", Toast.LENGTH_SHORT).show();
                    }

                } else if (id == R.id.faq) {

                    if (App.getInstance().IsInternetAvailable()){

                        startActivity(new Intent(MainActivity.this,Webviewload.class).putExtra("headertitle","FAQ")
                                .putExtra("url","https://dneeds.in/blog.html"));


                    }else{
                        Toast.makeText(MainActivity.this, "Check Your Internet Connection!!", Toast.LENGTH_SHORT).show();
                    }



                } else if (id == R.id.supportmobile) {


                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:7358371402"));
                    startActivity(intent);

                } else if (id == R.id.supportemail) {

                    Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Subject of email");
                    intent.putExtra(Intent.EXTRA_TEXT, "Body of email");
                    intent.setData(Uri.parse("mailto:support-complaints@dneeds.in")); // or just "mailto:" for blank
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    MainActivity.this.startActivity(intent);


                }else if (id== R.id.rateus1){


                        Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.DailyNeeds.dailyneeds"); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);



                }else if ( id == R.id.feedback){


                    startActivity(new Intent(MainActivity.this,Rating.class));

                }else if ( id == R.id.activities){


                    startActivity(new Intent(MainActivity.this,ServiceActivities.class));

                }else if ( id == R.id.changePWD){


                    startActivity(new Intent(MainActivity.this,ChangePassword.class));

                }

                return false;
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure you want to Logout ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                venderServiceOff();


                                startActivity(new Intent(MainActivity.this,Login.class));
                                App.getInstance().getSharedpreference().setISLOGIN(false);
                                //dApp.getInstance().getSharedpreference().ClearPreference();
                                finish();
                                NotificationManager nMgr =  ( NotificationManager ) getSystemService( MainActivity.this.NOTIFICATION_SERVICE );
                                nMgr.cancel(0);

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

            }
        });




    }

    private void getUserDetails() {

//        progressDialog.show();
        String userid = App.getInstance().sharedPreference.getID();
        String Accesstoken = App.getInstance().getSharedpreference().getTOKEN();

        getApiClient().getUserDetails(Accesstoken, userid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                progressDialog.dismiss();

                if (response.code() == 401) {
//                    LoginToken("get");
                } else {
                    if (response.body() != null)
                        Log.e("userdetail", response.body().toString());

                    final String body = response.body().toString(); // Or use response.raw(), if want raw response string
                    JsonParser jsonParser = new JsonParser();
                    JsonObject res = jsonParser.parse(body).getAsJsonObject();


                    Gson gson = new Gson();

                    //homeresponse = gson.fromJson(jsonstr,HomeResponse.class );
                    String mJsonString = res.toString();
                    JsonParser parser = new JsonParser();
                    JsonElement mJson = parser.parse(mJsonString);
                    userDetail = gson.fromJson(mJson, UserDetail.class);
//
//                    App.getInstance().sharedPreference.setEMAIL(userDetail.getData().getUser_details()[0].getEmail());
//                    App.getInstance().sharedPreference.setVENDOR(userDetail.getData().getUser_details()[0].getvendor_no());
//                    vidEdt.setText(userDetail.getData().getUser_details()[0].getvendor_no());
//                    nameEdt.setText(userDetail.getData().getUser_details()[0].getFirst_name());
//                    emailEdt.setText(userDetail.getData().getUser_details()[0].getEmail());
//                    poneEdt.setText(userDetail.getData().getUser_details()[0].getMobile_no());
//                    addr1Edt.setText(userDetail.getData().getUser_details()[0].getAddress());
//                    addr2Edt.setText(userDetail.getData().getUser_details()[0].getAddress2());
//                    cityEdt.setText(userDetail.getData().getUser_details()[0].getCity());
//                    stateEdt.setText(userDetail.getData().getUser_details()[0].getState());
//                    pincodeEdt.setText(userDetail.getData().getUser_details()[0].getZipcode());
                    dvendorno.setText(userDetail.getData().getUser_details()[0].getvendor_no());


                    String baseurl="https://dneeds.in/dailyneeds/";
                    String finalurl  =baseurl+userDetail.getData().getUser_details()[0].getUser_profile_url();
                    Log.e("finalurl", finalurl);
//                    ImageView image_url=view.findViewById(R.id.imgProfile);
//                    Glide.with(getContext())
//                            .load(finalurl)
//                            .centerCrop()
//                            .placeholder(R.drawable.ic_baseline_account_circle_24)
//                            .into(image_url);

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

//                progressDialog.dismiss();

            }
        });


    }


//    private void  getOneService() {
////        progressDialog=new ProgressDialog(this);
////        progressDialog.setMessage("Loading!!!");
////        progressDialog.show();
//        String token=App.getInstance().getSharedpreference().getTOKEN();
//        String vendorID= App.getInstance().getSharedpreference().getID();
//
//        Log.d("121:","121:"+token);
//        Log.d("121:","121:"+vendorID);
//
//        getApiClient().getAllServicesById(token,vendorID,"64").enqueue(new Callback<Assigned_services>() {
//            @Override
//            public void onResponse(Call<Assigned_services> call, Response<Assigned_services> response) {
//
//                Log.d("serviceidone:","serviceidone:"+new Gson().toJson(response.body()));
//
//                Toast.makeText(MainActivity.this, "serviceidone", Toast.LENGTH_SHORT).show();
//
////                progressDialog.dismiss();
//
////                if (response.code()==401){
////                    LoginToken("getAllServices");
////                }else {
//                try {
//
//                    Assigned_services servicesone = response.body();
//                    if (servicesone != null) {
//                        Gson gson = new Gson();
//
//                        String myJson = gson.toJson(servicesone);
//                            Intent intent = new Intent(MainActivity.this,Tracking_service.class);
//                            intent.putExtra("model",myJson);
//                            startActivity(intent);
//                    }else{
//
//                    }
//
//                } catch (Exception e) {
////                        progressDialog.dismiss();
//                    Log.e("eeeeeeeee", e.toString());
//
//                }
//
//            }
////            }
//
//            @Override
//            public void onFailure(Call<Assigned_services> call, Throwable t) {
////                progressDialog.dismiss();
//                Log.e("eeeeeeeee",t.toString());
//
//            }
//        });
//
//
//
//    }

    private void OnGPS() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocationone() {
        if (ActivityCompat.checkSelfPermission(
                MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude1 = String.valueOf(lat);
                longitude1 = String.valueOf(longi);
//                showLocation.setText("Your Location: " + "\n" + "Latitude: " + latitude1 + "\n" + "Longitude: " + longitude1);
                Log.d("Your Location: ","Your Location: " + "\n" + "Latitude: " + latitude1 + "\n" + "Longitude: " + longitude1);


                SharedPreferences sharedPreferences11 = context.getSharedPreferences("CURRENTLATELONG", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor11 = sharedPreferences11.edit();
                editor11.putString("LATITUDE", latitude1);
                editor11.putString("LONGITUTE", longitude1);
                editor11.commit();


            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void venderServiceOff() {
            String vendorID= App.getInstance().getSharedpreference().getID();


        Log.d("success:","success:0"+vendorID);
            getApiClient().getVenderServiceOff(vendorID).enqueue(new Callback<VendorServiceOff>() {
                @Override
                public void onResponse(Call<VendorServiceOff> call, Response<VendorServiceOff> response) {
                    try {
                        if (response.body().getStatus().equalsIgnoreCase("success")){

                            Log.d("success:","success:1");

//                            Toast.makeText(MainActivity.this, "VendorServiceOff_sucess", Toast.LENGTH_SHORT).show();

                        }else{
//                            Toast.makeText(MainActivity.this, "VendorServiceOff_failue", Toast.LENGTH_SHORT).show();
//                            Log.d("success:","success:2");

                        }

                    }catch (Exception e){
//                        Log.d("success:","success:3");

                    }
                }

                @Override
                public void onFailure(Call<VendorServiceOff> call, Throwable t) {
//                    Log.d("success:","success:4");
                }
            });

    }

    private ApiClient getApiClient(){
        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
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
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;

                     case LocationSettingsStatusCodes.CANCELED:
                         Log.e("ccccc","ccccc");
                        break;
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==0){
            buildGoogleApiClient();
        }


    }

    private void getLocation() {

        if (isPermissionGranted) {

            try {
                mLastLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient);
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

    private void initViews() {

        permissionUtils = new PermissionUtils(this);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);


        logout=findViewById(R.id.logout);
        navigationDrawer=findViewById(R.id.nav_view);
        headerview=navigationDrawer.getHeaderView(0);
        dname=headerview.findViewById(R.id.dname);
        demail=headerview.findViewById(R.id.demail);
        dvendorno=headerview.findViewById(R.id.dvendorId);
        navigationView = findViewById(R.id.bottom_navigation);
        frameLayout=findViewById(R.id.frame_layout);

        frameLayout=navigationView.findViewById(R.id.frame_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        dname.setText(App.getInstance().getSharedpreference().getFIRSTNAME());
        demail.setText(App.getInstance().getSharedpreference().getEMAIL());
        Log.e("demail","demail"+demail.getText());
        dvendorno.setText(App.getInstance().getSharedpreference().getVENDOR());
        Log.e("dvendorno","dvendorno"+dvendorno.getText());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.this.registerReceiver(broadcastReciver, new IntentFilter(MainActivity.NOTIFY_ACTIVITY_ACTION));

//        ActivityCompat.requestPermissions( this,
//                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            OnGPS();
//        } else {
//            getLocationone();
//        }

    }

    @Override
    public void PermissionGranted(int request_code) {
        Log.i("PERMISSION", "GRANTED");
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
