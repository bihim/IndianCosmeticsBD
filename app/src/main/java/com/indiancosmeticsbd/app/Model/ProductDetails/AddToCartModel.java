package com.indiancosmeticsbd.app.Model.ProductDetails;

public class AddToCartModel
{
    private String productId, size, color, quantity, price, imageUrl;

    public AddToCartModel(String productId, String size, String color, String quantity, String price, String imageUrl) {
        this.productId = productId;
        this.size = size;
        this.color = color;
        this.quantity = quantity;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public AddToCartModel() {
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
