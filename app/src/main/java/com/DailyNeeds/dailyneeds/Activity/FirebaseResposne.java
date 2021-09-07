package com.DailyNeeds.dailyneeds.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FirebaseResposne {

    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("view_id")
    @Expose
    private ViewId viewId;
    @SerializedName("message")
    @Expose
    private String message;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public ViewId getViewId() {
        return viewId;
    }

    public void setViewId(ViewId viewId) {
        this.viewId = viewId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class ViewId {

        @SerializedName("flag")
        @Expose
        private String flag;
        @SerializedName("order_id")
        @Expose
        private Integer orderId;
        @SerializedName("user_id")
        @Expose
        private String userId;
        @SerializedName("service_id")
        @Expose
        private String serviceId;

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getServiceId() {
            return serviceId;
        }

        public void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

    }

}