package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes;

public class Data {

    private Vendor_services[] vendor_services;

    private String message;

    public Vendor_services[] getVendor_services ()
    {
        return vendor_services;
    }

    public void setVendor_services (Vendor_services[] vendor_services)
    {
        this.vendor_services = vendor_services;
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
        return "ClassPojo [vendor_services = "+vendor_services+", message = "+message+"]";
    }
}
