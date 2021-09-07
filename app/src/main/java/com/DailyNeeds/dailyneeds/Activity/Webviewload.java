package com.DailyNeeds.dailyneeds.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.DailyNeeds.dailyneeds.R;

public class Webviewload extends AppCompatActivity {

    private ImageView BackWeb;
    private WebView webView;
    private ProgressDialog progressBar;
    private TextView headertitle;
    private String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webviewload);



        headertitle=findViewById(R.id.headertitle);
        BackWeb=findViewById(R.id.BackWeb);
        webView=findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        if (getIntent().getStringExtra("headertitle")!=null){
            headertitle.setText(getIntent().getStringExtra("headertitle"));
        }
        if (getIntent().getStringExtra("url")!=null){
            Url=getIntent().getStringExtra("url");
        }



        webView.setWebViewClient(new WebViewClient());

        if (Url==null || Url.isEmpty()){
            webView.loadUrl("https://policies.google.com/terms?hl=en-US");
        }else{
            webView.loadUrl(Url);
        }

       // webView.loadUrl(url);



        BackWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }
}
