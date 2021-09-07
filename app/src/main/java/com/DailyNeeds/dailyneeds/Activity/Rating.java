package com.DailyNeeds.dailyneeds.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

import com.DailyNeeds.dailyneeds.R;

public class Rating extends AppCompatActivity {


    private ImageView imgbackrating;
    private LinearLayout ratingSubmit;
    private RatingBar rb1,rb2,rb3,rb4;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        imgbackrating=findViewById(R.id.imgbackrating);
        ratingSubmit=findViewById(R.id.ratingSubmit);
        rb1=findViewById(R.id.rb1);
        rb2=findViewById(R.id.rb2);
        rb3=findViewById(R.id.rb3);
        rb4=findViewById(R.id.rb4);

        imgbackrating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        ratingSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Rating.this, "Submitted!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
