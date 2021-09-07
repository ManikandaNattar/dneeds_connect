package com.DailyNeeds.dailyneeds.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse.LoginResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.SignupResponse.SignupResponseModel;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.SignupResponse.User_details;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signupaddress extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,PermissionUtils.PermissionResultCallback, LocationListener {


    private EditText signAddr1, signAddr2, signcity, signstate, signpin,companyEdt,gstNumberEdt;
    private ImageView gps;
    private LinearLayout signupLt;
    private TextView loginTv, termsTV;
    private CheckBox check;
    private ImageView back;
    private Location mLastLocation;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private  ApiClient mApi;
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    double latitude;
    double longitude;

    boolean isPermissionGranted;
    private GoogleApiClient mGoogleApiClient;

    private SignupResponseModel responseModel;

    private ProgressDialog progressDialog;
    private LocationManager locationManager;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupaddress);
        initviews();
        clickEvents();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading!!!");

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        signAddr1.setText(address);
        // check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
            location_detect();
        }
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
                            status.startResolutionForResult(Signupaddress.this, REQUEST_CHECK_SETTINGS);

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


    @Override
    protected void onStart() {
        location_detect();
        super.onStart();
    }

    private void clickEvents() {



        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Signupaddress.this, "Accepted Terms and Condition !!", Toast.LENGTH_SHORT).show();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // startActivity(new Intent(Signupaddress.this, SignupName.class));
               finish();
            }
        });

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                location_detect();
            }
        });

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Signupaddress.this, Login.class));
                finish();
            }
        });

        signupLt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String regex = "^[0-9]{2}[A-Z]{5}[0-9]{4}"
                        + "[A-Z]{1}[1-9A-Z]{1}"
                        + "Z[0-9A-Z]{1}$";

                Pattern p = Pattern.compile(regex);

                Matcher m = p.matcher(gstNumberEdt.getText().toString());

                //old code
