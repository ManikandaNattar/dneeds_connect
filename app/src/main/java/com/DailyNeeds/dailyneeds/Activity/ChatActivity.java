package com.DailyNeeds.dailyneeds.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes.Assigned_services;
import com.DailyNeeds.dailyneeds.R;
import com.google.gson.Gson;

public class ChatActivity extends AppCompatActivity {

    private ImageView Back;
    private WebView trackChat;
    //old code
//    private String chatid,name;
    //new code
    private String chatid,name,orderid,vendorid,userid,service_id;
    private String usertype;
    private Assigned_services assigned_services;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initviews();
        //old code
//        if (getIntent().getStringExtra("chatid")!=null && getIntent().getStringExtra("name")!=null){
       //new code


            if (getIntent().getStringExtra("chatid") != null && getIntent().getStringExtra("name") != null && getIntent().getStringExtra("order_id") != null && getIntent().getStringExtra("vendor_id") != null && getIntent().getStringExtra("user_id") != null && getIntent().getStringExtra("service_id") != null) {

                chatid = getIntent().getStringExtra("chatid");
                name = getIntent().getStringExtra("name");


                //new code
                orderid = getIntent().getStringExtra("order_id");
                vendorid = App.getInstance().getSharedpreference().getID();
//            vendorid=getIntent().getStringExtra("vendor_id");
                userid = getIntent().getStringExtra("user_id");
                service_id = getIntent().getStringExtra("service_id");
                String user_type_Str = "vendor";
                usertype = user_type_Str;


                Log.d("idid:", "chatid:" + chatid);
                Log.d("idid:", "name:" + name);
                Log.d("idid:", "orderid:" + orderid);
                Log.d("idid:", "vendorid:" + vendorid);
                Log.d("idid:", "userid:" + userid);
                Log.d("idid:", "service_id:" + service_id);
                Log.d("idid:", "usertype:" + usertype);


            }




        trackChat.getSettings().setJavaScriptEnabled(true);
        trackChat.loadUrl("https://dneeds.in/chat-test");

        trackChat.getSettings().setDomStorageEnabled(true);

        trackChat.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                //  trackChat.loadUrl("javascript: sessionStorage.setItem('chat_room',"+chatid+");");
                // Log.e("uuu","javascript: sessionStorage.setItem('chat_room','"+chatid+"');");

                trackChat.loadUrl("javascript: sessionStorage.setItem('chat_room','"+chatid+"');");
                trackChat.loadUrl("javascript: sessionStorage.setItem('first_name','"+name+"');");
                // trackChat.loadUrl("javascript: sessionStorage.setItem('chat_room', b8eccac8685796b93e6741478f9457b3);");

                //new code
                trackChat.loadUrl("javascript: sessionStorage.setItem('order_id','"+orderid+"');");
                trackChat.loadUrl("javascript: sessionStorage.setItem('vendor_id','"+vendorid+"');");
                trackChat.loadUrl("javascript: sessionStorage.setItem('user_id','"+userid+"');");
                trackChat.loadUrl("javascript: sessionStorage.setItem('user_type','"+usertype+"');");
                //newly added for chat notification issue fixes
                trackChat.loadUrl("javascript: sessionStorage.setItem('service_id','"+service_id+"');");

                Log.d("onestep:","chat_room: "+chatid);
                Log.d("onestep:","first_name: "+name);
                Log.d("onestep:","order_id: "+orderid);
                Log.d("onestep:","vendor_id: "+vendorid);
                Log.d("onestep:","user_id: "+userid);
                Log.d("onestep:","user_type: "+usertype);
                Log.d("onestep:","service_id: "+service_id);

//                Toast.makeText(ChatActivity.this, "Log : chatid "+chatid, Toast.LENGTH_SHORT).show();
//                Toast.makeText(ChatActivity.this, "Log : name "+name, Toast.LENGTH_SHORT).show();
//                Toast.makeText(ChatActivity.this, "Log : orderid "+orderid, Toast.LENGTH_SHORT).show();
//                Toast.makeText(ChatActivity.this, "Log : vendorid "+vendorid, Toast.LENGTH_SHORT).show();
//                Toast.makeText(ChatActivity.this, "Log : userid "+userid, Toast.LENGTH_SHORT).show();
//                Toast.makeText(ChatActivity.this, "Log : usertype "+usertype, Toast.LENGTH_SHORT).show();
//                Toast.makeText(ChatActivity.this, "Log : service_id "+service_id, Toast.LENGTH_SHORT).show();





            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });




        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initviews() {

        Back=findViewById(R.id.chatBack);
        trackChat=findViewById(R.id.chatWeb);
    }
}
