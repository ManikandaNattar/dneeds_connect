package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.AllcategServices;

public class Subcategories {

    private String sub_category;

    private String cat_id;

    private String display_order;

    private String id;

    private String modified_datetime;

    private String icon_class_name;

    private String created_datetime;

    public String getSub_category ()
    {
        return sub_category;
    }

    public void setSub_category (String sub_category)
    {
        this.sub_category = sub_category;
    }

    public String getCat_id ()
    {
        return cat_id;
    }

    public void setCat_id (String cat_id)
    {
        this.cat_id = cat_id;
    }

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

    public String getIcon_class_name ()
    {
        return icon_class_name;
    }

    public void setIcon_class_name (String icon_class_name)
    {
        this.icon_class_name = icon_class_name;
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
        return "ClassPojo [sub_category = "+sub_category+", cat_id = "+cat_id+", display_order = "+display_order+", id = "+id+", modified_datetime = "+modified_datetime+", icon_class_name = "+icon_class_name+", created_datetime = "+created_datetime+"]";
    }

}
