package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.SignupResponse;

import java.util.List;

public class Data {

    private String access_token;

    private User_details[] user_details;

    private String id;

    private String message;

    public String getAccess_token ()
    {
        return access_token;
    }

    public void setAccess_token (String access_token)
    {
        this.access_token = access_token;
    }

    public User_details[] getUser_details ()
    {
        return user_details;
    }

    public void setUser_details (User_details[] user_details)
    {
        this.user_details = user_details;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [access_token = "+access_token+", user_details = "+user_details+", id = "+id+", message = "+message+"]";
    }}
