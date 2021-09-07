package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.VendorServiceRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VendorServiceOff {

    @SerializedName("status_code")
    @Expose
    public String statusCode;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("service_name")
    @Expose
    public String serviceName;
    @SerializedName("data")
    @Expose
    public Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}