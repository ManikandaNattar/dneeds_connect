package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes;

public class Sub_services {

    private String sub_service;

    private String price;

    public String getSub_service ()
    {
        return sub_service;
    }

    public void setSub_service (String sub_service)
    {
        this.sub_service = sub_service;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sub_service = "+sub_service+", price = "+price+"]";
    }
}
