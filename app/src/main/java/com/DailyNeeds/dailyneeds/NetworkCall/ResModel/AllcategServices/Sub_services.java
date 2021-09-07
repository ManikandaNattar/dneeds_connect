package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices;

public class Sub_services {

    private String sub_service;

    private String price;

    private String service_id;

    private String description;

    private String id;

    private String modified_datetime;

    private String created_datetime;

    private Sub_service_images[] sub_service_images;

    private String points;

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

    public String getService_id ()
    {
        return service_id;
    }

    public void setService_id (String service_id)
    {
        this.service_id = service_id;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
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

    public Sub_service_images[] getSub_service_images ()
    {
        return sub_service_images;
    }

    public void setSub_service_images (Sub_service_images[] sub_service_images)
    {
        this.sub_service_images = sub_service_images;
    }

    public String getPoints ()
    {
        return points;
    }

    public void setPoints (String points)
    {
        this.points = points;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [sub_service = "+sub_service+", price = "+price+", service_id = "+service_id+", description = "+description+", id = "+id+", modified_datetime = "+modified_datetime+", created_datetime = "+created_datetime+", sub_service_images = "+sub_service_images+", points = "+points+"]";
    }


}
