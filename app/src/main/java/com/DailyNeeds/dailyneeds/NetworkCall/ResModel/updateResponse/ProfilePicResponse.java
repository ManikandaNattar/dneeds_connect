package com.DailyNeeds.dailyneeds.NetworkCall.ResModel.updateResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfilePicResponse {
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
    private Data data;

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
    public class Data {

        @SerializedName("uploaded_data")
        @Expose
        private List<UploadedDatum> uploadedData = null;
        @SerializedName("message")
        @Expose
        private String message;

        public List<UploadedDatum> getUploadedData() {
            return uploadedData;
        }

        public void setUploadedData(List<UploadedDatum> uploadedData) {
            this.uploadedData = uploadedData;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }
    public class UploadedDatum {

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

}
