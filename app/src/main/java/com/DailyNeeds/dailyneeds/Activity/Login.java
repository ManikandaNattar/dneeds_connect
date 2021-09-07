package com.DailyNeeds.dailyneeds.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse.LoginResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse.User;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.DailyNeeds.dailyneeds.location.PermissionUtils;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements PermissionUtils.PermissionResultCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private EditText email, password;
    private LinearLayout loginLT;
    private TextView otptv, signupTV,forgetTV;
    private ImageView gps;
    private String otpNumber;
    private  ApiClient mApi;
    private LoginResponse loginResponse;
    private ProgressDialog progressDialog;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    //private TextView vendorTV;
    boolean isPermissionGrantedone;
    private Location mLastLocationone;
    private GoogleApiClient mGoogleApiClient;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    double latitudeone;
    double longitudeone;
    String address="";


    boolean isPermissionGranted;

    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;

    Context context;
    double latitude;
    double longitude;
    private GoogleApiClient mGoogleApiClientone;

    private Location mLastLocation;
    private LocationManager locationManager;
//    TextView signAddr1;
private final int REQUEST_LOCATION = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        progressDialog=new ProgressDialog(this);
//        progressDialog.setMessage("Loading!!!");
//        progressDialog.show();
//        location_detect();

        //tesitng

