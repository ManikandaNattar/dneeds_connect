package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.HomeResponse;

public class Category_images {

    private String img_url;

    public String getImg_url ()
    {
        return img_url;
    }

    public void setImg_url (String img_url)
    {
        this.img_url = img_url;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [img_url = "+img_url+"]";
    }

}
