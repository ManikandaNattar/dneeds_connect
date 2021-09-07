package com.DailyNeeds.dailyneeds.Adapter;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.DailyNeeds.dailyneeds.Activity.MainActivity;
import com.DailyNeeds.dailyneeds.Activity.Tracking_service;
import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.Fragment.HomeFrag;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AcceptService.AcceptResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.HomeResponse.Assigned_services;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse.LoginResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.DailyNeeds.dailyneeds.Utils.DurationDistance;
import com.google.android.gms.common.util.ArrayUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class AcceptServiceAdapter extends RecyclerView.Adapter<AcceptServiceAdapter.newServiceViewholder> {

    private Context context;
    private ProgressDialog progressDialog;
    private Assigned_services[] assigned_services;
    private DatabaseReference databaseReference;
    private  ApiClient mApi;
    private View view;
    private String orderID;

    public String distanceValue,durationValue;

    SharedPreferences sharedPreferences12;

    SharedPreferences sharedPreferences13;

    String profile_mobienoupdate="";

    String latitude_stri="";
    String longitude_stri="";

    Double latitude_dou;
    Double longitude_dou;

    String latitude_stri1="";
    String longitude_stri1="";

    Double latitude_dou1;
    Double longitude_dou1;


    public AcceptServiceAdapter(Context context, Assigned_services[] assigned_services) {


        this.assigned_services=assigned_services;
        this.context = context;
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading!!!");
    }

    @NonNull
    @Override
    public newServiceViewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.newservice, viewGroup, false);
        return new newServiceViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final newServiceViewholder viewHolder, final int i) {


        orderID = assigned_services[i].getUser_id();
        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(orderID);
        if (databaseReference.child("is_vendor_accepted").equals("C")){

        }



        viewHolder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AcceptApi(i);

            }
        });

        viewHolder.serUserName.setText("Customer name : "+assigned_services[i].getUser_first_name());
        viewHolder.serName.setText("Service name : "+assigned_services[i].getService());
        viewHolder.distancevaluetxt.setText(assigned_services[i].getDistance());

