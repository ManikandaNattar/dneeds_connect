package com.DailyNeeds.dailyneeds.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse.LoginResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {

    private EditText emailPassEDT,newPassEdt,confiromPassEdt;
    private LinearLayout SubmitLT;
    private  ApiClient mApi;
    String newPassword;
    String email;
    private ProgressDialog progressDialog;
    private ImageView Back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        initviews();

    }

    private void initviews() {

        emailPassEDT=findViewById(R.id.emailPassEDT);
        newPassEdt=findViewById(R.id.newPassEdt);
        confiromPassEdt=findViewById(R.id.confiromPassEdt);
        SubmitLT=findViewById(R.id.SubmitLT);
        Back=findViewById(R.id.Back);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading!!!");

        emailPassEDT.setText(App.getInstance().getSharedpreference().getEMAIL());


        SubmitLT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (newPassEdt.getText().toString().equalsIgnoreCase(confiromPassEdt.getText().toString()) && !newPassEdt.getText().toString().isEmpty()){

                    newPassword=newPassEdt.getText().toString();
                    email=App.getInstance().sharedPreference.getEMAIL();

                    progressDialog.show();
                    PaswordAPIcall();


                }else{
                    Toast.makeText(ChangePassword.this, "Enter the Valid Password !!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void PaswordAPIcall() {

        String Accesstoken=App.getInstance().getSharedpreference().getTOKEN();
        getApiClient().ChangePassword(Accesstoken,email,newPassword).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                progressDialog.dismiss();
                if (response.code()==401){

                    LoginToken();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(ChangePassword.this, "Password Changed Successfully!!", Toast.LENGTH_SHORT).show();
                    finish();
                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
            }
        });




    }

    private void LoginToken() {


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
                                PaswordAPIcall();



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
}
