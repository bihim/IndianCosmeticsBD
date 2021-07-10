package com.indiancosmeticsbd.app.Model.Category;

import com.indiancosmeticsbd.app.R;

public class CategoryAdapterModel
{
    private String id, name;

    public CategoryAdapterModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon(){
        switch (getName()){
            case "skin products":
                return R.drawable.ic_skin_products;
            case "slimming products":
                return R.drawable.ic_slimming_products;
            case "body products":
                return R.drawable.ic_body_product;
            case "hair products":
                return R.drawable.ic_hair_products;
            default:
                return R.drawable.ic_cosmetics;
        }
    }
}
