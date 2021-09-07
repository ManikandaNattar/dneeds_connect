package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices;

public class Data {

    private Sub_services[] sub_services;

    private Categories[] categories;

    private Services[] services;

    private Subcategories[] subcategories;

    private String message;

    public Sub_services[] getSub_services ()
    {
        return sub_services;
    }

    public void setSub_services (Sub_services[] sub_services)
    {
        this.sub_services = sub_services;
    }

    public Categories[] getCategories ()
    {
        return categories;
    }

    public void setCategories (Categories[] categories)
    {
        this.categories = categories;
    }

    public Services[] getServices ()
    {
        return services;
    }

    public void setServices (Services[] services)
    {
        this.services = services;
    }

    public Subcategories[] getSubcategories ()
    {
        return subcategories;
    }

    public void setSubcategories (Subcategories[] subcategories)
    {
        this.subcategories = subcategories;
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
        return "ClassPojo [sub_services = "+sub_services+", categories = "+categories+", services = "+services+", subcategories = "+subcategories+", message = "+message+"]";
    }
}
