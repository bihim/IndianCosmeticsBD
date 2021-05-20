package com.indiancosmeticsbd.app.Model.ProductDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ProductInfo {

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

        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("product_link")
        @Expose
        private String productLink;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("category")
        @Expose
        private ArrayList<Category> category = null;
        @SerializedName("brand")
        @Expose
        private String brand;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("stock")
        @Expose
        private Integer stock;
        @SerializedName("discount")
        @Expose
        private Integer discount;
        @SerializedName("available_sizes")
        @Expose
        private ArrayList<String> availableSizes = null;
        @SerializedName("available_colors")
        @Expose
        private ArrayList<String> availableColors = null;
        @SerializedName("thumnail_image")
        @Expose
        private String thumnailImage;
        @SerializedName("all_images")
        @Expose
        private AllImages allImages;
        @SerializedName("total_views")
        @Expose
        private Integer totalViews;
        @SerializedName("rating")
        @Expose
        private Rating rating;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("product_reviews")
        @Expose
        private ArrayList<ProductReview> productReviews = null;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public String getProductLink() {
            return productLink;
        }

        public void setProductLink(String productLink) {
            this.productLink = productLink;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Category> getCategory() {
            return category;
        }

        public void setCategory(ArrayList<Category> category) {
            this.category = category;
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

        public ArrayList<String> getAvailableSizes() {
            return availableSizes;
        }

        public void setAvailableSizes(ArrayList<String> availableSizes) {
            this.availableSizes = availableSizes;
        }

        public ArrayList<String> getAvailableColors() {
            return availableColors;
        }

        public void setAvailableColors(ArrayList<String> availableColors) {
            this.availableColors = availableColors;
        }

        public String getThumnailImage() {
            return thumnailImage;
        }

        public void setThumnailImage(String thumnailImage) {
            this.thumnailImage = thumnailImage;
        }

        public AllImages getAllImages() {
            return allImages;
        }

        public void setAllImages(AllImages allImages) {
            this.allImages = allImages;
        }

        public Integer getTotalViews() {
            return totalViews;
        }

        public void setTotalViews(Integer totalViews) {
            this.totalViews = totalViews;
        }

        public Rating getRating() {
            return rating;
        }

        public void setRating(Rating rating) {
            this.rating = rating;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public ArrayList<ProductReview> getProductReviews() {
            return productReviews;
        }

        public void setProductReviews(ArrayList<ProductReview> productReviews) {
            this.productReviews = productReviews;
        }

    }

    public static class Category {

        @SerializedName("category_id")
        @Expose
        private String categoryId;
        @SerializedName("category_main")
        @Expose
        private String categoryMain;
        @SerializedName("category_header")
        @Expose
        private String categoryHeader;
        @SerializedName("category_sub")
        @Expose
        private String categorySub;

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryMain() {
            return categoryMain;
        }

        public void setCategoryMain(String categoryMain) {
            this.categoryMain = categoryMain;
        }

        public String getCategoryHeader() {
            return categoryHeader;
        }

        public void setCategoryHeader(String categoryHeader) {
            this.categoryHeader = categoryHeader;
        }

        public String getCategorySub() {
            return categorySub;
        }

        public void setCategorySub(String categorySub) {
            this.categorySub = categorySub;
        }

    }

    public static class AllImages {

        @SerializedName("")
        @Expose
        private ArrayList<String> _default = null;

        public ArrayList<String> getDefault() {
            return _default;
        }

        public void setDefault(ArrayList<String> _default) {
            this._default = _default;
        }

    }

    public static class Rating {

        @SerializedName("total_reviewer")
        @Expose
        private Integer totalReviewer;
        @SerializedName("average_rating")
        @Expose
        private Double averageRating;

        public Integer getTotalReviewer() {
            return totalReviewer;
        }

        public void setTotalReviewer(Integer totalReviewer) {
            this.totalReviewer = totalReviewer;
        }

        public Double getAverageRating() {
            return averageRating;
        }

        public void setAverageRating(Double averageRating) {
            this.averageRating = averageRating;
        }

    }

    public static class ProductReview {

        @SerializedName("reviewId")
        @Expose
        private String reviewId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("reviewText")
        @Expose
        private String reviewText;
        @SerializedName("status")
        @Expose
        private String status;

        public String getReviewId() {
            return reviewId;
        }

        public void setReviewId(String reviewId) {
            this.reviewId = reviewId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getReviewText() {
            return reviewText;
        }

        public void setReviewText(String reviewText) {
            this.reviewText = reviewText;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

}
