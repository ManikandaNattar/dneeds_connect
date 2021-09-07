package com.DailyNeeds.dailyneeds.NetworkCall.ResModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilrImageUploadResponse {

    @SerializedName("status_code")
    @Expose
    private String statusCode;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("service_name")
    @Expose
    private String serviceName;
    @SerializedName("data")
    @Expose
    private ProfilrImageData data;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public ProfilrImageData getData() {
        return data;
    }

    public void setData(ProfilrImageData data) {
        this.data = data;
    }
}
