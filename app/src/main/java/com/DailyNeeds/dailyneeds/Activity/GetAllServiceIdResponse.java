package com.DailyNeeds.dailyneeds.Activity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetAllServiceIdResponse{

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

        @SerializedName("assigned_services")
        @Expose
        private List<AssignedService> assignedServices = null;
        @SerializedName("message")
        @Expose
        private String message;

        public List<AssignedService> getAssignedServices() {
            return assignedServices;
        }

        public void setAssignedServices(List<AssignedService> assignedServices) {
            this.assignedServices = assignedServices;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }



        public class AssignedService {

            @SerializedName("id")
            @Expose
            private String id;
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
            @SerializedName("sub_service_ids")
            @Expose
            private String subServiceIds;
            @SerializedName("service_type")
            @Expose
            private String serviceType;
            @SerializedName("service_request_date")
            @Expose
            private String serviceRequestDate;
            @SerializedName("issue_description")
            @Expose
            private String issueDescription;
            @SerializedName("issue_image_url")
            @Expose
            private String issueImageUrl;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("zipcode")
            @Expose
            private String zipcode;
            @SerializedName("lat")
            @Expose
            private String lat;
            @SerializedName("lon")
            @Expose
            private String lon;
            @SerializedName("user_first_name")
            @Expose
            private String userFirstName;
            @SerializedName("service")
            @Expose
            private String service;
            @SerializedName("chat_room_id")
            @Expose
            private String chatRoomId;
            @SerializedName("mobile_no")
            @Expose
            private String mobileNo;
            @SerializedName("sub_services")
            @Expose
            private List<SubService> subServices = null;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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

            public String getSubServiceIds() {
                return subServiceIds;
            }

            public void setSubServiceIds(String subServiceIds) {
                this.subServiceIds = subServiceIds;
            }

            public String getServiceType() {
                return serviceType;
            }

            public void setServiceType(String serviceType) {
                this.serviceType = serviceType;
            }

            public String getServiceRequestDate() {
                return serviceRequestDate;
            }

            public void setServiceRequestDate(String serviceRequestDate) {
                this.serviceRequestDate = serviceRequestDate;
            }

            public String getIssueDescription() {
                return issueDescription;
            }

            public void setIssueDescription(String issueDescription) {
                this.issueDescription = issueDescription;
            }

            public String getIssueImageUrl() {
                return issueImageUrl;
            }

            public void setIssueImageUrl(String issueImageUrl) {
                this.issueImageUrl = issueImageUrl;
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

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
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

            public String getChatRoomId() {
                return chatRoomId;
            }

            public void setChatRoomId(String chatRoomId) {
                this.chatRoomId = chatRoomId;
            }

            public String getMobileNo() {
                return mobileNo;
            }

            public void setMobileNo(String mobileNo) {
                this.mobileNo = mobileNo;
            }

            public List<SubService> getSubServices() {
                return subServices;
            }

            public void setSubServices(List<SubService> subServices) {
                this.subServices = subServices;
            }



            public class SubService {

                @SerializedName("service")
                @Expose
                private String service;
                @SerializedName("sub_service")
                @Expose
                private String subService;
                @SerializedName("price")
                @Expose
                private String price;

                public String getService() {
                    return service;
                }

                public void setService(String service) {
                    this.service = service;
                }

                public String getSubService() {
                    return subService;
                }

                public void setSubService(String subService) {
                    this.subService = subService;
                }

                public String getPrice() {
                    return price;
                }

                public void setPrice(String price) {
                    this.price = price;
                }

            }



        }



    }



}
