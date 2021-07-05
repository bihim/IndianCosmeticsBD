package com.indiancosmeticsbd.app.Model.ProductCategories;

import com.indiancosmeticsbd.app.R;

public class ProductCategoriesAdapterModel {
    private final String title;
    private String imageLink;

    /*public ProductCategoriesAdapterModel(String title) {
        this.title = title;
    }*/

    public ProductCategoriesAdapterModel(String title, String imageLink) {
        this.title = title;
        this.imageLink = imageLink;
    }

    public String getTitle() {
        /*if (title.equals("keya-seth-aromatherapy")) {
            String[] titleSplet = title.split("-");
            StringBuilder bobTheBuilder = new StringBuilder();
            for (int i = 0; i < titleSplet.length; i++) {
                bobTheBuilder.append(Character.toUpperCase(titleSplet[i].charAt(0))).append(titleSplet[i].substring(1));
                if (titleSplet.length != i) {
                    bobTheBuilder.append(" ");
                } else {
                    break;
                }
            }
            return bobTheBuilder.toString();
        } else {
            return Character.toUpperCase(title.charAt(0)) + title.substring(1);
            //return title;
        }*/
        return title;
    }

    /*public int getImageLink() {
        switch (getTitle()) {
            case "indian products":
                return R.drawable.india;
            case "thai products":
                return R.drawable.thai;
            case "the soumi's can products":
                return R.drawable.soumi;
            default:
                return R.drawable.keya;
        }
    }*/

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }
}
