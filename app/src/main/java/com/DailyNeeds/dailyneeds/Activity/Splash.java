package com.DailyNeeds.dailyneeds.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AcceptService.Assigned_services;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes.GetAllSrviceResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends AppCompatActivity {

    String message = "";
    String view_id = "";
    private ApiClient mApi;

    String flag="";
    String service_idstr="";
    JSONObject jsonObject= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);





        App.getInstance().sharedPreference.removeNOTIFICATION();
        if (App.getInstance().IsInternetAvailable()){

            try {
                Bundle bundle = getIntent().getExtras();
                if (bundle != null) {
                    for (String key : bundle.keySet()) {
                        Object value = bundle.get(key);
                        Log.d("FCM", "Key: " + key + " Value: " + value);

                        if(key.equals("view_id")){
                            jsonObject = objectToJSONObject(value);
                            Log.d("FCMf", "flagf:"+jsonObject.toString());
                            flag = jsonObject.getString("flag");
                            Log.d("FCMff", "flagff:"+flag);

//                            if(flag.contains("chat")){
//                                service_idstr = jsonObject.getString("service_id");
//                                getOneService(service_idstr);
//                            }

                        }

                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }




            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (App.getInstance().getSharedpreference().getIsLogin()){
                        //old code
//                        startActivity(new Intent(Splash.this,MainActivity.class));
                        //old code
                        //old new code
//                        Intent intent = new Intent(Splash.this,MainActivity.class);
//                        intent.putExtra("status",1);
//                        startActivity(intent);
//                        finish();

                        //latest new code
                        if(flag.contains("chat")){
                            try {
                                service_idstr = jsonObject.getString("service_id");
                                getOneService(service_idstr);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else {
                            Intent intent = new Intent(Splash.this,MainActivity.class);
                            intent.putExtra("status",1);
                            startActivity(intent);
                            finish();
                        }


                    }else{
                        startActivity(new Intent(Splash.this,Login.class));
                        finish();
                    }


                }
            }, 3000);


        }else{
            Toast.makeText(this, "Kindly check your internet connection !!!", Toast.LENGTH_SHORT).show();
        }

//        if (extras != null) {
//
//            Toast.makeText(this, "oneone", Toast.LENGTH_SHORT).show();
//            // possible launched from notification
//            // check if desired notification data present in extras then its
//            // confirmed that launched from notification
//
//        }else{
//            Toast.makeText(this, "two", Toast.LENGTH_SHORT).show();
//
//            // not launched from notification
//        }





    }


    public static JSONObject objectToJSONObject(Object object){
        Object json = null;
        JSONObject jsonObject = null;
        try {
            json = new JSONTokener(object.toString()).nextValue();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (json instanceof JSONObject) {
            jsonObject = (JSONObject) json;
        }
        return jsonObject;
    }

    private void  getOneService(String service_id) {
//        progressDialog=new ProgressDialog(this);
//        progressDialog.setMessage("Loading!!!");
//        progressDialog.show();
        String token=App.getInstance().getSharedpreference().getTOKEN();
        String vendorID= App.getInstance().getSharedpreference().getID();

        HashMap<String,String> map1 = new HashMap<>();
        map1.put("Authorization",token);
        map1.put("vendor_id",vendorID);
        map1.put("service_id",service_id);

        Log.d("serviceidone_request:","serviceidone_request:"+map1);

        getApiClient().getAllServicesById(token,vendorID,service_id).enqueue(new Callback<GetAllServiceIdResponse>() {
            @Override
            public void onResponse(Call<GetAllServiceIdResponse> call, Response<GetAllServiceIdResponse> response) {

                Log.d("serviceidone_response:","serviceidone_response:"+new Gson().toJson(response.body()));

//                Toast.makeText(Splash.this, "serviceidone", Toast.LENGTH_SHORT).show();

//                progressDialog.dismiss();

//                if (response.code()==401){
//                    LoginToken("getAllServices");
//                }else {
                try {

                    GetAllServiceIdResponse servicesone = response.body();

                    GetAllServiceIdResponse.Data.AssignedService servicetwo = servicesone.getData().getAssignedServices().get(0);


                    if (servicetwo != null) {
                        Gson gson = new Gson();

                        String myJson = gson.toJson(servicetwo);

                        Log.d("serviceidone_response11:","serviceidone_response11:"+myJson);

                        //old code
                        Intent intent = new Intent(Splash.this,Tracking_service.class);
                        intent.putExtra("model",myJson);
                        startActivity(intent);
                        //old code
                        //new code
//                        Intent intent1 = new Intent(Splash.this,ChatActivity.class);
//                        intent1.putExtra("model",myJson);
//                        startActivity(intent1);


                        Intent intent2 = new Intent(Splash.this,ChatActivity.class);
                        intent2.putExtra("name",App.getInstance().getSharedpreference().getFIRSTNAME());
                        intent2.putExtra("chatid",servicetwo.getChatRoomId());
                        intent2.putExtra("order_id",servicetwo.getOrderId());
                        intent2.putExtra("vendor_id",App.getInstance().getSharedpreference().getID());
                        intent2.putExtra("user_id",servicetwo.getUserId());
                        intent2.putExtra("service_id",servicetwo.getServiceId());
                        startActivity(intent2);


                        //new code
                    }else{

                    }

                } catch (Exception e) {
//                        progressDialog.dismiss();
                    Log.e("eeeeeeeee", e.toString());

                }

            }
//            }

            @Override
            public void onFailure(Call<GetAllServiceIdResponse> call, Throwable t) {
//                progressDialog.dismiss();
                Log.e("eeeeeeeee",t.toString());

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
