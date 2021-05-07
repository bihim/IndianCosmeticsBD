package com.indiancosmeticsbd.app.Network.ProductCategories;

import com.indiancosmeticsbd.app.R;

public class ProductCategoriesModelAdapter {
    private String title;

    public ProductCategoriesModelAdapter(String title) {
        this.title = title;
    }

    public String getTitle() {
        {
            if (title.equals("keya-seth-aromatherapy")) {
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
            }
        }
    }

    public int getImageLink() {
        switch (getTitle()) {
            case "Indian products":
                return R.drawable.india;
            case "Thai products":
                return R.drawable.thai;
            case "The soumi's can products":
                return R.drawable.soumi;
            default:
                return R.drawable.keya;
        }
    }
}
