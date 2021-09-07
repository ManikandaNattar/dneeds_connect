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
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
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
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupName extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, PermissionUtils.PermissionResultCallback, LocationListener {


    private EditText name, email, phone, password,con_password,signAddr1, signAddr2, signcity, signstate, signpin, vendorno;
    private RadioButton Radiomale, Radiofemale;
    private LinearLayout register_User;
   private ImageView gps;
    private TextView login;
    private ProgressDialog progressDialog;
    private  ApiClient mApi;
    private Location mLastLocation;
    double latitude;
    double longitude;
    boolean isPermissionGranted;
    private GoogleApiClient mGoogleApiClient;
    private LocationManager locationManager;

    //new
    private Location mLastLocationone;

    boolean isPermissionGrantedone;
    private GoogleApiClient mGoogleApiClientone;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    double latitudeone;
    double longitudeone;
    String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_name);
        initview();
//        getLocation();
        location_detectone();
//        getLocationpermission();
        location_detect();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        signAddr1.setText(address);
        //new

        // check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
            location_detectone();
        }

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                location_detect();
            }
        });

        register_User.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (name.getText().toString().equals("")) {
                    Toast.makeText(SignupName.this, " Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (email.getText().toString().equals("")) {
                    Toast.makeText(SignupName.this, " Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
                    Toast.makeText(SignupName.this, "Enter valid email.", Toast.LENGTH_SHORT).show();
                } else if (phone.getText().toString().equals("")) {
                    Toast.makeText(SignupName.this, " Phone cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (password.getText().toString().equals("")) {
                    Toast.makeText(SignupName.this, " Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (con_password.getText().toString().equals("")) {
                    Toast.makeText(SignupName.this, " Confirm Password cannot be empty", Toast.LENGTH_SHORT).show();
                }else if(!password.getText().toString().equalsIgnoreCase(con_password.getText().toString())){
                    Toast.makeText(SignupName.this, " Password is not match !!", Toast.LENGTH_SHORT).show();
                }else if(signAddr1.getText().toString().equals("")) {
                    Toast.makeText(SignupName.this, " Address cannot be empty", Toast.LENGTH_SHORT).show();
                }else if (signcity.getText().toString().trim().equals("")) {
                    Toast.makeText(SignupName.this, "City cannot be empty.", Toast.LENGTH_SHORT).show();
                } else if (signstate.getText().toString().equals("")) {
                    Toast.makeText(SignupName.this, " State cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (signpin.getText().toString().equals("")) {
                    Toast.makeText(SignupName.this, " Pin cannot be empty", Toast.LENGTH_SHORT).show();
                } else
                    {
                        progressDialog.show();
                        String addr1=signAddr1.getText().toString();
                        String city=signcity.getText().toString();
                        String state=signstate.getText().toString();
                        String pincode=signpin.getText().toString();

                        String addr=addr1+","+city+","+state+","+pincode;
                        Geocoder coder = new Geocoder(SignupName.this);
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


                        String lat= String.valueOf(latitude);
                        String longit= String.valueOf(longitude);


                        Log.e("latt",lat);
                        Log.e("lang",longit);

                        App.getInstance().getSharedpreference().SaveNameReg(name.getText().toString(), email.getText().toString(), phone.getText().toString(),password.getText().toString(),vendorno.getText().toString());
                        progressDialog.show();
                    getApiClient().VerifyMobile(phone.getText().toString()).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            progressDialog.dismiss();

                            try {
                                Log.e("mobileVerification",response.body().toString());
                                JSONObject jsonObject=new JSONObject(String.valueOf(response.body()));

                                if (jsonObject.has("status_code")){
                                    if (jsonObject.getString("status_code").equalsIgnoreCase("200")){

                                      //  App.getInstance().getSharedpreference().SaveNameReg(name.getText().toString(), email.getText().toString(), phone.getText().toString(), password.getText().toString());
                                        Intent intent = new Intent(SignupName.this, Verification.class);
                                        intent.putExtra("address", signAddr1.getText().toString());
                                        startActivity(intent);
                                        //startActivity(new Intent(SignupName.this, Verification.class));
                                    }else{

                                        Toast.makeText(SignupName.this, "Mobile number already registered !!!", Toast.LENGTH_SHORT).show();

                                    }

                                }

                            } catch (Exception e) {
                                progressDialog.dismiss();
                                System.out.println("eee"+e.toString());
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {

                            progressDialog.dismiss();
                            Log.e("error",t.toString());
                        }
                    });



                }

            }
        });


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
                            status.startResolutionForResult(SignupName.this, REQUEST_CHECK_SETTINGS);

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

    private void location_detectone() {

        try {
            getLocationone();

            if (mLastLocationone != null) {
                Log.e("lastlocation", String.valueOf(mLastLocationone.getLatitude()));
                latitudeone = mLastLocationone.getLatitude();
                longitudeone = mLastLocationone.getLongitude();
                getAddressone();

            } else {
                getAddressone();
                Log.d("", "Couldn't get the location. Make sure location is enabled on the device");
                //showToast("Couldn't get the location. Make sure location is enabled on the device");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getAddressone() {

        Log.d("Addresslane 1=:","Address:" +latitudeone);
        Log.d("Addresslane 11=:", "Address:" +longitudeone);

        Address locationAddress = getAddressonetwo(latitudeone, longitudeone);
        Log.e("addrLat", String.valueOf(latitudeone));

        if (locationAddress != null) {
            String address = locationAddress.getAddressLine(0);
            String address1 = locationAddress.getAddressLine(1);

            Log.e("Addresslane123:", address + "");
            Log.e("Addresslane123:", address1 + "");
            String city = locationAddress.getLocality();
            String state = locationAddress.getAdminArea();
            String country = locationAddress.getCountryName();
            String postalCode = locationAddress.getPostalCode();

            Log.d("Addresslane 1-:","Address:" +address);
            Log.d("Addresslane 11-:", "Address:" +address1);
            Log.d("Addresslane 111:", "Address:" +city);
            Log.d("Addresslane 1111:", "Address:" +state);
            Log.d("Addresslane 11111:", "Address:" +country);
            Log.d("Addresslane 111111:", "Address:" +postalCode);


            final int mid = address.length() / 2; //get the middle of the String
            String[] parts = {address.substring(0, mid),address.substring(mid)};
            System.out.println(parts[0]); //first part
            System.out.println(parts[1]);

Log.d("onek1:","onek1:"+address);
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

    public Address getAddressonetwo(double latitude, double longitude) {
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


    private void getLocationone() {

        if (isPermissionGrantedone) {

            try {
                mLastLocationone = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClientone);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    private void getLocationpermission() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }


    public void backToLogin(View view) {
        startActivity(new Intent(SignupName.this, Login.class));
        finish();
    }


    private ApiClient getApiClient() {
        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
    }


    private void initview() {
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
       gps = findViewById(R.id.gps);
        signAddr1 = findViewById(R.id.signAddr1);
        signAddr2 = findViewById(R.id.signAddr2);
        signcity = findViewById(R.id.signcity);
        signstate = findViewById(R.id.signstate);
        signpin = findViewById(R.id.signpin);
        password = findViewById(R.id.password);
        con_password = findViewById(R.id.con_password);
        register_User = findViewById(R.id.register_User);
        login=findViewById(R.id.login);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading!!!");


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignupName.this,Login.class));
                finish();

            }
        });
    }



    private void location_detect() {
        try {
            getLocationone();

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
    public  Address getAddress(double latitude, double longitude) {
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

                Log.d("onek11:","onek11:"+address);
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


    @Override
    protected void onStart() {
        super.onStart();
        location_detectone();
        location_detect();
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

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.e("currentlocation", String.valueOf(location.getLatitude()));
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    protected void onResume() {
        super.onResume();
        location_detectone();
        getLocationpermission();
        location_detect();
    }
}




