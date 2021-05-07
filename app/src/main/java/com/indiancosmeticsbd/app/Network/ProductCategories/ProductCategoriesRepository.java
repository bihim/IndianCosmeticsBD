package com.indiancosmeticsbd.app.Network.ProductCategories;

import androidx.lifecycle.MutableLiveData;

import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesModel;
import com.indiancosmeticsbd.app.Network.RetrofitService;

public class ProductCategoriesRepository
{
    private static ProductCategoriesRepository productCategoriesRepository;
    private  final ProductCategoriesApi productCategoriesApi;

    public static ProductCategoriesRepository getInstance(){
        if (productCategoriesRepository == null) {
            productCategoriesRepository = new ProductCategoriesRepository();
        }
        return productCategoriesRepository;
    }

    public ProductCategoriesRepository(){
        productCategoriesApi = RetrofitService.createService(ProductCategoriesApi.class);
    }

    public MutableLiveData<ProductCategoriesModel> getProductCategories(API_TOKEN, ){

    }
}
