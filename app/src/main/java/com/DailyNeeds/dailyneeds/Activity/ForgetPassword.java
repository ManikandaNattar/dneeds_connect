package com.DailyNeeds.dailyneeds.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetPassword extends AppCompatActivity {

     private ApiClient mApi;
     private LinearLayout mailLT,codeLT,newpasswordLT;
     private Button pwdBTN,codeBTN,emailBTN;
     private EditText emailForgetEDT,forgetCodeEDT,newpwdEDT,confirmpwdEDT;
     private Activity activity;
     String code="";
    private ProgressDialog progressDialog;
    private String email="";
    private ImageView forgetBack;
    private FirebaseAuth mAuth;
    private String MobileNo="";
    private String mVerificationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        activity=this;
        initviews();
        clickevents();
    }



    private void initviews() {
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading!!!");
        emailForgetEDT=findViewById(R.id.emailForgetEDT);
        forgetCodeEDT=findViewById(R.id.forgetCodeEDT);
        newpwdEDT=findViewById(R.id.newpwdEDT);
        confirmpwdEDT=findViewById(R.id.confirmpwdEDT);

        mailLT=findViewById(R.id.mailLT);
        codeLT=findViewById(R.id.codeLT);
        newpasswordLT=findViewById(R.id.newpasswordLT);

        pwdBTN=findViewById(R.id.pwdBTN);
        codeBTN=findViewById(R.id.codeBTN);
        emailBTN=findViewById(R.id.emailBTN);

        forgetBack=findViewById(R.id.forgetBack);

    }



    private void clickevents() {



/*        if (!Patterns.EMAIL_ADDRESS.matcher(emailForgetEDT.getText().toString().trim()).matches() && emailForgetEDT.getText().toString().isEmpty()) {
            Toast.makeText(activity, "Enter valid email.", Toast.LENGTH_SHORT).show();
        }else{

            email= emailForgetEDT.getText().toString().trim();

            progressDialog.show();
            getApiClient().ForgetPasswordEmail(email).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    progressDialog.dismiss();
                    Log.e("fffff",response.body().toString());

                    try {
                        JSONObject jsonObject=new JSONObject(String.valueOf(response.body()));

                        if (jsonObject.getString("status_code").equalsIgnoreCase("200")){

                            JSONObject jsonObject1=jsonObject.getJSONObject("data");
                            if (jsonObject1.has("auth_code")){
                                code=jsonObject1.getString("auth_code");
                                mailLT.setVisibility(View.GONE);
                                codeLT.setVisibility(View.VISIBLE);
                                Toast.makeText(activity, jsonObject1.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }else{

                            JSONObject jsonObject1=jsonObject.getJSONObject("data");
                            Toast.makeText(activity, jsonObject1.getString("message"), Toast.LENGTH_SHORT).show();
                        }


                    }catch (Exception e){
                        progressDialog.dismiss();
                        Log.e("exception",e.toString());
                    }



                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    progressDialog.dismiss();
                }
            });

        }*/


        emailBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 MobileNo=emailForgetEDT.getText().toString();
                if (MobileNo.length()==10){

                    getApiClient().VerifyMobile(MobileNo).enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                            progressDialog.dismiss();

                            try {
                                Log.e("mobileVerification",response.body().toString());
                                JSONObject jsonObject=new JSONObject(String.valueOf(response.body()));

                                if (jsonObject.has("status_code")){
                                    if (jsonObject.getString("status_code").equalsIgnoreCase("200")){

                                        Toast.makeText(activity, "Enter your registered mobile number!!!", Toast.LENGTH_SHORT).show();

                                    }else{
                                        progressDialog.show();
                                        sendVerificationCode();

                                        mailLT.setVisibility(View.GONE);
                                        codeLT.setVisibility(View.VISIBLE);
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
                }else{
                    Toast.makeText(activity, "Enter valid mobile number!!", Toast.LENGTH_SHORT).show();
                }



            }
        });

        codeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                String otpcode=forgetCodeEDT.getText().toString().trim();

                if (!forgetCodeEDT.getText().toString().isEmpty() ){

                    verifyVerificationCode(otpcode);


                }else{
                    Toast.makeText(activity, "Enter the valid code !!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        pwdBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newpassword=newpwdEDT.getText().toString();
                if (confirmpwdEDT.getText().toString().equalsIgnoreCase(newpassword) && !newpassword.isEmpty()){

                    progressDialog.show();
                   getApiClient().NewForgetPassword("mobile",MobileNo,newpassword).enqueue(new Callback<JsonObject>() {
                       @Override
                       public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        progressDialog.dismiss();

                        try{

                            JSONObject jsonObject=new JSONObject(String.valueOf(response.body()));

                            if (jsonObject.getString("status_code").equalsIgnoreCase("200")){

                                startActivity(new Intent(activity,Login.class));
                                finish();
                            }else{

                            }

                        }catch (Exception e){
                            progressDialog.dismiss();
                            Log.e("exception",e.toString());

                        }

                       }

                       @Override
                       public void onFailure(Call<JsonObject> call, Throwable t) {
                           progressDialog.dismiss();

                       }
                   });

                }else{
                    Toast.makeText(activity, "Enter the valid password !!", Toast.LENGTH_SHORT).show();
                }


            }
        });

        forgetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendVerificationCode() {


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + MobileNo,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    private ApiClient getApiClient(){
        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            progressDialog.dismiss();
            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();


            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {

                Log.e("otppppp",code);
                //verifyEDT.setText(code);
                //verifying the code
                //verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("mmmmm",e.toString());
            progressDialog.dismiss();

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId=s;
            progressDialog.dismiss();

        }
    };

    private void verifyVerificationCode(String code) {

        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(activity, "OTP Verified successfully!!!", Toast.LENGTH_SHORT).show();
                            //verification successful we will start the profile activity
                            codeLT.setVisibility(View.GONE);
                            newpasswordLT.setVisibility(View.VISIBLE);
                        } else {
                            progressDialog.dismiss();
                            //verification unsuccessful.. display an error message

                            Toast.makeText(activity, "Kindly enter the valid OTP !!!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }

}