//        if(checkPlayServices()) {
//            buildGoogleApiClient();
//            location_detect();
//        }

       context = Login.this;
       getLocationpermission();
        initview();
        otpNumber=RandomNumber();
        clickEvents();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading!!!");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        location_detect();



    }

    private void getLocationpermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},  REQUEST_LOCATION);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                location_detect();
            }
        }
    }

    private void location_detect() {

        try {
            getLocation();

            if (mLastLocation != null) {
                Log.e("lastlocation", String.valueOf(mLastLocation.getLatitude()));
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
                getAddress();

            } else {
                getAddress();
                Log.d("", "Couldn't get the location. Make sure location is enabled on the device");
                //showToast("Couldn't get the location. Make sure location is enabled on the device");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Address getAddress(double latitude, double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void getAddress() {

        Address locationAddress = getAddress(latitude, longitude);
        Log.e("addrLat", String.valueOf(latitude));

        if (locationAddress != null) {
             address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);

            Log.e("Addresslane 0:", address + "");
            Log.e("Addresslane 1:", address1 + "");
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();




            final int mid = address.length() / 2; //get the middle of the String
            String[] parts = {address.substring(0, mid),address.substring(mid)};
            System.out.println(parts[0]); //first part
            System.out.println(parts[1]);
//            signAddr1.setVisibility(View.VISIBLE);


//            signAddr1.setText(address);
//            signAddr2.setText(address1);
//            signcity.setText(city);
//            signstate.setText(state);
//            signpin.setText(postalCode);
//            signAddr1.setText(address);
            String currentLocation;

            if (!TextUtils.isEmpty(address)) {
                currentLocation = address;
                //
                // signAddr1.setText(address);

                if (!TextUtils.isEmpty(address1))
                    currentLocation += "\n" + address1;

                if (!TextUtils.isEmpty(city)) {
                    currentLocation += "\n" + city;

                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += " - " + postalCode;
                } else {
                    if (!TextUtils.isEmpty(postalCode))
                        currentLocation += "\n" + postalCode;
                }

                if (!TextUtils.isEmpty(state))
                    currentLocation += "\n" + state;

                if (!TextUtils.isEmpty(country))
                    currentLocation += "\n" + country;



                Log.d("locaa", currentLocation + "");


            }

        }
    }

    private void getLocation() {

        if (isPermissionGranted) {

            try {
                mLastLocation = LocationServices.FusedLocationApi

                        .getLastLocation(mGoogleApiClient);
                Log.d("mLastLocation","mLastLocation"+mLastLocation);
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

    private void buildGoogleApiClient() {

        mGoogleApiClientone = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClientone.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClientone, builder.build());

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
                            status.startResolutionForResult(Login.this, REQUEST_CHECK_SETTINGS);

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



    private void clickEvents() {
//        gps.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                location_detect();
//            }
//        });

        loginLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().toString().trim().length()>11){

                    if (email.getText().toString().trim().length()>5 && password.getText().toString().length()>1){

                        String emailstr=email.getText().toString().trim();
                        String pass=password.getText().toString();

                        String loginflag="";
                        if (email.getText().toString().length()==10){
                            loginflag="mobile";
                        }else if(email.getText().toString().length()>10){
                            loginflag="email";
                        }


                        progressDialog.show();
                        getApiClient().Loginemail(emailstr,pass,loginflag).enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                Log.d("loginresponse:","loginresponse:"+new Gson().toJson(response.body()));


                                    try {


                                        String jsonstr=response.body().toString();
                                        LoginResponse loginResponse =new LoginResponse() ; //initialize the constructor
                                        Gson gson = new Gson();
                                        loginResponse = gson.fromJson(jsonstr,LoginResponse.class );

                                        if ( loginResponse.getStatus().equalsIgnoreCase("success")){
                                            //vendorTV.setVisibility(View.GONE);

                                            if (loginResponse.getData().getUser()[0]!=null){

                                                User user=loginResponse.getData().getUser()[0];
                                                App.getInstance().getSharedpreference().SaveNameReg(user.getFirst_name(),user.getEmail(),user.getMobile_no(),password.getText().toString(),user.getvendor_no());
                                                App.getInstance().getSharedpreference().SaveAddresReg(user.getAddress(),user.getAddress2(),user.getCity(),user.getState(),user.getZipcode(),true);
                                                App.getInstance().getSharedpreference().setTOKEN("Bearer "+loginResponse.getData().getAccess_token());
                                                Log.e("Bearer ",loginResponse.getData().getAccess_token());
                                                App.getInstance().getSharedpreference().setID_TYPE(user.getId(),user.getUsertype_id());
                                                App.getInstance().getSharedpreference().setLATITUDE(user.getLatitude());
                                                App.getInstance().getSharedpreference().setLONGITUTE(user.getLongitude());
                                                App.getInstance().getSharedpreference().setVENDOR(user.getvendor_no());

                                                Log.d("checkone:","checkone:"+user.getvendor_no());

                                                Log.d("loginlat1:","loginlat1:"+user.getLatitude());
                                                Log.d("loginlat11:","loginlat11:"+user.getLongitude());

                                                String latitude_str = user.getLatitude();
                                                String longitude_str = user.getLongitude();

                                                SharedPreferences sharedPreferences11 = context.getSharedPreferences("CURRENTLATELONG", Context.MODE_PRIVATE);
                                                SharedPreferences.Editor editor11 = sharedPreferences11.edit();
                                                editor11.putString("LATITUDE", latitude_str);
                                                editor11.putString("LONGITUTE", longitude_str);
                                                editor11.commit();


                                                sensPUSHTOKEN();

                                            }



                                           /* App.getInstance().getSharedpreference().setISLOGIN(true);
                                            startActivity(new Intent(Login.this, MainActivity.class));
                                            finish();*/


                                            // Toast.makeText(Login.this, loginResponse.getStatus(), Toast.LENGTH_SHORT).show();

                                        }else {

                                            if(loginResponse.getData().getMessage().contains("No user details to retrieve")){
                                                //new code
                                                Toast.makeText(Login.this, getString(R.string.incorrect_pwd), Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                                //new code
                                            }else{
                                                Toast.makeText(Login.this, loginResponse.getData().getMessage(), Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }




                                           // vendorTV.setVisibility(View.VISIBLE);
                                        }


                                    }catch (Exception e){
                                        progressDialog.dismiss();
                                        Log.e("eeeeeee",e.toString());
                                    }



                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Log.e("eeee",t.toString());
                                progressDialog.dismiss();

                            }
                        });
                    }else{
                        Toast.makeText(Login.this, "Enter valid user details or Password !!!", Toast.LENGTH_SHORT).show();
                    }


                }else if (email.getText().toString().trim().length()==10){

                    if (email.getText().toString().trim().length()>5 && password.getText().toString().length()>1){

                        String emailstr=email.getText().toString().trim();
                        String pass=password.getText().toString();

                        String loginflag="";
                        if (email.getText().toString().length()==10){
                            loginflag="mobile";
                        }else if(email.getText().toString().length()>10){
                            loginflag="email";
                        }


                        progressDialog.show();
                        getApiClient().LoginMobile(emailstr,pass,loginflag).enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                                progressDialog.dismiss();
                                loginResponse=response.body();

                                if (loginResponse!=null && loginResponse.getStatus()!=null){

                                    try {

                                        if ( loginResponse.getStatus().equalsIgnoreCase("success")){

                                            if (loginResponse.getData().getUser()[0]!=null){

                                                User user=loginResponse.getData().getUser()[0];
                                                App.getInstance().getSharedpreference().SaveNameReg(user.getFirst_name(),user.getEmail(),user.getMobile_no(),password.getText().toString(),user.getvendor_no());
                                                App.getInstance().getSharedpreference().SaveAddresReg(user.getAddress(),user.getAddress2(),user.getCity(),user.getState(),user.getZipcode(),true);
                                                App.getInstance().getSharedpreference().setTOKEN("Bearer "+loginResponse.getData().getAccess_token());
                                                App.getInstance().getSharedpreference().setID_TYPE(user.getId(),user.getUsertype_id());

                                                if (user.getIs_vendor_suspend().equalsIgnoreCase("Y")){

                                                    Toast.makeText(Login.this, "You are suspended please contact the Admin !!!", Toast.LENGTH_SHORT).show();

                                                }else{

                                                    sensPUSHTOKEN();

                                                }

                                            }





                                        }else {

                                            Toast.makeText(Login.this, loginResponse.getData().getMessage(), Toast.LENGTH_SHORT).show();

                                        }



                                    }catch (Exception e){

                                    }

                                }

                            }

                            @Override
                            public void onFailure(Call<LoginResponse> call, Throwable t) {
                                Log.e("eeee",t.toString());
                                progressDialog.dismiss();

                            }
                        });
                    }else{
                        Toast.makeText(Login.this, "Enter valid user details or Password !!!", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });


        otptv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().length()==10){
                    String phone=email.getText().toString();

                    startActivity(new Intent(Login.this, LoginOtp.class).putExtra("otp",otpNumber).putExtra("phone",phone));
                }else{
                    Toast.makeText(Login.this, "Check your Mobile Number!!", Toast.LENGTH_SHORT).show();
                }



            }
        });

        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location_detect();
               // startActivity(new Intent(Login.this, SignupName.class));
                Intent intent = new Intent(Login.this, SignupName.class);
                intent.putExtra("address",address);
                startActivity(intent);


            }
        });

        forgetTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Login.this,ForgetPassword.class));
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finishAffinity();
        finish();

    }

    private void sensPUSHTOKEN() {

        String user_id=App.getInstance().getSharedpreference().getID();


       String imei_no = Settings.Secure.getString(Login.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        String token=App.getInstance().getSharedpreference().getPUSHTOKEN();
        String Accesstoken=App.getInstance().getSharedpreference().getTOKEN();

        getApiClient().sendPUSHTOKEN(Accesstoken,user_id,imei_no,token).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {


                    JSONObject json = new JSONObject(response.body().toString());
                    if (json.has("status")) {
                        Log.e("pppppp",json.toString());

                        if (json.getString("status").equalsIgnoreCase("success")){
                            progressDialog.dismiss();
                            //old code
                            App.getInstance().getSharedpreference().setISLOGIN(true);
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                            App.getInstance().getSharedpreference().getADDR1();
//                            Intent intent = new Intent(Login.this, SignupName.class);
//                            intent.putExtra("address", signAddr1.getText().toString());
//                            startActivity(intent);
                            //new code
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                                startActivity(new Intent(Login.this, PermissionActivity.class));
//                                finish();
//                            }else {
//                                App.getInstance().getSharedpreference().setISLOGIN(true);
//                                startActivity(new Intent(Login.this, MainActivity.class));
//                                finish();
//                            }

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

    private void initview() {

        email = findViewById(R.id.email);
//        signAddr1 = findViewById(R.id.signAddr1);
        password = findViewById(R.id.password);
        loginLT = findViewById(R.id.loginLT);
        otptv = findViewById(R.id.otptv);
        signupTV = findViewById(R.id.signupTV);
        forgetTV = findViewById(R.id.forgetTV);
        gps = findViewById(R.id.gps);
        //vendorTV = findViewById(R.id.vendorTV);
        otptv.setVisibility(View.GONE);
//        permissionUtils = new PermissionUtils(Login.this);
//        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
//        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);

    }

    private String RandomNumber() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);

    }

    private ApiClient getApiClient(){
        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

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
    public void onLocationChanged(Location location) {
        Log.e("currentlocation", String.valueOf(location.getLatitude()));
        latitude = location.getLatitude();
        longitude = location.getLongitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
