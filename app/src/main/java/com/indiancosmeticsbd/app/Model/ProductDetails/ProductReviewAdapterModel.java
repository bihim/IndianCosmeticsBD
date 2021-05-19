package com.indiancosmeticsbd.app.Model.ProductDetails;

public class ProductReviewAdapterModel
{
    private String username, email, date, description;

    public ProductReviewAdapterModel(String username, String email, String date, String description) {
        this.username = username;
        this.email = email;
        this.date = date;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