//        new getDistancenDirection().execute();


        viewHolder.declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               view=v;
                RejectAPI(i);




            }
        });
    }


    public class getDistancenDirection extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection mUrlConnection = null;
            StringBuilder mJsonResults = new StringBuilder();
            try {
                String distUrl = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" + latitude_dou + "," + longitude_dou + "&destinations=" + 9.8433 + "," + 78.4809 + "&sensor=false&mode=driving&key="+
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

            Log.d("distance:","distance:"+distanceValue);

//            viewHoldertwo.distancevaluetxt.setText(distanceValue);
//            viewHoldertwo.durationvaluetxt.setText(durationValue);




        }

    }

    private void RejectAPI(int position) {

        progressDialog.show();
        final String orderID=assigned_services[position].getOrder_id();
        final String vendorID=App.getInstance().getSharedpreference().getID();
        String token= App.getInstance().getSharedpreference().getTOKEN();
        Log.e("Reject serviceIP","orderid :"+orderID+"vendorID :"+vendorID+"token :"+token);
        //progressDialog.show();
        getApiClient().RejectService(token,orderID,vendorID).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                if (response.code()==401){
                    LoginToken(position,"reject");

                }else {
                    JSONObject jsonObject=null;
                    try {

                         jsonObject = new JSONObject(new Gson().toJson(response.body()));
                         Log.e("Reject serviceRES",jsonObject.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (jsonObject.has("status")) {

                        String status=response.body().get("status").getAsString();

                        if (status.equalsIgnoreCase("success")){


                            Toast.makeText(context, "Service Rejected !!! ", Toast.LENGTH_SHORT).show();



                           /* ArrayList<Assigned_services> arrayList= ArrayUtils.toArrayList(assigned_services);
                            arrayList.remove(position);
                            assigned_services = new Assigned_services[arrayList.size()];
                            arrayList.toArray(assigned_services);
                            notifyDataSetChanged();
*/


                            String orderID=assigned_services[position].getOrder_id();
                            databaseReference = FirebaseDatabase.getInstance().getReference("user").child(orderID);
                            databaseReference.child("is_vendor_accepted").setValue("R");
                            databaseReference.child("is_order_placed").setValue("Y");
                            //new code
                            databaseReference.child("order_id").setValue(orderID);
                            final String userid =assigned_services[position].getUser_id();
                            databaseReference.child("user_id").setValue(userid);
                            databaseReference.child("shownPopup").setValue("0");

                            Log.d("oneoneone1:","oneoneone1:"+orderID+" / "+userid);
                            //new code
                            Log.e("is_vendor_accepted", "R");


                            ArrayList<Assigned_services> arrayList= ArrayUtils.toArrayList(assigned_services);
                            arrayList.remove(position);
                            assigned_services = new Assigned_services[arrayList.size()];
                            arrayList.toArray(assigned_services);
                            notifyDataSetChanged();
                            progressDialog.dismiss();

                           /* final android.os.Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    progressDialog.dismiss();
                                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                    final HomeFrag homeFrag = new HomeFrag();
                                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, homeFrag).commit();

                                }
                            },3000);
*/


                            databaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }



                    } else {

                        Toast.makeText(context, "Please try again !!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();

            }
        });


    }

    private void AcceptApi(final int position) {


        final String orderID=assigned_services[position].getOrder_id();
        final String vendorID=App.getInstance().getSharedpreference().getID();
        String token= App.getInstance().getSharedpreference().getTOKEN();
        progressDialog.show();
        getApiClient().AcceptService(token,orderID,vendorID).enqueue(new Callback<AcceptResponse>() {
            @Override
            public void onResponse(Call<AcceptResponse> call, Response<AcceptResponse> response) {

                if (response.code()==401){
                    LoginToken(position,"accept");

                }else {

                    progressDialog.dismiss();
                    if (response.body().getStatus().equalsIgnoreCase("success")) {

                        AcceptResponse acceptResponse = response.body();
                      //  Assigned_services assigned_services = acceptResponse.getData().getAssigned_services()[0];

                        databaseReference = FirebaseDatabase.getInstance().getReference("user").child(orderID);
                        databaseReference.child("is_vendor_accepted").setValue("Y");
                        databaseReference.child("is_order_placed").setValue("Y");

                        //new code
                        databaseReference.child("order_id").setValue(orderID);
                        final String userid =assigned_services[position].getUser_id();
                        databaseReference.child("user_id").setValue(userid);
                        databaseReference.child("shownPopup").setValue("0");
                        //new code

                        Log.d("oneoneone11:","oneoneone11:"+orderID+" / "+userid);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                        Toast.makeText(context, response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();

                        com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AcceptService.Assigned_services assigned_service = acceptResponse.getData().getAssigned_services()[0];
                        //com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AcceptService.Assigned_services assigned_service = assigned_services[position];

                        Gson gson = new Gson();

                        String myJson = gson.toJson(assigned_service);
                        context.startActivity(new Intent(context, Tracking_service.class).putExtra("model", myJson));

                    } else {

                        Toast.makeText(context, "Please try again !!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<AcceptResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });


    }

    private void LoginToken(final int position,String apiname) {


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
                                    if (apiname.equalsIgnoreCase("accept")){
                                        AcceptApi(position);
                                    }else if (apiname.equalsIgnoreCase("reject")){
                                        RejectAPI(position);
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

    private ApiClient getApiClient() {
        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;


    }


    @Override
    public int getItemCount() {
        return assigned_services.length;
    }

    public class newServiceViewholder extends RecyclerView.ViewHolder{

        private Button declineBtn,acceptBtn;
        private LinearLayout buttonLT;
        private TextView newServiceTV,serUserName,serName;
        TextView durationvaluetxt,distancevaluetxt;

        public newServiceViewholder(@NonNull View itemView) {
            super(itemView);

            declineBtn=itemView.findViewById(R.id.declineBtn);
        acceptBtn=itemView.findViewById(R.id.acceptBtn);
        buttonLT=itemView.findViewById(R.id.buttonLT);
        newServiceTV=itemView.findViewById(R.id.newServiceTV);
        serUserName=itemView.findViewById(R.id.serUserName);
        serName=itemView.findViewById(R.id.serName);

        durationvaluetxt = itemView.findViewById(R.id.durationvalue);
        distancevaluetxt = itemView.findViewById(R.id.distancevalue);
        }
    }
}

