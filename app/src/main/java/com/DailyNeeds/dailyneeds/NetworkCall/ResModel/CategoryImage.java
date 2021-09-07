package com.DailyNeeds.dailyneeds.NetworkCall.ResModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryImage {

    @SerializedName("photo_url")
    @Expose
    private String photoUrl;
    @SerializedName("type")
    @Expose
    private String type;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
