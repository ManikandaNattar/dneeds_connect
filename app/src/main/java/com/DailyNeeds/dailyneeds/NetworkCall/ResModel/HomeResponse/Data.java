package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.HomeResponse;

public class Data {



    private String total_services;

    private Assigned_services[] assigned_services;

    private String total_pending_services;

    private String total_completed_services;

    private String message;

    private String total_active_services;

    private String vendor_points;

    private String is_vendor_on;

    private String is_vendor_suspend;

    public Category_images[] getCategory_images() {
        return category_images;
    }

    public void setCategory_images(Category_images[] category_images) {
        this.category_images = category_images;
    }

    private Category_images[] category_images;



    public String getTotal_services ()
    {
        return total_services;
    }

    public void setTotal_services (String total_services)
    {
        this.total_services = total_services;
    }

    public String getVendor_points() {
        return vendor_points;
    }

    public void setVendor_points(String vendor_points) {
        this.vendor_points = vendor_points;
    }

    public String getIs_vendor_on() {
        return is_vendor_on;
    }

    public void setIs_vendor_on(String is_vendor_on) {
        this.is_vendor_on = is_vendor_on;
    }

    public Assigned_services[] getAssigned_services ()
    {
        return assigned_services;
    }


    public void setAssigned_services (Assigned_services[] assigned_services)
    {
        this.assigned_services = assigned_services;
    }

    public String getTotal_pending_services ()
    {
        return total_pending_services;
    }

    public void setTotal_pending_services (String total_pending_services)
    {
        this.total_pending_services = total_pending_services;
    }

    public String getTotal_completed_services ()
    {
        return total_completed_services;
    }

    public void setTotal_completed_services (String total_completed_services)
    {
        this.total_completed_services = total_completed_services;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
    }

    public String getTotal_active_services ()
    {
        return total_active_services;
    }

    public void setTotal_active_services (String total_active_services)
    {
        this.total_active_services = total_active_services;
    }


    public String getIs_vendor_suspend ()
    {
        return is_vendor_suspend;
    }

    public void setIs_vendor_suspend (String is_vendor_suspend)
    {
        this.is_vendor_suspend = is_vendor_suspend;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total_services = "+total_services+", assigned_services = "+assigned_services+", is_vendor_suspend = "+is_vendor_suspend+", vendor_points = "+vendor_points+", total_pending_services = "+total_pending_services+", total_completed_services = "+total_completed_services+", message = "+message+", is_vendor_on = "+is_vendor_on+", total_active_services = "+total_active_services+", category_images = "+category_images+"]";
    }


}
