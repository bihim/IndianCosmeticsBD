package com.indiancosmeticsbd.app.Model.BannerSlider;

public class SliderItem
{
    private String imageUrl;

    public SliderItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public SliderItem() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}