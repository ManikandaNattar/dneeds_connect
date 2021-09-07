package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.Loginresponse;

import java.util.List;

public class Data {
    private String access_token;

    private String message;

    private User[] user;

    public String getAccess_token ()
    {
        return access_token;
    }

    public void setAccess_token (String access_token)
    {
        this.access_token = access_token;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public User[] getUser ()
    {
        return user;
    }

    public void setUser (User[] user)
    {
        this.user = user;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [access_token = "+access_token+", message = "+message+", user = "+user+"]";
    }
}