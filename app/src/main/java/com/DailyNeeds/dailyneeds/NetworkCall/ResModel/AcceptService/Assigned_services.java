package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AcceptService;

public class Assigned_services {

    private String issue_description;

    private String address;

    private String is_vendor_accepted;

    private String lon;

    private String service_request_date;

    private String is_order_completed;

    private String issue_image_url;

    private String zipcode;

    private String service_type;

    private String user_first_name;

    private String user_id;

    private String service;

    private String service_id;

    private Sub_services[] sub_services;

    private String chat_room_id;

    private String sub_service_ids;

    private String id;

    private String order_id;

    private String lat;

    private String mobile_no;

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getIssue_description ()
    {
        return issue_description;
    }

    public void setIssue_description (String issue_description)
    {
        this.issue_description = issue_description;
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

    public String getLon ()
    {
        return lon;
    }

    public void setLon (String lon)
    {
        this.lon = lon;
    }

    public String getService_request_date ()
    {
        return service_request_date;
    }

    public void setService_request_date (String service_request_date)
    {
        this.service_request_date = service_request_date;
    }

    public String getIs_order_completed ()
    {
        return is_order_completed;
    }

    public void setIs_order_completed (String is_order_completed)
    {
        this.is_order_completed = is_order_completed;
    }

    public String getIssue_image_url ()
    {
        return issue_image_url;
    }

    public void setIssue_image_url (String issue_image_url)
    {
        this.issue_image_url = issue_image_url;
    }

    public String getZipcode ()
    {
        return zipcode;
    }

    public void setZipcode (String zipcode)
    {
        this.zipcode = zipcode;
    }

    public String getService_type ()
    {
        return service_type;
    }

    public void setService_type (String service_type)
    {
        this.service_type = service_type;
    }

    public String getUser_first_name ()
    {
        return user_first_name;
    }

    public void setUser_first_name (String user_first_name)
    {
        this.user_first_name = user_first_name;
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

    public Sub_services[] getSub_services ()
    {
        return sub_services;
    }

    public void setSub_services (Sub_services[] sub_services)
    {
        this.sub_services = sub_services;
    }

    public String getChat_room_id ()
    {
        return chat_room_id;
    }

    public void setChat_room_id (String chat_room_id)
    {
        this.chat_room_id = chat_room_id;
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

    @Override
    public String toString()
    {
        return "ClassPojo [issue_description = "+issue_description+", address = "+address+", is_vendor_accepted = "+is_vendor_accepted+", lon = "+lon+", service_request_date = "+service_request_date+", is_order_completed = "+is_order_completed+", issue_image_url = "+issue_image_url+", zipcode = "+zipcode+", service_type = "+service_type+", user_first_name = "+user_first_name+", user_id = "+user_id+", service = "+service+", service_id = "+service_id+", sub_services = "+sub_services+", chat_room_id = "+chat_room_id+", sub_service_ids = "+sub_service_ids+", id = "+id+", order_id = "+order_id+", lat = "+lat+"]";
    }

}
