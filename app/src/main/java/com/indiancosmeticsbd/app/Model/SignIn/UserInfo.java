package com.indiancosmeticsbd.app.Model.SignIn;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
public class UserInfo {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("errorno")
    @Expose
    private String errorno;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("content")
    @Expose
    private Content content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorno() {
        return errorno;
    }

    public void setErrorno(String errorno) {
        this.errorno = errorno;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public static class Content {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("joined")
        @Expose
        private String joined;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("token")
        @Expose
        private String token;
        @SerializedName("first_name")
        @Expose
        private String firstName;
        @SerializedName("last_name")
        @Expose
        private String lastName;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("district")
        @Expose
        private String district;
        @SerializedName("country")
        @Expose
        private String country;
        @SerializedName("postalcode")
        @Expose
        private String postalcode;
        @SerializedName("mobile_number")
        @Expose
        private String mobileNumber;
        @SerializedName("wishlists")
        @Expose
        private List<String> wishlists = null;
        @SerializedName("notifications")
        @Expose
        private List<Notification> notifications = null;
        @SerializedName("customer_orders")
        @Expose
        private List<CustomerOrder> customerOrders = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getJoined() {
            return joined;
        }

        public void setJoined(String joined) {
            this.joined = joined;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPostalcode() {
            return postalcode;
        }

        public void setPostalcode(String postalcode) {
            this.postalcode = postalcode;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public List<String> getWishlists() {
            return wishlists;
        }

        public void setWishlists(List<String> wishlists) {
            this.wishlists = wishlists;
        }

        public List<Notification> getNotifications() {
            return notifications;
        }

        public void setNotifications(List<Notification> notifications) {
            this.notifications = notifications;
        }

        public List<CustomerOrder> getCustomerOrders() {
            return customerOrders;
        }

        public void setCustomerOrders(List<CustomerOrder> customerOrders) {
            this.customerOrders = customerOrders;
        }


    }
    public static class CustomerOrder {

        @SerializedName("order_no")
        @Expose
        private String orderNo;
        @SerializedName("date")
        @Expose
        private String date;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }


    }
    public static class Notification {

        @SerializedName("notificationType")
        @Expose
        private String notificationType;
        @SerializedName("notificationText")
        @Expose
        private String notificationText;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("status")
        @Expose
        private String status;

        public String getNotificationType() {
            return notificationType;
        }

        public void setNotificationType(String notificationType) {
            this.notificationType = notificationType;
        }

        public String getNotificationText() {
            return notificationText;
        }

        public void setNotificationText(String notificationText) {
            this.notificationText = notificationText;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }
}