package com.DailyNeeds.dailyneeds.NetworkCall.ResModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfilrImageData {

    @SerializedName("uploaded_data")
    @Expose
    private List<CategoryImage> uploadedData = null;
    @SerializedName("message")
    @Expose
    private String message;

    public List<CategoryImage> getUploadedData() {
        return uploadedData;
    }

    public void setUploadedData(List<CategoryImage> uploadedData) {
        this.uploadedData = uploadedData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
