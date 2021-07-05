package com.indiancosmeticsbd.app.Model.Orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class OrderStatusModel {

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

    public class Content {

        @SerializedName("order_no")
        @Expose
        private Integer orderNo;
        @SerializedName("order_date")
        @Expose
        private String orderDate;
        @SerializedName("customer_name")
        @Expose
        private String customerName;
        @SerializedName("customer_phone")
        @Expose
        private String customerPhone;
        @SerializedName("customer_username")
        @Expose
        private String customerUsername;
        @SerializedName("customer_address")
        @Expose
        private String customerAddress;
        @SerializedName("delivery_location")
        @Expose
        private String deliveryLocation;
        @SerializedName("shipping_info")
        @Expose
        private ShippingInfo shippingInfo;
        @SerializedName("payment_info")
        @Expose
        private PaymentInfo paymentInfo;
        @SerializedName("coupon_info")
        @Expose
        private CouponInfo couponInfo;
        @SerializedName("products")
        @Expose
        private List<Product> products = null;
        @SerializedName("order_status")
        @Expose
        private OrderStatus orderStatus;
        @SerializedName("order_summery")
        @Expose
        private OrderSummery orderSummery;
        @SerializedName("invoice_url")
        @Expose
        private String invoiceUrl;

        public Integer getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(Integer orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderDate() {
            return orderDate;
        }

        public void setOrderDate(String orderDate) {
            this.orderDate = orderDate;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public String getCustomerPhone() {
            return customerPhone;
        }

        public void setCustomerPhone(String customerPhone) {
            this.customerPhone = customerPhone;
        }

        public String getCustomerUsername() {
            return customerUsername;
        }

        public void setCustomerUsername(String customerUsername) {
            this.customerUsername = customerUsername;
        }

        public String getCustomerAddress() {
            return customerAddress;
        }

        public void setCustomerAddress(String customerAddress) {
            this.customerAddress = customerAddress;
        }

        public String getDeliveryLocation() {
            return deliveryLocation;
        }

        public void setDeliveryLocation(String deliveryLocation) {
            this.deliveryLocation = deliveryLocation;
        }

        public ShippingInfo getShippingInfo() {
            return shippingInfo;
        }

        public void setShippingInfo(ShippingInfo shippingInfo) {
            this.shippingInfo = shippingInfo;
        }

        public PaymentInfo getPaymentInfo() {
            return paymentInfo;
        }

        public void setPaymentInfo(PaymentInfo paymentInfo) {
            this.paymentInfo = paymentInfo;
        }

        public CouponInfo getCouponInfo() {
            return couponInfo;
        }

        public void setCouponInfo(CouponInfo couponInfo) {
            this.couponInfo = couponInfo;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }

        public OrderStatus getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
        }

        public OrderSummery getOrderSummery() {
            return orderSummery;
        }

        public void setOrderSummery(OrderSummery orderSummery) {
            this.orderSummery = orderSummery;
        }

        public String getInvoiceUrl() {
            return invoiceUrl;
        }

        public void setInvoiceUrl(String invoiceUrl) {
            this.invoiceUrl = invoiceUrl;
        }
    }

    public class CouponInfo {

        @SerializedName("coupon_id")
        @Expose
        private Object couponId;
        @SerializedName("coupon_code")
        @Expose
        private Object couponCode;
        @SerializedName("discount")
        @Expose
        private Integer discount;

        public Object getCouponId() {
            return couponId;
        }

        public void setCouponId(Object couponId) {
            this.couponId = couponId;
        }

        public Object getCouponCode() {
            return couponCode;
        }

        public void setCouponCode(Object couponCode) {
            this.couponCode = couponCode;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }
    }

    public class OrderStatus {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("description")
        @Expose
        private String description;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public class OrderSummery {

        @SerializedName("total_cost_without_discount")
        @Expose
        private Integer totalCostWithoutDiscount;
        @SerializedName("discount_total")
        @Expose
        private Integer discountTotal;
        @SerializedName("delivery_cost")
        @Expose
        private Integer deliveryCost;
        @SerializedName("coupon_discount")
        @Expose
        private Integer couponDiscount;

        public Integer getTotalCostWithoutDiscount() {
            return totalCostWithoutDiscount;
        }

        public void setTotalCostWithoutDiscount(Integer totalCostWithoutDiscount) {
            this.totalCostWithoutDiscount = totalCostWithoutDiscount;
        }

        public Integer getDiscountTotal() {
            return discountTotal;
        }

        public void setDiscountTotal(Integer discountTotal) {
            this.discountTotal = discountTotal;
        }

        public Integer getDeliveryCost() {
            return deliveryCost;
        }

        public void setDeliveryCost(Integer deliveryCost) {
            this.deliveryCost = deliveryCost;
        }

        public Integer getCouponDiscount() {
            return couponDiscount;
        }

        public void setCouponDiscount(Integer couponDiscount) {
            this.couponDiscount = couponDiscount;
        }
    }

    public class PaymentInfo {

        @SerializedName("payment_type")
        @Expose
        private String paymentType;
        @SerializedName("payment_trxn_id")
        @Expose
        private String paymentTrxnId;
        @SerializedName("payment_status")
        @Expose
        private String paymentStatus;

        public String getPaymentType() {
            return paymentType;
        }

        public void setPaymentType(String paymentType) {
            this.paymentType = paymentType;
        }

        public String getPaymentTrxnId() {
            return paymentTrxnId;
        }

        public void setPaymentTrxnId(String paymentTrxnId) {
            this.paymentTrxnId = paymentTrxnId;
        }

        public String getPaymentStatus() {
            return paymentStatus;
        }

        public void setPaymentStatus(String paymentStatus) {
            this.paymentStatus = paymentStatus;
        }
    }

    public static class Product {

        @SerializedName("productId")
        @Expose
        private Integer productId;
        @SerializedName("productSize")
        @Expose
        private String productSize;
        @SerializedName("productColor")
        @Expose
        private String productColor;
        @SerializedName("productQuantity")
        @Expose
        private Integer productQuantity;
        @SerializedName("productPrice")
        @Expose
        private Integer productPrice;
        @SerializedName("productImage")
        @Expose
        private String productImage;
        @SerializedName("productName")
        @Expose
        private String productName;

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public Integer getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(Integer productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductImage() {
            return productImage;
        }

        public void setProductImage(String productImage) {
            this.productImage = productImage;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getProductSize() {
            return productSize;
        }

        public void setProductSize(String productSize) {
            this.productSize = productSize;
        }

        public String getProductColor() {
            return productColor;
        }

        public void setProductColor(String productColor) {
            this.productColor = productColor;
        }

        public Integer getProductQuantity() {
            return productQuantity;
        }

        public void setProductQuantity(Integer productQuantity) {
            this.productQuantity = productQuantity;
        }
    }

    public class ShippingInfo {

        @SerializedName("priority")
        @Expose
        private String priority;
        @SerializedName("shipping_name")
        @Expose
        private String shippingName;
        @SerializedName("shipping_phone")
        @Expose
        private String shippingPhone;
        @SerializedName("shipping_address")
        @Expose
        private String shippingAddress;
        @SerializedName("method_id")
        @Expose
        private Integer methodId;
        @SerializedName("delivery_cost")
        @Expose
        private Integer deliveryCost;

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public String getShippingName() {
            return shippingName;
        }

        public void setShippingName(String shippingName) {
            this.shippingName = shippingName;
        }

        public String getShippingPhone() {
            return shippingPhone;
        }

        public void setShippingPhone(String shippingPhone) {
            this.shippingPhone = shippingPhone;
        }

        public String getShippingAddress() {
            return shippingAddress;
        }

        public void setShippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
        }

        public Integer getMethodId() {
            return methodId;
        }

        public void setMethodId(Integer methodId) {
            this.methodId = methodId;
        }

        public Integer getDeliveryCost() {
            return deliveryCost;
        }

        public void setDeliveryCost(Integer deliveryCost) {
            this.deliveryCost = deliveryCost;
        }

    }
}



