package com.indiancosmeticsbd.app.Model.ProductList;


public class ProductListModel
{
    private Integer id;
    private String name;
    private String brand;
    private Integer price;
    private Integer views;
    private Integer stock;
    private Integer discount;
    private String thumbnail;

    public ProductListModel(Integer id, String name, String brand, Integer price, Integer views, Integer stock, Integer discount, String thumbnail) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.views = views;
        this.stock = stock;
        this.discount = discount;
        this.thumbnail = thumbnail;
    }

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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
