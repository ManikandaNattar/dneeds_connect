package com.DailyNeeds.dailyneeds.Service;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;


import com.DailyNeeds.dailyneeds.Activity.FirebaseResposne;
import com.DailyNeeds.dailyneeds.Activity.MainActivity;
import com.DailyNeeds.dailyneeds.Activity.Splash;
import com.DailyNeeds.dailyneeds.App.App;
import com.DailyNeeds.dailyneeds.DemoOne;
import com.DailyNeeds.dailyneeds.DemoTwo;
import com.DailyNeeds.dailyneeds.NetworkCall.ApiClient;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.HomeResponse.Assigned_services;
import com.DailyNeeds.dailyneeds.NetworkCall.RetrofitClient;
import com.DailyNeeds.dailyneeds.R;
import com.DailyNeeds.dailyneeds.Utils.Config;
import com.DailyNeeds.dailyneeds.Utils.NotificationUtils;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FirebasePushNotificationService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    private FirebaseResposne firebase_resposne;

    private  ApiClient mApi;

    private ProgressDialog progressDialog;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());


//        //new code
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
//            handleNotification(remoteMessage.getNotification().getBody());
//        }
        

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {

            Log.e(TAG, "Data Payload=" + remoteMessage.getData().toString());


//            try{
//                Map<String, String> params = remoteMessage.getData();
//                JSONObject object = new JSONObject(params);
//                Log.e("JSON OBJECT", object.toString());
//
//                handleDataMessage(object);
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }

            //new code



//            Toast.makeText(FirebasePushNotificationService.this, "serviceidone", Toast.LENGTH_SHORT).show();


//            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//
//            Log.d(TAG, "Message data payload1: " + remoteMessage.getData().toString());

//            Map<String, String> params = remoteMessage.getData();
//            JSONObject object = new JSONObject(params);
//            Log.d("JSON_OBJECT", "JSON_OBJECT"+object.toString());
//            JSONObject object1=null;
//            String viewidstr = "";
//            try {
//                viewidstr = object.getString("view_id");
//                object1 = new JSONObject(viewidstr);
//                Log.d("JSON_OBJECT1", "JSON_OBJECT1"+object1.toString());
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            String flagstr="";
//            String serviceidstr = "";
//            try {
//                flagstr = object1.getString("flag");
//                serviceidstr = object1.getString("service_id");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

//            if(flagstr.equals("chat")){
//
//                Toast.makeText(this, "chat", Toast.LENGTH_SHORT).show();
//
////                getOneService(serviceidstr);
////
////                Intent intent = new Intent(FirebasePushNotificationService.this, TrackingServiceTwo.class);
////                startActivity(intent);
//
//            }else {
//                Toast.makeText(this, "Order", Toast.LENGTH_SHORT).show();
//                if (/* Check if data needs to be processed by long running job */ true) {
//                    // For long-running tasks (10 seconds or more) use WorkManager.
//                } else {
//                    // Handle message within 10 seconds
//                    handleNow();
//                }


//                if (remoteMessage.getNotification() != null) {
//                    Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//                    try {
//                        String body = null, title = null;
//                        String imageUri = null;
//                        if (remoteMessage.getNotification().getBody() != null) {
//                            body = remoteMessage.getNotification().getBody();
//                        }
//                        if (remoteMessage.getNotification().getTitle() != null) {
//                            title = remoteMessage.getNotification().getTitle();
//                        }
//                        if (remoteMessage.getNotification().getImageUrl() != null) {
//                            imageUri = remoteMessage.getNotification().getImageUrl().toString();
//                        }
//                        new generatePictureStyleNotification(this, title, body,
//                                imageUri).execute();
//                    } catch (Exception e) {
//
//                    }
//                }
//            }
//            }


//            Log.d(TAG, "Message data payload11: " + firebase_resposne.getViewId().getFlag());
//            Log.d(TAG, "Message data payload111: " + firebase_resposne.getViewId().getOrderId());



            //new code
//            try {
//                JSONObject json = new JSONObject(remoteMessage.getData().toString());
//                JSONObject dataJson = json.getJSONObject("data");
//                String message = stripHtml(dataJson.getString("message"));
//                JSONObject dataJson1 = dataJson.getJSONObject("view_id");
//                String flagone = dataJson1.getString("flag");
////                generateNotification(message, type);
//
//                Log.d(TAG, "Message data payload11: " + message);
//                Log.d(TAG, "Message data payload111: " + flagone);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            //new code


            //old code
            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            try {
                String body = null, title = null;
                String imageUri = null;
                if (remoteMessage.getNotification().getBody() != null) {
                    body = remoteMessage.getNotification().getBody();
                }
                if (remoteMessage.getNotification().getTitle() != null) {
                    title = remoteMessage.getNotification().getTitle();
                }
                if (remoteMessage.getNotification().getImageUrl() != null) {
                    imageUri = remoteMessage.getNotification().getImageUrl().toString();
                }
                new generatePictureStyleNotification(this, title, body,
                        imageUri).execute();
            } catch (Exception e) {

            }
        }
        //old code

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void handleNotification(String message) {
        if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
            // app is in foreground, broadcast the push message
            Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
            pushNotification.putExtra("message", message);
            LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            // play notification sound
            NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
            notificationUtils.playNotificationSound();
        }else{
            // If the app is in background, firebase itself handles the notification
        }
    }
    private void handleDataMessage(JSONObject json) {

        try {
            String message = json.getString("message");
            String title= "Finobot",imageUrl = null,timestamp = null;

            final String view_id=json.getString("view_id");
            final JSONObject viewid_object = new JSONObject(view_id);


            String flag=viewid_object.getString("flag");

            Log.d("12121:","12121:"+message);
            Log.d("12121:","12121:"+view_id);
            Log.d("12121:","12121:"+flag);

//            Toast.makeText(this, "12121:"+message, Toast.LENGTH_LONG).show();
//            Toast.makeText(this, "12121:"+view_id, Toast.LENGTH_LONG).show();
//
//            Toast.makeText(this, "12121:"+flag, Toast.LENGTH_LONG).show();





            // String page_name=viewid_object.getString("page_name");

            Log.e(TAG, "message: " + message);

            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();

                Intent resultIntent = new Intent(getApplicationContext(), DemoOne.class);
//                resultIntent.putExtra("message", message);
//                resultIntent.putExtra("flag", flag);


                //    resultIntent.putExtra("page_name", page_name);

               /* Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        bus.post(viewid_object);
                    }
                });*/

                // check for text attachment
//                if (TextUtils.isEmpty(imageUrl)) {
//                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//                } else {
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }
            }else {
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra("message", message);
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
                NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
                notificationUtils.playNotificationSound();
                Intent resultIntent = new Intent(getApplicationContext(), DemoTwo.class);
                resultIntent.putExtra("message", message);
                resultIntent.putExtra("flag", flag);

                Log.d("flagsss","flagsss"+flag);
                // check for text attachment
//                if (TextUtils.isEmpty(imageUrl)) {
//                    showNotificationMessage(getApplicationContext(), title, message, timestamp, resultIntent);
//                } else {
//                    showNotificationMessageWithBigImage(getApplicationContext(), title, message, timestamp, resultIntent, imageUrl);
//                }

            }





        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }

    }

