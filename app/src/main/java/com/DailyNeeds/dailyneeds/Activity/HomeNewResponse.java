package com.DailyNeeds.dailyneeds.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeNewResponse {

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

        @SerializedName("total_services")
        @Expose
        private Integer totalServices;
        @SerializedName("total_completed_services")
        @Expose
        private Integer totalCompletedServices;
        @SerializedName("total_active_services")
        @Expose
        private Integer totalActiveServices;
        @SerializedName("total_pending_services")
        @Expose
        private Integer totalPendingServices;
        @SerializedName("vendor_points")
        @Expose
        private String vendorPoints;
        @SerializedName("is_vendor_on")
        @Expose
        private String isVendorOn;
        @SerializedName("is_vendor_suspend")
        @Expose
        private String isVendorSuspend;
        @SerializedName("assigned_services")
        @Expose
        private List<AssignedService> assignedServices = null;
        @SerializedName("category_images")
        @Expose
        private List<Object> categoryImages = null;
        @SerializedName("message")
        @Expose
        private String message;

        public Integer getTotalServices() {
        return totalServices;
    }

        public void setTotalServices(Integer totalServices) {
        this.totalServices = totalServices;
    }

        public Integer getTotalCompletedServices() {
        return totalCompletedServices;
    }

        public void setTotalCompletedServices(Integer totalCompletedServices) {
        this.totalCompletedServices = totalCompletedServices;
    }

        public Integer getTotalActiveServices() {
        return totalActiveServices;
    }

        public void setTotalActiveServices(Integer totalActiveServices) {
        this.totalActiveServices = totalActiveServices;
    }

        public Integer getTotalPendingServices() {
        return totalPendingServices;
    }

        public void setTotalPendingServices(Integer totalPendingServices) {
        this.totalPendingServices = totalPendingServices;
    }

        public String getVendorPoints() {
        return vendorPoints;
    }

        public void setVendorPoints(String vendorPoints) {
        this.vendorPoints = vendorPoints;
    }

        public String getIsVendorOn() {
        return isVendorOn;
    }

        public void setIsVendorOn(String isVendorOn) {
        this.isVendorOn = isVendorOn;
    }

        public String getIsVendorSuspend() {
        return isVendorSuspend;
    }

        public void setIsVendorSuspend(String isVendorSuspend) {
        this.isVendorSuspend = isVendorSuspend;
    }

        public List<AssignedService> getAssignedServices() {
        return assignedServices;
    }

        public void setAssignedServices(List<AssignedService> assignedServices) {
        this.assignedServices = assignedServices;
    }

        public List<Object> getCategoryImages() {
        return categoryImages;
    }

        public void setCategoryImages(List<Object> categoryImages) {
        this.categoryImages = categoryImages;
    }

        public String getMessage() {
        return message;
    }

        public void setMessage(String message) {
        this.message = message;
    }


        public class AssignedService {

            @SerializedName("order_id")
            @Expose
            private String orderId;
            @SerializedName("is_vendor_accepted")
            @Expose
            private String isVendorAccepted;
            @SerializedName("is_order_completed")
            @Expose
            private String isOrderCompleted;
            @SerializedName("user_id")
            @Expose
            private String userId;
            @SerializedName("service_id")
            @Expose
            private String serviceId;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("zipcode")
            @Expose
            private String zipcode;
            @SerializedName("latitude")
            @Expose
            private String latitude;
            @SerializedName("longitude")
            @Expose
            private String longitude;
            @SerializedName("user_first_name")
            @Expose
            private String userFirstName;
            @SerializedName("service")
            @Expose
            private String service;
            @SerializedName("distance")
            @Expose
            private String distance;

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public String getIsVendorAccepted() {
                return isVendorAccepted;
            }

            public void setIsVendorAccepted(String isVendorAccepted) {
                this.isVendorAccepted = isVendorAccepted;
            }

            public String getIsOrderCompleted() {
                return isOrderCompleted;
            }

            public void setIsOrderCompleted(String isOrderCompleted) {
                this.isOrderCompleted = isOrderCompleted;
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

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getZipcode() {
                return zipcode;
            }

            public void setZipcode(String zipcode) {
                this.zipcode = zipcode;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getUserFirstName() {
                return userFirstName;
            }

            public void setUserFirstName(String userFirstName) {
                this.userFirstName = userFirstName;
            }

            public String getService() {
                return service;
            }

            public void setService(String service) {
                this.service = service;
            }

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

        }




    }


}
