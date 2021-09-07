package com.DailyNeeds.dailyneeds.Preference;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private String FIRSTNAME = "firstname";
    private String EMAIL = "email";
    private String VENDOR="vendor_no";
    private String PHONE = "phone";
    private String PASSWORD = "password";

    private String ADDR1 = "addr1";
    private String ADDR2 = "addr2";
    private String CITY = "city";
    private String STATE = "state";
    private String PIN = "pin";
    private String ISLOGIN = "islogin";
    private String PROFILEIMG="profileimg";
    private String TOKEN="token";
    private String ID="id";
    private String USERTYPE="usertype";
    private String PUSHTOKEN="pushtoken";

    private String TOTALSERVICES="totalservices";
    private String PENDINGSERVICES="pendingservices";
    private String ACTIVESERVICES="activeservices";
    private String COMPLETESERVICES="completeservices";
    private String NOTIFICATION="notification";

    private String LATITUDE = "latitude";
    private String LONGITUTE = "longitude";




    public Preference(Context context) {
        this.context = context;
        mSharedPreferences = context.getSharedPreferences("Preference", Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public void ClearPreference() {

        editor.clear();
        editor.commit();
    }

    public void SaveNameReg(String name, String email, String phone, String password,String vendor) {
        editor.putString(FIRSTNAME, name);
        editor.putString(EMAIL, email);
        editor.putString(PHONE, phone);
        editor.putString(PASSWORD, password);
       editor.putString(VENDOR,vendor);
        editor.apply();

    }

    public void setEMAIL(String email){
        editor.putString(EMAIL,email);
        editor.apply();
    }
    public void setVENDOR(String vendor_no){
        editor.putString(VENDOR,vendor_no);
        editor.apply();
    }
    public String getLATITUDE() {
        return mSharedPreferences.getString(LATITUDE,"");
    }

    public void setLATITUDE(String LATITUDE1) {
        editor.putString(LATITUDE,LATITUDE1);
        editor.apply();
    }

    public String getLONGITUTE() {
        return mSharedPreferences.getString(LONGITUTE,"");
    }

    public void setLONGITUTE(String LONGITUTE1) {
        editor.putString(LONGITUTE,LONGITUTE1);
        editor.apply();
    }

    public void SaveAddresReg(String addr1, String addr2, String city, String state, String pin, boolean islogin) {
        editor.putString(ADDR1, addr1);
        editor.putString(ADDR2, addr2);
        editor.putString(CITY, city);
        editor.putString(STATE, state);
        editor.putString(PIN, pin);
        editor.putBoolean(ISLOGIN, islogin);
        editor.apply();
    }
    public void setTOKEN(String token){
        editor.putString(TOKEN,token);
        editor.apply();
    }

    public void setID_TYPE(String id,String usertype){
        editor.putString(ID,id);
        editor.putString(USERTYPE,usertype);
        editor.apply();
    }
    public String getID(){
        return mSharedPreferences.getString(ID,"");
    }
    public String getUSERTYPE(){
        return mSharedPreferences.getString(USERTYPE,"2");
    }

    public String getTOKEN(){
        return mSharedPreferences.getString(TOKEN,"");
    }

    public void setPROFILEIMG(String profileimg){
        editor.putString(PROFILEIMG,profileimg);
        editor.apply();
    }

    public String getPROFILEIMG(){
        return mSharedPreferences.getString(PROFILEIMG,"");
    }
    public void setISLOGIN(boolean islogin){
         editor.putBoolean(ISLOGIN, islogin);
        editor.apply();
    }

    public void removePROFILE_IMAGE(){
        editor.remove(PROFILEIMG);
        editor.apply();
    }
    public String getFIRSTNAME() {
        return mSharedPreferences.getString(FIRSTNAME, "");
    }


    public String getPASSWORD() {
        return mSharedPreferences.getString(PASSWORD, "");
    }

    public boolean getIsLogin() {
        return mSharedPreferences.getBoolean(ISLOGIN, false);
    }


    public  String getEMAIL(){
        return mSharedPreferences.getString(EMAIL,"");
    }
    public String getVENDOR(){
        return mSharedPreferences.getString(VENDOR,"");
    }
    public String getPHONE(){
        return mSharedPreferences.getString(PHONE,"0");
    }

    public String getADDR1(){
        return mSharedPreferences.getString(ADDR1,"");
    }

    public String getADDR2(){
        return mSharedPreferences.getString(ADDR2,"");
    }

    public String getCITY(){
        return mSharedPreferences.getString(CITY,"");
    }

    public String getSTATE(){
        return mSharedPreferences.getString(STATE,"");
    }
    public  String getPIN(){
        return mSharedPreferences.getString(PIN,"");
    }

    public void setPUSHTOKEN(String pushtoken){
        editor.putString(PUSHTOKEN,pushtoken);
        editor.apply();
    }

    public String getPUSHTOKEN(){
        return mSharedPreferences.getString(PUSHTOKEN,"");
    }

    public void setSERVICES(String total,String active,String pending,String complete){
        editor.putString(TOTALSERVICES,total);
        editor.putString(ACTIVESERVICES,active);
        editor.putString(PENDINGSERVICES,pending);
        editor.putString(COMPLETESERVICES,complete);
        editor.apply();
    }

    public String getTOTALSERVICES(){
        return  mSharedPreferences.getString(TOTALSERVICES,"0");
    }
    public String getACTIVESERVICES(){
        return  mSharedPreferences.getString(ACTIVESERVICES,"0");
    }
    public String getPENDINGSERVICES(){
        return  mSharedPreferences.getString(PENDINGSERVICES,"0");
    }
    public String getCOMPLETESERVICES(){
        return  mSharedPreferences.getString(COMPLETESERVICES,"0");
    }

    public void setNOTIFICATION(String notification){
        editor.putString(NOTIFICATION,notification);
        editor.apply();
    }
    public String getNOTIFICATION(){
        return  mSharedPreferences.getString(NOTIFICATION,"");
    }
    public void removeNOTIFICATION(){
        editor.remove(NOTIFICATION);
        editor.apply();
    }
}
