package com.DailyNeeds.dailyneeds.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verification extends AppCompatActivity {
    private Button verifyBTN;
    private EditText verifyEDT;
    private FirebaseAuth mAuth;
    private String MobileNo;
    private String mVerificationId;
    private ImageView otpback;
    private ProgressDialog progressDialog;
    private TextView resend;
    String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mAuth = FirebaseAuth.getInstance();
        verifyBTN = findViewById(R.id.verifyBTN);
        verifyEDT = findViewById(R.id.verifyEDT);
        otpback = findViewById(R.id.otpback);
        resend = findViewById(R.id.resend);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading!!!");
        progressDialog.show();
        sendVerificationCode();

        Intent intent = getIntent();
        address = intent.getStringExtra("address");

        verifyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String code=verifyEDT.getText().toString().trim();
                if (!code.isEmpty()){

                    verifyVerificationCode(code);
                }


            }
        });

        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendVerificationCode();

            }
        });

        otpback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendVerificationCode() {
        MobileNo = App.getInstance().sharedPreference.getPHONE();
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + MobileNo,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
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
                Log.e("otppppppp",code);
                //verifyEDT.setText(code);
                //verifying the code
                //verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Verification.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
                .addOnCompleteListener(Verification.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            Toast.makeText(Verification.this, "OTP Verified successfully!!!", Toast.LENGTH_SHORT).show();
                            //verification successful we will start the profile activity
                            Intent intent = new Intent(Verification.this, Signupaddress.class);
                            intent.putExtra("address", address);
                            startActivity(intent);

                        } else {
                            progressDialog.dismiss();
                            //verification unsuccessful.. display an error message

                            Toast.makeText(Verification.this, "Kindly enter the valid OTP !!!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


}

}
