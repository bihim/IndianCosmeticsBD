package com.indiancosmeticsbd.app.Model.ProductDetails;

public class AddToCartModel
{
    private String productId, brandName, productName, price, quantity, discount, size, imageUrl;

    public AddToCartModel(String productId, String brandName, String productName, String price, String quantity, String discount, String size, String imageUrl) {
        this.productId = productId;
        this.brandName = brandName;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
        this.size = size;
        this.imageUrl = imageUrl;
    }


    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
