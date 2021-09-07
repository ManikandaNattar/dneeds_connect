package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices;

public class Services {

    private String service;

    private String price;

    private String sub_cat_id;

    private String cat_id;

    private String allow_multi_service;

    private String id;

    private String modified_datetime;

    private String created_datetime;

    public String getService ()
    {
        return service;
    }

    public void setService (String service)
    {
        this.service = service;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getSub_cat_id ()
    {
        return sub_cat_id;
    }

    public void setSub_cat_id (String sub_cat_id)
    {
        this.sub_cat_id = sub_cat_id;
    }

    public String getCat_id ()
    {
        return cat_id;
    }

    public void setCat_id (String cat_id)
    {
        this.cat_id = cat_id;
    }

    public String getAllow_multi_service ()
    {
        return allow_multi_service;
    }

    public void setAllow_multi_service (String allow_multi_service)
    {
        this.allow_multi_service = allow_multi_service;
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
        return "ClassPojo [service = "+service+", price = "+price+", sub_cat_id = "+sub_cat_id+", cat_id = "+cat_id+", allow_multi_service = "+allow_multi_service+", id = "+id+", modified_datetime = "+modified_datetime+", created_datetime = "+created_datetime+"]";
    }
}
