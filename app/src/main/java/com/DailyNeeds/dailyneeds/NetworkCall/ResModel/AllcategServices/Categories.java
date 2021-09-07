package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices;

public class Categories {

    private String display_order;

    private String id;

    private String modified_datetime;

    private String category;

    private String created_datetime;

    public String getDisplay_order ()
    {
        return display_order;
    }

    public void setDisplay_order (String display_order)
    {
        this.display_order = display_order;
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
        return "ClassPojo [display_order = "+display_order+", id = "+id+", modified_datetime = "+modified_datetime+", category = "+category+", created_datetime = "+created_datetime+"]";
    }
}
