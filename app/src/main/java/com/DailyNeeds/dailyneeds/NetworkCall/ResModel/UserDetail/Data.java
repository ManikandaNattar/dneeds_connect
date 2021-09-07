package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.UserDetail;

public class Data {

    private User_details[] user_details;

    private String message;

    public User_details[] getUser_details ()
    {
        return user_details;
    }

    public void setUser_details (User_details[] user_details)
    {
        this.user_details = user_details;
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
        return "ClassPojo [user_details = "+user_details+", message = "+message+"]";
    }

}
