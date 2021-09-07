package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.getAllServiceRes;

public class Data {

    private Assigned_services[] assigned_services;

    private String message;

    public Assigned_services[] getAssigned_services ()
    {
        return assigned_services;
    }

    public void setAssigned_services (Assigned_services[] assigned_services)
    {
        this.assigned_services = assigned_services;
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
        return "ClassPojo [assigned_services = "+assigned_services+", message = "+message+"]";
    }
}
