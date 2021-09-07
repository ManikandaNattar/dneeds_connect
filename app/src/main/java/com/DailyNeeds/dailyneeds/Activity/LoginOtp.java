package com.DailyNeeds.dailyneeds.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.R;

import java.util.Random;

public class LoginOtp extends AppCompatActivity {
    private String otpNumber;
    private EditText edt1,edt2,edt3,edt4,edt5,edt6;
    private LinearLayout LoginBtn;
    private String MobileStr,VerifyStr,countryCode,URL,otpnumber;
    private TextView timertv,resendtv,notreceivetv;
    private ImageView backImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_otp);

        backImg=findViewById(R.id.img_back_my_orders);
        LoginBtn =findViewById(R.id.LoginBtn);
        timertv=findViewById(R.id.timertv);
        resendtv=findViewById(R.id.resendtv);
        notreceivetv=findViewById(R.id.notreceivetv);
        edt1=findViewById(R.id.edt1);
        edt2=findViewById(R.id.edt2);
        edt3=findViewById(R.id.edt3);
        edt4=findViewById(R.id.edt4);
        edt5=findViewById(R.id.edt5);
        edt6=findViewById(R.id.edt6);
        resendtv.setVisibility(View.GONE);

        otpNumber=RandomNumber();

        if (getIntent()!=null && getIntent().getStringExtra("phone")!=null){
            MobileStr=getIntent().getStringExtra("phone");
            otpnumber=getIntent().getStringExtra("otp");
            Toast.makeText(LoginOtp.this, "Your OTP is "+otpnumber, Toast.LENGTH_LONG).show();
        }

        notreceivetv.setVisibility(View.GONE);


        textWacthers();
        clickEvents();
        timer();

    }

    private void clickEvents() {

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VerifyStr=edt1.getText().toString()+edt2.getText().toString()+edt3.getText().toString()+edt4.getText().toString()+edt5.getText().toString()+edt6.getText().toString();
                if (VerifyStr.equalsIgnoreCase(otpnumber)){
                    App.getInstance().getSharedpreference().setISLOGIN(true);
                    startActivity(new Intent(LoginOtp.this,MainActivity.class));
                    finish();
                }

            }
        });

        resendtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer();

                App.getInstance().sharedPreference.setISLOGIN(false);

             /*   if (MobileStr.length()==10) {
                    String old = "http://control.msg91.com/api/sendotp.php?otp_length=6&authkey=262852AVZTMdYhbNf5c6532e3&message=Your+OTP+is+179438&mobile=9080628342&sender=VMMEDI&otp=179438&otp_expiry=30&email=vinformaxchennai@gmail.com&template=otp";
                    URL = "http://control.msg91.com/api/sendotp.php?otp_length=6&authkey=262852AVZTMdYhbNf5c6532e3&message=Your+OTP+is+"+otpNumber+"&mobile="+MobileStr+"&sender=VMMEDI&otp="+otpNumber+"&otp_expiry=30&email=vinformaxchennai@gmail.com&template=otp";

                    Log.e("nnnnnn", URL);
                    Log.e("ooooo", old);

                if (MyApplication.getInstance().IsInternetAvailable()) {

                    OkHttpClient client=new OkHttpClient();

                    Request request=new Request.Builder()
                                        .url(URL)
                                        .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                            Log.e("eeeeee",e.toString());

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                        Log.e("sssss","sssss");
                                }
                            });


                        }
                    });




                  }else{

                    Toast.makeText(LoginOTP.this, "Kindly Check your Internet Connection!!", Toast.LENGTH_SHORT).show();

                }

                }else{
                    Toast.makeText(LoginOTP.this, "Enter valid Mobile Number!!", Toast.LENGTH_SHORT).show();
                }*/

                Toast.makeText(LoginOtp.this, "Your OTP is "+otpnumber, Toast.LENGTH_LONG).show();
                Log.e("oooo",otpnumber);
            }


        });

    }

    private void timer() {

        notreceivetv.setVisibility(View.GONE);
        resendtv.setVisibility(View.GONE);
        timertv.setVisibility(View.VISIBLE);

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                timertv.setText("seconds remaining\n " + millisUntilFinished / 1000);

                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                notreceivetv.setVisibility(View.VISIBLE);
                resendtv.setVisibility(View.VISIBLE);
                timertv.setVisibility(View.GONE);
            }

        }.start();

    }


    private void textWacthers() {

        // cursor automatically goes next
        edt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()==1){
                    edt2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()==1){
                    edt3.requestFocus();
                }else if (charSequence.length()==0){
                    edt1.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()==1){
                    edt4.requestFocus();
                }else if (charSequence.length()==0){
                    edt2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()==1){
                    edt5.requestFocus();
                }else if (charSequence.length()==0){
                    edt3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()==1){
                    edt6.requestFocus();
                }else if (charSequence.length()==0){
                    edt4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        edt6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()==0){
                    edt5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private String RandomNumber() {

        Random rnd = new Random();
        int number = rnd.nextInt(999999);

        // this will convert any number sequence into 6 character.
        return String.format("%06d", number);

    }

}