//                if (signAddr1.getText().toString().equals("")) {
//                    Toast.makeText(Signupaddress.this, " Address cannot be empty", Toast.LENGTH_SHORT).show();
//                } else if (signAddr2.getText().toString().equals("")) {
//                    Toast.makeText(Signupaddress.this, " Address 2 cannot be empty", Toast.LENGTH_SHORT).show();
//                }
                //old code


                //new code
                if (signAddr1.getText().toString().equals("")) {
                    Toast.makeText(Signupaddress.this, " Address cannot be empty", Toast.LENGTH_SHORT).show();
                }else if (signcity.getText().toString().trim().equals("")) {
                    Toast.makeText(Signupaddress.this, "City cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (signstate.getText().toString().equals("")) {
                    Toast.makeText(Signupaddress.this, " State cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (signpin.getText().toString().equals("")) {
                    Toast.makeText(Signupaddress.this, " Pin cannot be empty", Toast.LENGTH_SHORT).show();
                } else if(!check.isChecked()) {
                    Toast.makeText(Signupaddress.this, "Accept the terms and condition", Toast.LENGTH_SHORT).show();
                }else if(!m.matches()) {
                    Toast.makeText(Signupaddress.this, "Enter the valid gst number !! ", Toast.LENGTH_SHORT).show();
                }else
                    {
                    progressDialog.show();

                    String addr1=signAddr1.getText().toString();
                    String addr2=signAddr2.getText().toString();
                    String city=signcity.getText().toString();
                    String state=signstate.getText().toString();
                    String pincode=signpin.getText().toString();

                    String addr=addr1+","+city+","+state+","+pincode;
                    Geocoder coder = new Geocoder(Signupaddress.this);
                    List<Address> address;

                    try {
                        // May throw an IOException
                        address = coder.getFromLocationName(addr, 5);


                        Address location = address.get(0);
                        latitude=  location.getLatitude();
                        longitude=  location.getLongitude();

                    } catch (IOException ex) {
                        progressDialog.cancel();
                        ex.printStackTrace();
                    }


                    String userid="2";
                    String name=App.getInstance().getSharedpreference().getFIRSTNAME();
                    String email =App.getInstance().sharedPreference.getEMAIL();
                    String mobile=App.getInstance().getSharedpreference().getPHONE();
                    String password=App.getInstance().getSharedpreference().getPASSWORD();



                    String socialuser="0";
                    String userpaidtype="1";
                    String license="1";
                    String approved="1";
                    String userlegal="1";
                    String rating ="3";
                    String countryCode="91";
                    String authCode="0";
                    String lat= String.valueOf(latitude);
                    String longit= String.valueOf(longitude);
                    String comanyName=companyEdt.getText().toString();
                    String gstNo=gstNumberEdt.getText().toString();

                    Log.e("latt",lat);
                    Log.e("lang",longit);

                    if (lat.length()>4){

                        progressDialog.show();
                        getApiClient().Signup(userid,email,password,mobile,name,addr1,addr2,city,state,pincode,socialuser,userpaidtype,license,approved,userlegal,rating,countryCode,authCode,lat,longit,comanyName,gstNo).enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                Log.d("response45:","response45:"+new Gson().toJson(response.body()));



                                 progressDialog.dismiss();

                                JSONObject json = null;
                                try {

                                    json = new JSONObject(response.body().toString());
                                    String jsonstr=response.body().toString();
                                    SignupResponseModel responseModel=new SignupResponseModel();
                                    Gson gson = new Gson();
                                    responseModel = gson.fromJson(jsonstr,SignupResponseModel.class );

                                    if (responseModel!=null){

                                        try {

                                            if (responseModel.getStatus().equalsIgnoreCase("success")){


                                                User_details userDetail=responseModel.getData().getUser_details()[0];
                                                App.getInstance().getSharedpreference().SaveNameReg(userDetail.getFirst_name(),userDetail.getEmail(),userDetail.getMobile_no(),"",userDetail.getvendor_no());
                                                App.getInstance().getSharedpreference().SaveAddresReg(userDetail.getAddress(),userDetail.getAddress2(),userDetail.getCity(),userDetail.getState(),userDetail.getZipcode(),false);
                                                App.getInstance().getSharedpreference().setTOKEN("Bearer "+responseModel.getData().getAccess_token());
                                                App.getInstance().getSharedpreference().setID_TYPE(userDetail.getId(),userDetail.getUsertype_id());
                                                App.getInstance().getSharedpreference().setLATITUDE(userDetail.getLatitude());
                                                App.getInstance().getSharedpreference().setLONGITUTE(userDetail.getLongitude());
                                                sensPUSHTOKEN();
                                            }else{
                                                Toast.makeText(Signupaddress.this, responseModel.getData().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }catch (Exception e){


                                        }


                                    }




                                    sensPUSHTOKEN();
                                } catch (Exception e) {
                                    Log.e("excep",e.toString());
                                    e.printStackTrace();
                                }
                                if (json.has("status")){}



                            }
                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {

                                progressDialog.dismiss();
                                Log.e("rrrrrrr",t.toString());

                            }
                        });

                        Toast.makeText(Signupaddress.this, "Signuped !!", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(Signupaddress.this, "Kindly click the GPS icon to fill the Address !!", Toast.LENGTH_SHORT).show();
                    }



                }
            }
        });
    }

    private void sensPUSHTOKEN() {

        String user_id=App.getInstance().getSharedpreference().getID();
       /* TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String imei_no = tm.getDeviceId();*/

           String  imei_no = Settings.Secure.getString(Signupaddress.this.getContentResolver(),
                    Settings.Secure.ANDROID_ID);


        String token=App.getInstance().getSharedpreference().getPUSHTOKEN();
        String Accesstoken=App.getInstance().getSharedpreference().getTOKEN();

        getApiClient().sendPUSHTOKEN(Accesstoken,user_id,imei_no,token).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {

                    progressDialog.dismiss();
                    JSONObject json = new JSONObject(response.body().toString());
                    if (json.has("status")) {


                        if (json.getString("status").equalsIgnoreCase("success")){

                            startActivity(new Intent(Signupaddress.this,AddServiceActivity.class));
                            finish();
                          Log.e("ssss","sssuceess");

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

    private ApiClient getApiClient(){
       if (mApi==null){
           mApi= RetrofitClient.getClient().create(ApiClient.class);
       }
        return mApi;
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
            String address = locationAddress.getAddressLine(0);
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


            signAddr1.setText(address);
                signAddr2.setText(address1);
                signcity.setText(city);
                signstate.setText(state);
                signpin.setText(postalCode);
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
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    private void initviews() {

        termsTV = findViewById(R.id.termsTV);
        back = findViewById(R.id.back);
        signAddr1 = findViewById(R.id.signAddr1);
        signAddr2 = findViewById(R.id.signAddr2);
        signcity = findViewById(R.id.signcity);
        signstate = findViewById(R.id.signstate);
        signpin = findViewById(R.id.signpin);
        gps = findViewById(R.id.gps);
        signupLt = findViewById(R.id.signupLt);
        loginTv = findViewById(R.id.loginTv);
        check = findViewById(R.id.check);
        gstNumberEdt = findViewById(R.id.gstNumberEdt);
        companyEdt = findViewById(R.id.companyEdt);
        permissionUtils = new PermissionUtils(Signupaddress.this);
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);


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
