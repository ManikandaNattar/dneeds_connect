package com.DailyNeeds.dailyneeds.App;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.Preference.Preference;

public class App extends Application {

    private static App mInstance;
    public Preference sharedPreference;
    private  ApiClient mApi;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sharedPreference=new Preference(this);

    }



    public static App getInstance() {
        return mInstance;
    }


    // initialise Shared preference
    public Preference getSharedpreference(){
        return  sharedPreference;
    }


    public boolean IsInternetAvailable(){
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mInstance.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private ApiClient getApiClient(){
        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
    }
}
