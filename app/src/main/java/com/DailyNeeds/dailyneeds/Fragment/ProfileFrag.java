package com.DailyNeeds.dailyneeds.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.se.omapi.Session;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.Activity.DeleteProfileImage;
import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse.LoginResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.ProfilrImageUploadResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.UserDetail.UserDetail;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.updateResponse.ProfilePicResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.updateResponse.UpdateResponseModel;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.updateResponse.Updated_details;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.DailyNeeds.dailyneeds.Utils.TextDrawable;
import com.DailyNeeds.dailyneeds.Utils.Utils;
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
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static tvo.webrtc.ContextUtils.getApplicationContext;


public class ProfileFrag extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,PermissionUtils.PermissionResultCallback {

    public ProfileFrag() {
        // Required empty public constructor
    }

    private View view;
    String image_url = "";

    private EditText vidEdt, nameEdt, emailEdt, poneEdt, addr1Edt, addr2Edt, cityEdt, stateEdt, pincodeEdt;
    private LinearLayout updateLT;
    private ImageView profileIV;
    private int PermissionRequestForCamera = 45;
    private CircleImageView imgProfile;
    private ApiClient mApi;
    private UpdateResponseModel updateResponseModel;
    private ProgressDialog progressDialog;
    private Location mLastLocation;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    ArrayList<String> permissions = new ArrayList<>();
    PermissionUtils permissionUtils;
    double latitude;
    double longitude;

    boolean isPermissionGranted = true;
    private GoogleApiClient mGoogleApiClient;
    private ImageView locationgps;
    private UserDetail userDetail;

    Context c;
    ProfilrImageUploadResponse profilrImageUploadResponse ;

    ProfileFrag(Context c,ProfilrImageUploadResponse profilrImageUploadResponse){
        this.c=c;
        this.profilrImageUploadResponse=profilrImageUploadResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

    }



    @Override
    public void onStart() {

        super.onStart();

    }

    private void location_detect() {

        try {
            getLocation();

            if (mLastLocation != null) {
                latitude = mLastLocation.getLatitude();
                longitude = mLastLocation.getLongitude();
                getAddress();

            } else {

                Log.d("", "Couldn't get the location. Make sure location is enabled on the device");
                //showToast("Couldn't get the location. Make sure location is enabled on the device");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAddress() {


        Address locationAddress = getAddress(latitude, longitude);

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
            String[] parts = {address.substring(0, mid), address.substring(mid)};
            System.out.println(parts[0]); //first part
            System.out.println(parts[1]);


            addr1Edt.setText(parts[0]);
            addr2Edt.setText(parts[1]);
            cityEdt.setText(city);
            stateEdt.setText(state);
            pincodeEdt.setText(postalCode);
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
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS_CAMERA = 675;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS_CHOOSE_PHOTO = 685;
    private Address getAddress(double latitude, double longitude) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            return addresses.get(0);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading!!!");
        initviews();
        clickEvents();
        getUserDetails();

        // check availability of play services
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        progressDialog.setContentView(R.layout.fragment_profile);
        return view;

    }

    private void getUserDetails() {

        progressDialog.show();
        String userid = App.getInstance().sharedPreference.getID();
        String Accesstoken = App.getInstance().getSharedpreference().getTOKEN();

        getApiClient().getUserDetails(Accesstoken, userid).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                progressDialog.dismiss();

                if (response.code() == 401) {
                    LoginToken("get");
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

                    App.getInstance().sharedPreference.setEMAIL(userDetail.getData().getUser_details()[0].getEmail());
                    App.getInstance().sharedPreference.setVENDOR(userDetail.getData().getUser_details()[0].getvendor_no());
                    vidEdt.setText(userDetail.getData().getUser_details()[0].getvendor_no());
                    nameEdt.setText(userDetail.getData().getUser_details()[0].getFirst_name());
                    emailEdt.setText(userDetail.getData().getUser_details()[0].getEmail());
                    poneEdt.setText(userDetail.getData().getUser_details()[0].getMobile_no());
                    addr1Edt.setText(userDetail.getData().getUser_details()[0].getAddress());
                    addr2Edt.setText(userDetail.getData().getUser_details()[0].getAddress2());
                    cityEdt.setText(userDetail.getData().getUser_details()[0].getCity());
                    stateEdt.setText(userDetail.getData().getUser_details()[0].getState());
                    pincodeEdt.setText(userDetail.getData().getUser_details()[0].getZipcode());

                    String baseurl="https://dneeds.in/dailyneeds/";
                    String finalurl  =baseurl+userDetail.getData().getUser_details()[0].getUser_profile_url();
                    Log.e("finalurl", finalurl);
                    ImageView image_url=view.findViewById(R.id.imgProfile);
                    Glide.with(getContext())
                            .load(finalurl)
                            .centerCrop()
                            .placeholder(R.drawable.ic_baseline_account_circle_24)
                            .into(image_url);

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                progressDialog.dismiss();

            }
        });


    }

