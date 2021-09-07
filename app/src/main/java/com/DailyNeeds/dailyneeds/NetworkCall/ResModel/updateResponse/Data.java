package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.updateResponse;

public class Data {

    private Updated_details updated_details;

    private String message;

    public Updated_details getUpdated_details ()
    {
        return updated_details;
    }

    public void setUpdated_details (Updated_details updated_details)
    {
        this.updated_details = updated_details;
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
        return "ClassPojo [updated_details = "+updated_details+", message = "+message+"]";
    }

}
