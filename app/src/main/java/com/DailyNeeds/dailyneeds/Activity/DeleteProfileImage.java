package com.DailyNeeds.dailyneeds.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class DeleteProfileImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent resultIntent = new Intent();
        resultIntent.putExtra("remove_image", true);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }
}
