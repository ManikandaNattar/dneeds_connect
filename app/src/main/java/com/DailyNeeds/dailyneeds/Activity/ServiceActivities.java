package com.DailyNeeds.dailyneeds.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.LocationUpdate.BackgroundLocationUpdateService;
import com.DailyNeeds.dailyneeds.R;

public class ServiceActivities extends AppCompatActivity {

    private TextView tService,aService,pService,cService;
    private ImageView Backimg;
    private Button start,stop;
    private AlarmManager alarmManager;
    private  PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_services);
        initviews();

        tService.setText(App.getInstance().getSharedpreference().getTOTALSERVICES());
        aService.setText(App.getInstance().getSharedpreference().getACTIVESERVICES());
        pService.setText(App.getInstance().getSharedpreference().getPENDINGSERVICES());
        cService.setText(App.getInstance().getSharedpreference().getCOMPLETESERVICES());
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Clickevents();
    }

    private void Clickevents() {
        Backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent notificationIntent = new Intent(ServiceActivities.this, LocationUpdate.class);
                 pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,0, 10000,pendingIntent);*/

                startService(new Intent(ServiceActivities.this, BackgroundLocationUpdateService.class));
            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  /*  Intent notificationIntent = new Intent(ServiceActivities.this, LocationUpdate.class);
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,0,10000,pendingIntent);
                    alarmManager.cancel(pendingIntent);*/

                stopService(new Intent(ServiceActivities.this, BackgroundLocationUpdateService.class));

            }
        });

    }

    private void initviews() {


        tService=findViewById(R.id.tService);
        aService=findViewById(R.id.aService);
        pService=findViewById(R.id.pService);
        cService=findViewById(R.id.cService);
        Backimg=findViewById(R.id.Backimg);
        start=findViewById(R.id.start);
        stop=findViewById(R.id.stop);




    }
}
