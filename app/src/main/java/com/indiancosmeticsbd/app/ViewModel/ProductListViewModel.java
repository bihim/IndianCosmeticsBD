package com.indiancosmeticsbd.app.ViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indiancosmeticsbd.app.Model.ProductList.Products;
import com.indiancosmeticsbd.app.Network.Product.ProductRepository;

public class ProductListViewModel extends ViewModel {
    private MutableLiveData<Products> mutableLiveData;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
    }

    public LiveData<Products> getProductList(Activity activity, String fetchScope, String categoryId, String sort, String search, String productLimit, String productOffset, String filters){
        ProductRepository productRepository = ProductRepository.getInstance();
        mutableLiveData = productRepository.getProductList(activity, fetchScope, categoryId, sort, search, productLimit, productOffset, filters);
        return mutableLiveData;
    }
}