    private void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
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
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

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

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(getActivity(), resultCode,
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

    private void clickEvents() {

        profileIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  getPermissionImagePick();
                selectImage();
            }
        });

        updateLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                updateUser();


            }
        });

        locationgps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                location_detect();

            }
        });


    }

    private void updateUser() {


        //old code
//        if (nameEdt.getText().toString().equals("")) {
//            Toast.makeText(getActivity(), " Name cannot be empty", Toast.LENGTH_SHORT).show();
//        } else if (emailEdt.getText().toString().equals("")) {
//            Toast.makeText(getActivity(), " Email cannot be empty", Toast.LENGTH_SHORT).show();
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText().toString().trim()).matches()) {
//            Toast.makeText(getActivity(), "Enter valid email.", Toast.LENGTH_SHORT).show();
//        } else if (poneEdt.getText().toString().equals("")) {
//            Toast.makeText(getActivity(), " Phone cannot be empty", Toast.LENGTH_SHORT).show();
//        } else if (addr1Edt.getText().toString().equals("")) {
//            Toast.makeText(getActivity(), " Address cannot be empty", Toast.LENGTH_SHORT).show();
//        } else if (addr2Edt.getText().toString().equals("")) {
//            Toast.makeText(getActivity(), " Address 2 cannot be empty", Toast.LENGTH_SHORT).show();
//        }
        //old  code

        //new code
        if (nameEdt.getText().toString().equals("")) {
            Toast.makeText(getActivity(), " Name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (emailEdt.getText().toString().equals("")) {
            Toast.makeText(getActivity(), " Email cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailEdt.getText().toString().trim()).matches()) {
            Toast.makeText(getActivity(), "Enter valid email.", Toast.LENGTH_SHORT).show();
        } else if (vidEdt.getText().toString().equals("")) {
            Toast.makeText(getActivity(), " id cannot be empty", Toast.LENGTH_SHORT).show();
        }else if (poneEdt.getText().toString().equals("")) {
            Toast.makeText(getActivity(), " Phone cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (addr1Edt.getText().toString().equals("")) {
            Toast.makeText(getActivity(), " Address cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (cityEdt.getText().toString().trim().equals("")) {
            Toast.makeText(getActivity(), "City cannot be empty.", Toast.LENGTH_SHORT).show();
        } else if (stateEdt.getText().toString().equals("")) {
            Toast.makeText(getActivity(), " State cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (pincodeEdt.getText().toString().equals("")) {
            Toast.makeText(getActivity(), " Pin cannot be empty", Toast.LENGTH_SHORT).show();
        } else{


            String addr1 = addr1Edt.getText().toString();
            String addr2 = addr2Edt.getText().toString();
            String city = cityEdt.getText().toString();
            String state = stateEdt.getText().toString();
            String pin = pincodeEdt.getText().toString();


            String addr = addr1 + "," + city + "," + state + "," + pin;
            Geocoder coder = new Geocoder(getActivity());
            List<Address> address;

            try {
                // May throw an IOException
                address = coder.getFromLocationName(addr, 5);


                Address location = address.get(0);
                latitude = location.getLatitude();
                longitude = location.getLongitude();

            } catch (IOException ex) {

                ex.printStackTrace();
            }

            String id = App.getInstance().getSharedpreference().getID();
            String usertypeid = App.getInstance().getSharedpreference().getUSERTYPE();
            String name = nameEdt.getText().toString();
            String email = emailEdt.getText().toString();
            String phone = poneEdt.getText().toString();
            String lat = String.valueOf(latitude);
            String longit = String.valueOf(longitude);
            String token = App.getInstance().getSharedpreference().getTOKEN();

//            String baseurl="https://dneeds.in/dailyneeds/";
//            String finalurl=baseurl+profilrImageUploadResponse.getData().getUploadedData().get(0).getPhotoUrl();
//            ImageView imageView=view.findViewById(R.id.imgProfile);
//            Glide.with(c)
//                    .load(finalurl)
//                    .centerCrop()
//                    .placeholder(R.drawable.ic_baseline_account_circle_24)
//                    .into(imageView);

            Log.e("latitude", lat);
            Log.e("longitude", longit);


            if (lat.length() > 3) {

                progressDialog.show();
                getApiClient().UpdateUser(token, id, usertypeid, email, phone, name, addr1, addr2, city, state, pin, lat, longit, image_url).enqueue(new Callback<UpdateResponseModel>() {
                    @Override
                    public void onResponse(Call<UpdateResponseModel> call, Response<UpdateResponseModel> response) {

                        Log.d("response", "response:" + new Gson().toJson(response.body()));

                        if (response.code() == 401) {
                            LoginToken("update");
                        } else {
                            updateResponseModel = response.body();
                            try {
                                if (updateResponseModel.getStatus().equalsIgnoreCase("success")) {

                                    Updated_details details = updateResponseModel.getData().getUpdated_details();
                                    App.getInstance().getSharedpreference().SaveNameReg(details.getFirst_name(), details.getEmail(), details.getMobile_no(), "",details.getvendor_no());
                                    App.getInstance().getSharedpreference().SaveAddresReg(details.getAddress(), "",  details.getCity(), details.getState(), details.getZipcode(), true);

                                    progressDialog.dismiss();
                                    final FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    final HomeFrag homeFrag = new HomeFrag();
                                    fragmentManager.beginTransaction().replace(R.id.frame_layout, homeFrag).commit();

                                    Toast.makeText(getActivity(), "Updated Successfully !!!", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity(), updateResponseModel.getStatus(), Toast.LENGTH_SHORT).show();
                                }


                            } catch (Exception e) {


                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<UpdateResponseModel> call, Throwable t) {
                        progressDialog.dismiss();
                        Log.e("eeee", t.toString());

                    }
                });


            } else {
                Toast.makeText(getActivity(), "Click the GPS to Fill the Address !!", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void LoginToken(String type) {

        String mobilestr = App.getInstance().getSharedpreference().getPHONE();
        String pass = App.getInstance().getSharedpreference().getPASSWORD();

        String loginflag = "mobile";

        getApiClient().LoginMobile(mobilestr, pass, loginflag).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {


                LoginResponse loginResponse = response.body();

                if (loginResponse != null && loginResponse.getStatus() != null) {

                    try {

                        if (loginResponse.getStatus().equalsIgnoreCase("success")) {

                            if (loginResponse.getData().getUser()[0] != null) {

                                App.getInstance().getSharedpreference().setTOKEN("Bearer " + loginResponse.getData().getAccess_token());
                                Log.e("sssssssss", "Bearer " + loginResponse.getData().getAccess_token());
                                if (type.equalsIgnoreCase("update")) {
                                    updateUser();
                                } else if (type.equalsIgnoreCase("get")) {
                                    getUserDetails();
                                }


                            }

                        }

                    } catch (Exception e) {

                    }

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });


    }

    private ApiClient getApiClient() {
        if (mApi == null) {
            mApi = RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
    }


    private void getPermissionImagePick() {

        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                String[] userPermissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(getActivity(), userPermissions, PermissionRequestForCamera);

            } else {

                String[] userPermissions = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                ActivityCompat.requestPermissions(getActivity(), userPermissions, PermissionRequestForCamera);


            }

        } else {
            // This is Case 2. You have permission now you can do anything related to it
            startActivityForResult(getPickImageIntent(), 7460);

        }

    }
    private void selectImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    List<String> listPermissionsNeeded = new ArrayList<>();
                    listPermissionsNeeded.add(android.Manifest.permission.CAMERA);
                    listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (checkAndRequestPermissionsDy(listPermissionsNeeded, REQUEST_ID_MULTIPLE_PERMISSIONS_CAMERA)) {
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 1);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    List<String> listPermissionsNeeded = new ArrayList<>();
                    listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    listPermissionsNeeded.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (checkAndRequestPermissionsDy(listPermissionsNeeded, REQUEST_ID_MULTIPLE_PERMISSIONS_CHOOSE_PHOTO)) {
                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                    }
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    private boolean checkAndRequestPermissionsDy(List<String> runTimePermissions, int requestCode) {
        List<String> listPermissionsNeeded = new ArrayList<>();

        for (String eachPermissions : runTimePermissions) {
            if (ContextCompat.checkSelfPermission(getActivity(), eachPermissions) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(eachPermissions);
            }
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), requestCode);
            return false;
        }
        return true;
    }


//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//
//        if (data != null) {
//            if (data.hasExtra("remove_image")) {
//
//                App.getInstance().sharedPreference.removePROFILE_IMAGE();
//                String mInitial = Utils.getInitials(App.getInstance().sharedPreference.getFIRSTNAME());
//                int mBgColor = Utils.getUserColorCode(mInitial);
//
//                TextDrawable drawable = TextDrawable.builder()
//                        .beginConfig()
//                        .textColor(R.color.white)
//                        .useFont(Typeface.SANS_SERIF)
//                        .fontSize(40) // size in px
////                    .bold()
//                        .toUpperCase()
//                        .endConfig()
//                        .buildRound(mInitial, mBgColor);
//
//                if (drawable != null) {
//                    // mImageViewProfile.setImageDrawable(drawable);
//                    imgProfile.setImageBitmap(Utils.drawableToBitmap(drawable));
////                imgProfile.setImageDrawable(drawable);
//
//
////        imgProfile.setImageURI(uri);
//
//
//                }
//                deleteFromInternalStorage();
//            }
//        }
//
//
//        if (requestCode == 7460 && resultCode == RESULT_OK) {
//            if (data != null) {
//                Uri uri = data.getData();
//                if (uri != null) {
//                    uploadProfilePhotoToDatabase(uri);
//                }
//
//
//            } else {
//
//
//                Uri uri = Uri.fromFile(new File(getActivity().getExternalCacheDir().getPath(), "profile.png"));
//
//
//                try {
//                    if (Uri.parse(String.valueOf(uri)) != null) {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.parse(String.valueOf(uri)));
//                        imgProfile.setImageBitmap(bitmap);
//                        String imgString = Utils.BitMapToStringJPEGFormat(bitmap);
//                        App.getInstance().sharedPreference.setPROFILEIMG(imgString);
//                    }
//                } catch (Exception e) {
//                    //handle exception
//                }
//
//
//            }
//        }else if (resultCode == 99) {
//
//
//         if (data != null) {
//           Uri uri = data.getData();
//
//         uploadProfilePhotoToDatabase(uri);
//
//
//        }
//
//    }
//
//}
@Override
public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
    super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    switch (requestCode) {
        case 1:
            if (resultCode == RESULT_OK) {
                final Bundle extras = imageReturnedIntent.getExtras();
                Bitmap ProfilePic = extras.getParcelable("data");
                Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                imgProfile.setImageBitmap(ProfilePic);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                // upload_phot0_parent.setImageBitmap(parent_bitmap);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                File file = getFile(Base64.encodeToString(byteArray, Base64.DEFAULT));

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("fileToUpload", file.getName(), requestFile);


                ApiClient apiService =
                        RetrofitClient.getClient().create(ApiClient.class);


                apiService.profileUpload(body).enqueue(new Callback<ProfilrImageUploadResponse>() {
                    @Override
                    public void onResponse(Call<ProfilrImageUploadResponse> call, Response<ProfilrImageUploadResponse> response) {



                        ProfilrImageUploadResponse data1 = response.body();
                        image_url = data1.getData().getUploadedData().get(0).getPhotoUrl();
                        Log.i("all", "data   " + image_url);



                    }

                    @Override
                    public void onFailure(Call<ProfilrImageUploadResponse> call, Throwable t) {


                        Log.i("error", "shoow  " + t.getMessage().toString());

                    }
                });
            }

            break;
        case 2:
            if (resultCode == RESULT_OK) {

                Uri selectedImage = imageReturnedIntent.getData();
                imgProfile.setImageURI(selectedImage);

                String selectedFilePath = FilePath.getPath(getActivity(), selectedImage);
                final File file = new File(selectedFilePath);

                RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);

                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("fileToUpload", file.getName(), requestFile);

                ApiClient apiService = RetrofitClient.getClient().create(ApiClient.class);


                apiService.profileUpload(body).enqueue(new Callback<ProfilrImageUploadResponse>() {
                    @Override
                    public void onResponse(Call<ProfilrImageUploadResponse> call, Response<ProfilrImageUploadResponse> response) {



                        ProfilrImageUploadResponse data1 = response.body();
                        image_url = data1.getData().getUploadedData().get(0).getPhotoUrl();
                        Log.i("all_det", "data   " + image_url);

                    }

                    @Override
                    public void onFailure(Call<ProfilrImageUploadResponse> call, Throwable t) {


                        Log.i("error", "shoow  " + t.getMessage().toString());

                    }
                });
            }
            break;
    }
}

    private File getFile(String paren_photo_base64) {

        String fileName = String.valueOf(System.currentTimeMillis());

        byte[] decodedString = Base64.decode(paren_photo_base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        //prof.setImageBitmap(decodedByte);
        try {
            FileOutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory() + "/" + fileName + "1.png");
            decodedByte.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(Environment.getExternalStorageDirectory() + "/" + fileName + "1.png");
    }
    private void uploadProfilePhotoToDatabase(Uri uri) {

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String bitMapString = Utils.BitMapToStringJPEGFormat(bitmap);
        App.getInstance().sharedPreference.setPROFILEIMG(bitMapString);

        if (bitMapString != null) {
            Bitmap imgbitmap = Utils.StringToBitMap(bitMapString);
            //saveToInternalStorage(imgbitmap);
            if (imgbitmap != null)
                imgProfile.setImageBitmap(imgbitmap);
        }


    }

    private void deleteFromInternalStorage() {

        ContextWrapper cw = new ContextWrapper(getActivity().getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, "profile.jpg");
        App.getInstance().sharedPreference.removePROFILE_IMAGE();
        if (mypath.exists()) {
            if (mypath.delete()) {
            } else {
            }
        }

    }

    private Intent getPickImageIntent() {

        StrictMode.VmPolicy.Builder newbuilder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(newbuilder.build());
        Intent chooserIntent = null;
        List<Intent> intentList = new ArrayList<>();

        Intent pickIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra("return-data", true);
        if (getCaptureImageOutputUri() != null) {
            takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, getCaptureImageOutputUri());
        }
        if (App.getInstance().getSharedpreference().getPROFILEIMG() != null) {
            Intent removeImageIntent = new Intent(getActivity(), DeleteProfileImage.class);
            intentList = addIntentsToList(intentList, removeImageIntent);
        }

        intentList = addIntentsToList(intentList, pickIntent);
        intentList = addIntentsToList(intentList, takePhotoIntent);


        if (intentList.size() > 0) {
            chooserIntent = Intent.createChooser(intentList.remove(intentList.size() - 1),
                    "Select source");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentList.toArray(new Parcelable[]{}));
        }

       /* if (img_available){
        //    Intent removeImageIntent = new Intent(this, DeleteProfileImage.class);
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {removeImageIntent} );
        }*/

        return chooserIntent;

    }

    private List<Intent> addIntentsToList(List<Intent> list, Intent intent) {


        List<ResolveInfo> resInfo = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resInfo) {
            String packageName = resolveInfo.activityInfo.packageName;
            Intent targetedIntent = new Intent(intent);
            targetedIntent.setPackage(packageName);
            list.add(targetedIntent);

        }
        return list;
    }

    private Uri getCaptureImageOutputUri() {

        Uri outputFileUri = null;
        File getImage = getActivity().getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "profile.png"));
        }
        return outputFileUri;
    }

    private void initviews() {

        imgProfile=view.findViewById(R.id.imgProfile);
        profileIV=view.findViewById(R.id.profileIV);
        nameEdt=view.findViewById(R.id.nameEdt);
        emailEdt=view.findViewById(R.id.emailEdt);
        poneEdt=view.findViewById(R.id.poneEdt);
        // disable edittext
        poneEdt.setEnabled(false);
        vidEdt=view.findViewById(R.id.vidEdt);
        vidEdt.setEnabled(false);
        addr1Edt=view.findViewById(R.id.addr1Edt);
        addr2Edt=view.findViewById(R.id.addr2Edt);
        cityEdt=view.findViewById(R.id.cityEdt);
        stateEdt=view.findViewById(R.id.stateEdt);
        pincodeEdt=view.findViewById(R.id.pincodeEdt);
        updateLT=view.findViewById(R.id.updateLT);
       locationgps=view.findViewById(R.id.locationgps);



        permissionUtils = new PermissionUtils(getActivity());
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionUtils.check_permission(permissions, "Need GPS permission for getting your location", 1);

    }

    @Override
    public void PermissionGranted(int request_code) {
        isPermissionGranted=true;
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
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
