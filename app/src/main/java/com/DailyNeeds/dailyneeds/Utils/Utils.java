package com.DailyNeeds.dailyneeds.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static int padeindi = 0;
    public static boolean isNetworkAvailable(Context con) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static final void ShowToast(Context context, String msg) {
        if (!msg.isEmpty())
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }



    public static String getInitials(String name) {
        String mInitial = "";
        Pattern mPattern;
        mPattern = Pattern.compile("((^| )[A-Za-z])");
        try {
            Matcher matcher = mPattern.matcher(name);

            while (matcher.find()) {
                mInitial += matcher.group().trim().toUpperCase();
            }

            if (mInitial.length() >= 2) {
                mInitial = mInitial.substring(0, 2);
            }
        } catch (NullPointerException ex) {
            mInitial = "";
        }
        return mInitial;
    }


    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0,
                    encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static String BitMapToStringJPEGFormat(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 15, baos);
        byte[] b = baos.toByteArray();
        Log.e("Bit", "" + b.length);
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Uri convertBitmapToURI(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }


        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 96; // Replaced the 1 by a 96
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 96; // Replaced the 1 by a 96

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;

    }

    public static int getUserColorCode(String strLetter) {

        String strColorCode = null;
        if (null != strLetter) {
            strLetter = strLetter.trim();
        } else {
            return Color.parseColor("#4dc3a9");
        }
        if (strLetter.equalsIgnoreCase("A")) {
            strColorCode = "#ffc423";
        } else if (strLetter.equalsIgnoreCase("B")) {
            strColorCode = "#92d266";
        } else if (strLetter.equalsIgnoreCase("C")) {
            strColorCode = "#dd3d5b";
        } else if (strLetter.equalsIgnoreCase("D")) {
            strColorCode = "#ff8149";
        } else if (strLetter.equalsIgnoreCase("E")) {
            strColorCode = "#4dc3a9";
        } else if (strLetter.equalsIgnoreCase("F")) {
            strColorCode = "#50b1dc";
        } else if (strLetter.equalsIgnoreCase("G")) {
            strColorCode = "#b31271";
        } else if (strLetter.equalsIgnoreCase("H")) {
            strColorCode = "#ffa423";
        } else if (strLetter.equalsIgnoreCase("I")) {
            strColorCode = "#0CD5F6";
        } else if (strLetter.equalsIgnoreCase("J")) {
            strColorCode = "#AE8567";
        } else if (strLetter.equalsIgnoreCase("K")) {
            strColorCode = "#721155";
        } else if (strLetter.equalsIgnoreCase("L")) {
            strColorCode = "#140C56";
        } else if (strLetter.equalsIgnoreCase("M")) {
            strColorCode = "#F7E1A0";
        } else if (strLetter.equalsIgnoreCase("N")) {
            strColorCode = "#1693A5";
        } else if (strLetter.equalsIgnoreCase("O")) {
            strColorCode = "#E80C7A";
        } else if (strLetter.equalsIgnoreCase("P")) {
            strColorCode = "#b31271";
        } else if (strLetter.equalsIgnoreCase("Q")) {
            strColorCode = "#E82C0C";
        } else if (strLetter.equalsIgnoreCase("R")) {
            strColorCode = "#19526c";
        } else if (strLetter.equalsIgnoreCase("S")) {
            strColorCode = "#ffc423";
        } else if (strLetter.equalsIgnoreCase("T")) {
            strColorCode = "#92d266";
        } else if (strLetter.equalsIgnoreCase("U")) {
            strColorCode = "#dd3d5b";
        } else if (strLetter.equalsIgnoreCase("V")) {
            strColorCode = "#ff8149";
        } else if (strLetter.equalsIgnoreCase("W")) {
            strColorCode = "#4dc3a9";
        } else if (strLetter.equalsIgnoreCase("X")) {
            strColorCode = "#50b1dc";
        } else if (strLetter.equalsIgnoreCase("Y")) {
            strColorCode = "#b31271";
        } else {
            strColorCode = "#ffa423";
        }

//        byte [] encodeByte=Base64.decode(":",Base64.DEFAULT);
//        Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
//        Drawable d = new BitmapDrawable(bitmap);
        return Color.parseColor(strColorCode);
    }


}
