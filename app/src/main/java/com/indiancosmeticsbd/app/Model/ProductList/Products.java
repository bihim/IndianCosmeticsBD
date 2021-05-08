package com.indiancosmeticsbd.app.Model.ProductList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Products {

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
    private ArrayList<Content> content = null;

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

    public ArrayList<Content> getContent() {
        return content;
    }

    public void setContent(ArrayList<Content> content) {
        this.content = content;
    }

    public class Content {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("category_id")
        @Expose
        private ArrayList<String> categoryId = null;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("sizes")
        @Expose
        private ArrayList<String> sizes = null;
        @SerializedName("colors")
        @Expose
        private ArrayList<String> colors = null;
        @SerializedName("views")
        @Expose
        private Integer views;
        @SerializedName("stock")
        @Expose
        private Integer stock;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("added_date")
        @Expose
        private String addedDate;
        @SerializedName("rating")
        @Expose
        private Rating rating;
        @SerializedName("thumbnail")
        @Expose
        private String thumbnail;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<String> getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(ArrayList<String> categoryId) {
            this.categoryId = categoryId;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public ArrayList<String> getSizes() {
            return sizes;
        }

        public void setSizes(ArrayList<String> sizes) {
            this.sizes = sizes;
        }

        public ArrayList<String> getColors() {
            return colors;
        }

        public void setColors(ArrayList<String> colors) {
            this.colors = colors;
        }

        public Integer getViews() {
            return views;
        }

        public void setViews(Integer views) {
            this.views = views;
        }

        public Integer getStock() {
            return stock;
        }

        public void setStock(Integer stock) {
            this.stock = stock;
        }

        public Integer getDiscount() {
            return discount;
        }

        public void setDiscount(Integer discount) {
            this.discount = discount;
        }

        public String getAddedDate() {
            return addedDate;
        }

        public void setAddedDate(String addedDate) {
            this.addedDate = addedDate;
        }

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

    }

    public class Rating {

        @SerializedName("average_rating")
        @Expose
        private String averageRating;
        @SerializedName("total_reviewer")
        @Expose
        private String totalReviewer;

        public String getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(String averageRating) {
            this.averageRating = averageRating;
        }

        public String getTotalReviewer() {
            return totalReviewer;
        }

        public void setTotalReviewer(String totalReviewer) {
            this.totalReviewer = totalReviewer;
        }

    }

}
