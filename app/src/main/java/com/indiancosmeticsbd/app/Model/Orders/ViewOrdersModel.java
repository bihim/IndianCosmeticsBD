package com.indiancosmeticsbd.app.Model.Orders;

public class ViewOrdersModel
{
    private String orderNumber, orderDate;

    public ViewOrdersModel(String orderNumber, String orderDate) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
