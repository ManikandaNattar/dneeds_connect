package com.DailyNeeds.dailyneeds.NetworkCall;

import com.DailyNeeds.dailyneeds.Activity.GetAllServiceIdResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AcceptService.AcceptResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AddSerRes.AddService;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices.AllCategoryServiceRes;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.CompleteOrderRes.CompleteOrderRes;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.HomeResponse.Assigned_services;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.HomeResponse.HomeResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse.LoginResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.ProfilrImageUploadResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.SignupResponse.SignupResponseModel;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes.GetAllVendorService;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes.VendorServiceOff;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes.GetAllSrviceResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.updateResponse.ProfilePicResponse;
import com.DailyNeeds.dailyneeds.NetworkCall.ResModel.updateResponse.UpdateResponseModel;
import com.google.gson.JsonObject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiClient {


    @FormUrlEncoded
    @POST("users/user_sign_up")
    Call<JsonObject>Signup(

            @Field("usertype_id") String usertype_id,
            @Field("email") String email,
            @Field("password") String password,
            @Field("mobile_no") String mobile_no,
            @Field("first_name") String first_name,
            @Field("address") String address,
            @Field("address2") String address2,
            @Field("city") String city,
            @Field("state") String state,
            @Field("zipcode") String zipcode,
            @Field("is_social_user") String is_social_user,
            @Field("user_paid_type") String user_paid_type,
            @Field("is_licence") String is_licence,
            @Field("is_approved") String is_approved,
            @Field("user_org_legal") String user_org_legal,
            @Field("rating") String rating,
            @Field("country") String country,
            @Field("auth_code") String auth_code,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("company_name") String company_name,
            @Field("gst_in") String gst_no
    );


    @FormUrlEncoded
    @POST("users/login")
    Call<LoginResponse> LoginMobile(

            @Field("mobile_no") String email,
            @Field("password") String password,
            @Field("login_flag") String login_flag
    );

    @FormUrlEncoded
    @POST("users/login")
    Call<JsonObject>Loginemail(

            @Field("email") String email,
            @Field("password") String password,
            @Field("login_flag") String login_flag
    );

    @FormUrlEncoded
    @POST("users/update_user_details")
    Call<UpdateResponseModel>UpdateUser(
            @Header("Authorization") String auth,
            @Field("id") String id,
            @Field("usertype_id") String usertype_id,
            @Field("email") String email,
            @Field("mobile_no") String mobile_no,
            @Field("first_name") String first_name,
            @Field("address") String address,
            @Field("address2") String address2,
            @Field("city") String city,
            @Field("state") String state,
            @Field("zipcode") String zipcode,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("user_profile_url") String user_profile_url
    );
//    @FormUrlEncoded
//    @POST()
//    Call<UpdateResponseModel>profileUpload(
//
//    );
    @Multipart
    @POST("users/upload_media")
    Call<ProfilrImageUploadResponse> profileUpload(@Part MultipartBody.Part fileToUpload);

//    old code
//    @FormUrlEncoded
//    @POST("vendor/get_home_cards")
//    Call<JsonObject>getHomeDetail(
//            @Header("Authorization") String auth,
//            @Field("vendor_id") String vendor_id
//    );

    //new code
    @FormUrlEncoded
    @POST("vendor/get_home_cards")
    Call<JsonObject>getHomeDetail(
            @Header("Authorization") String auth,
            @Field("vendor_id") String vendor_id,
            @Field("vendor_latitude") String vendor_latitude,
            @Field("vendor_longitude") String vendor_longitude

    );

    @FormUrlEncoded
    @POST("vendor/accept_service")
    Call<AcceptResponse>AcceptService(
            @Header("Authorization") String auth,
            @Field("order_id") String order_id,
            @Field("vendor_id") String vendor_id
    );


    @FormUrlEncoded
    @POST("vendor/reject_order")
    Call<JsonObject>RejectService(
            @Header("Authorization") String auth,
            @Field("order_id") String order_id,
            @Field("vendor_id") String vendor_id
    );


    @FormUrlEncoded
    @POST("vendor/update_vendor_on_off")
    Call<JsonObject>VendorOnOFF(
            @Header("Authorization") String auth,
            @Field("is_vendor_on") String is_vendor_on,
            @Field("vendor_id") String vendor_id
    );


    @FormUrlEncoded
    @POST("vendor/get_all_services")
    Call<GetAllSrviceResponse>getAllServices(
            @Header("Authorization") String auth,
            @Field("vendor_id") String vendor_id);

    //new code
    @FormUrlEncoded
    @POST("vendor/get_all_services_by_id")
    Call<GetAllServiceIdResponse>getAllServicesById(
            @Header("Authorization") String auth,
            @Field("vendor_id") String vendor_id,
            @Field("service_id") String service_id);


    @FormUrlEncoded
    @POST("vendor/complete_order")
    Call<CompleteOrderRes>CompleteService(
            @Header("Authorization") String auth,
            @Field("order_id") String order_id,
            @Field("vendor_id") String vendor_id
    );


   // @FormUrlEncoded
    @POST("lk_services/get_all_categories_services")
    Call<AllCategoryServiceRes>getAllCategService();



    @FormUrlEncoded
    @POST("vendor/add_vendor_services")
    Call<JsonObject>AddServiceApi(
            @Field("vendor_id") String vendor_id,
            @Field("cat_id") String cat_id,
            @Field("sub_cat_id") String sub_cat_id,
            @Field("service_id") String service_id,
            @Field("sub_service_ids") String sub_service_ids
    );



    @FormUrlEncoded
    @POST("vendor/add_vendor_services")
    Call<JsonObject>getDistanceDuration();

    @FormUrlEncoded
    @POST("vendor/get_all_vendor_services")
    Call<GetAllVendorService>getAllVendorService(
            @Field("vendor_id") String vendor_id
    );

    @FormUrlEncoded
    @POST("users/vendor_service_off\n")
    Call<VendorServiceOff>getVenderServiceOff(
            @Field("user_id") String vendor_id
    );

    @FormUrlEncoded
    @POST("vendor/delete_vendor_service")
    Call<JsonObject>deleteVendorService(
            @Field("id") String vendor_id
    );

    @FormUrlEncoded
    @POST("push/add_deviceid_android")
    Call<JsonObject> sendPUSHTOKEN(
            @Header("Authorization") String auth,
            @Field("user_id") String user_id,
            @Field("imei_no") String imei_no,
            @Field("device_token") String device_token
    );

    @FormUrlEncoded
    @POST("users/get_user_details_by_id")
    Call<JsonObject>getUserDetails(
            @Header("Authorization") String auth,
            @Field("user_id") String vendor_id
    );


    @FormUrlEncoded
    @POST("users/check_mobile_no")
    Call<JsonObject>VerifyMobile(
            @Field("mobile_no") String mobile_no
    );


    @FormUrlEncoded
    @POST("users/change_password")
    Call<JsonObject>ChangePassword(
            @Header("Authorization") String auth,
            @Field("email") String email,
            @Field("new_password") String new_password
    );

    @FormUrlEncoded
    @POST("users/forgot_password")
    Call<JsonObject>ForgetPasswordEmail(
            @Field("email") String email

    );

    @FormUrlEncoded
    @POST("users/change_forgot_password")
    Call<JsonObject>NewForgetPassword(

            @Field("flag") String flag,
            @Field("mobile_no") String mobile_no,
            @Field("new_password") String new_password

    );



}
