package com.DailyNeeds.dailyneeds.Utils;


public class Assigned_servicesNew{

    private String zipcode;

    private String user_first_name;

    private String address;

    private String is_vendor_accepted;

    private String user_id;

    private String service;

    private String service_id;

    private String order_id;

    private String duration;

    private String distance;

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    private String lat;

    private String lng;

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    private String is_order_completed;


    public String getZipcode ()
    {
        return zipcode;
    }

    public void setZipcode (String zipcode)
    {
        this.zipcode = zipcode;
    }

    public String getUser_first_name ()
    {
        return user_first_name;
    }

    public void setUser_first_name (String user_first_name)
    {
        this.user_first_name = user_first_name;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getIs_vendor_accepted ()
    {
        return is_vendor_accepted;
    }

    public void setIs_vendor_accepted (String is_vendor_accepted)
    {
        this.is_vendor_accepted = is_vendor_accepted;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public void setUser_id (String user_id)
    {
        this.user_id = user_id;
    }

    public String getService ()
    {
        return service;
    }

    public void setService (String service)
    {
        this.service = service;
    }

    public String getService_id ()
    {
        return service_id;
    }

    public void setService_id (String service_id)
    {
        this.service_id = service_id;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    public String getLat ()
    {
        return lat;
    }

    public void setLat (String lat)
    {
        this.lat = lat;
    }


    public String getIs_order_completed ()
    {
        return is_order_completed;
    }

    public void setIs_order_completed (String is_order_completed)
    {
        this.is_order_completed = is_order_completed;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [zipcode = "+zipcode+", user_first_name = "+user_first_name+", address = "+address+", is_vendor_accepted = "+is_vendor_accepted+", user_id = "+user_id+", service = "+service+", service_id = "+service_id+", order_id = "+order_id+","+is_order_completed+"]";
    }
}