//    private void  getOneService(String serviceidstrone) {
////        progressDialog=new ProgressDialog(this);
////        progressDialog.setMessage("Loading!!!");
////        progressDialog.show();
//        String token=App.getInstance().getSharedpreference().getTOKEN();
//        String vendorID= App.getInstance().getSharedpreference().getID();
//        getApiClient().getAllServicesById(token,vendorID,serviceidstrone).enqueue(new Callback<Assigned_services>() {
//            @Override
//            public void onResponse(Call<Assigned_services> call, Response<Assigned_services> response) {
//
//                Log.d("serviceidone:","serviceidone:"+new Gson().toJson(response.body()));
//
//                Toast.makeText(FirebasePushNotificationService.this, "serviceidone", Toast.LENGTH_SHORT).show();
//
////                progressDialog.dismiss();
//
////                if (response.code()==401){
////                    LoginToken("getAllServices");
////                }else {
//                    try {
//
//                        Assigned_services servicesone = response.body();
//                        if (servicesone != null) {
//                            Gson gson = new Gson();
//
//                            String myJson = gson.toJson(servicesone);
////                            Intent intent = new Intent(FirebasePushNotificationService.this,TrackingServiceTwo.class);
////                            intent.putExtra("model",myJson);
////                            startActivity(intent);
//                        }else{
//
//                        }
//
//                    } catch (Exception e) {
////                        progressDialog.dismiss();
//                        Log.e("eeeeeeeee", e.toString());
//
//                    }
//
//                }
////            }
//
//            @Override
//            public void onFailure(Call<Assigned_services> call, Throwable t) {
////                progressDialog.dismiss();
//                Log.e("eeeeeeeee",t.toString());
//
//            }
//        });
//
//
//
//    }

    private ApiClient getApiClient(){
        if (mApi==null){
            mApi= RetrofitClient.getClient().create(ApiClient.class);
        }
        return mApi;
    }

    public String stripHtml(String html) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return "" + Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return "" + Html.fromHtml(html);
        }
    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);
        Log.e("tttttt" , token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
    }
    // [END on_new_token]

    /**
     * Schedule async work using WorkManager.
     */


    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {

        if (token!=null){
            App.getInstance().getSharedpreference().setPUSHTOKEN(token);
        }



    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     * @param result
     */
    private void sendNotification(String messageBody, String title, Bitmap result) {

        Log.d("Welcomenotification:","Welcomenotification:");

//        Toast.makeText(this, "sendnotification", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, Splash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.default_notification_channel_id);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setContentIntent(pendingIntent);

        if (result != null) {
            notificationBuilder.setLargeIcon(result);
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(result));
        }

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Learning",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(createID() /* ID of notification */, notificationBuilder.build());
    }

    public class generatePictureStyleNotification extends AsyncTask<String, Void, Bitmap> {

        private Context mContext;
        private String title, message, imageUrl;

        public generatePictureStyleNotification(Context context, String title, String message, String imageUrl) {
            super();
            this.mContext = context;
            this.title = title;
            this.message = message;
            this.imageUrl = imageUrl;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            if (imageUrl == null)
                return null;
            InputStream in;
            try {
                URL url = new URL(this.imageUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            sendNotification(message, title, result);

            Log.d("msg:","msg:"+message);

            Intent broadcastIntent = new Intent(MainActivity.NOTIFY_ACTIVITY_ACTION );
              broadcastIntent.setAction(MainActivity.NOTIFY_ACTIVITY_ACTION );
            broadcastIntent.putExtra(MainActivity.NOTIFY_ACTIVITY_ACTION , MainActivity.NOTIFY_ACTIVITY_ACTION );
            //broadcastIntent.putExtra("addtional_param2", 2); //etc

            getApplicationContext().sendBroadcast(broadcastIntent);

        }
    }

    public int createID(){
        Date now = new Date();
        int id = Integer.parseInt(new SimpleDateFormat("ddHHmmss",  Locale.US).format(now));
        return id;
    }
}
