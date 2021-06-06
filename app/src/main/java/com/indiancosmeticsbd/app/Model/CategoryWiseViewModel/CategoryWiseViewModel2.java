package com.indiancosmeticsbd.app.Model.CategoryWiseViewModel;

import com.indiancosmeticsbd.app.Model.ProductList.ProductListModel;

import java.util.ArrayList;

public class CategoryWiseViewModel2
{
    private String id, name;
    private ArrayList<ProductListModel> productListModels;

    public CategoryWiseViewModel2(String id, String name, ArrayList<ProductListModel> productListModels) {
        this.id = id;
        this.name = name;
        this.productListModels = productListModels;
    }

    public ArrayList<ProductListModel> getProductListModels() {
        return productListModels;
    }

    public void setProductListModels(ArrayList<ProductListModel> productListModels) {
        this.productListModels = productListModels;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return titleText(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    private String titleText(String title){
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
