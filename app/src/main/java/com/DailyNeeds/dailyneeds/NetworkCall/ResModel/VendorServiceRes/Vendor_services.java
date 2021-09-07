package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes;

public class Vendor_services {

    private String sub_category;

    private String service;

    private String vendor_id;

    private String sub_cat_id;

    private String service_id;

    private Sub_services[] sub_services;

    private String cat_id;

    private String sub_service_ids;

    private String id;

    private String modified_datetime;

    private String category;

    private String created_datetime;

    public String getSub_category ()
    {
        return sub_category;
    }

    public void setSub_category (String sub_category)
    {
        this.sub_category = sub_category;
    }

    public String getService ()
    {
        return service;
    }

    public void setService (String service)
    {
        this.service = service;
    }

    public String getVendor_id ()
    {
        return vendor_id;
    }

    public void setVendor_id (String vendor_id)
    {
        this.vendor_id = vendor_id;
    }

    public String getSub_cat_id ()
    {
        return sub_cat_id;
    }

    public void setSub_cat_id (String sub_cat_id)
    {
        this.sub_cat_id = sub_cat_id;
    }

    public String getService_id ()
    {
        return service_id;
    }

    public void setService_id (String service_id)
    {
        this.service_id = service_id;
    }

    public Sub_services[] getSub_services ()
    {
        return sub_services;
    }

    public void setSub_services (Sub_services[] sub_services)
    {
        this.sub_services = sub_services;
    }

    public String getCat_id ()
    {
        return cat_id;
    }

    public void setCat_id (String cat_id)
    {
        this.cat_id = cat_id;
    }

    public String getSub_service_ids ()
    {
        return sub_service_ids;
    }

    public void setSub_service_ids (String sub_service_ids)
    {
        this.sub_service_ids = sub_service_ids;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getModified_datetime ()
    {
        return modified_datetime;
    }

    public void setModified_datetime (String modified_datetime)
    {
        this.modified_datetime = modified_datetime;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getCreated_datetime ()
    {
        return created_datetime;
    }

    public void setCreated_datetime (String created_datetime)
    {
        this.created_datetime = created_datetime;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sub_category = "+sub_category+", service = "+service+", vendor_id = "+vendor_id+", sub_cat_id = "+sub_cat_id+", service_id = "+service_id+", sub_services = "+sub_services+", cat_id = "+cat_id+", sub_service_ids = "+sub_service_ids+", id = "+id+", modified_datetime = "+modified_datetime+", category = "+category+", created_datetime = "+created_datetime+"]";
    }
}
