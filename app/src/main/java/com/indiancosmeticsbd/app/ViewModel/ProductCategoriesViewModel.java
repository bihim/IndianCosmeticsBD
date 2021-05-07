package com.indiancosmeticsbd.app.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesModel;
import com.indiancosmeticsbd.app.Network.ProductCategories.ProductCategoriesRepository;

public class ProductCategoriesViewModel extends ViewModel {
    private MutableLiveData<ProductCategoriesModel> mutableLiveData;

    public void init() {
        if (mutableLiveData != null){
            return;
        }
        ProductCategoriesRepository productCategoriesRepository = ProductCategoriesRepository.getInstance();
        mutableLiveData = productCategoriesRepository.getProductCategories("", "");
    }

    public LiveData<ProductCategoriesModel> getProductCategories(){
        return mutableLiveData;
    }
}
