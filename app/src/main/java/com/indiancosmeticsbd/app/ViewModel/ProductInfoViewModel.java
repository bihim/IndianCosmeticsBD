package com.indiancosmeticsbd.app.ViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indiancosmeticsbd.app.Model.ProductDetails.ProductInfo;
import com.indiancosmeticsbd.app.Network.ProductInfo.ProductInfoRepository;

public class ProductInfoViewModel extends ViewModel {
    private MutableLiveData<ProductInfo> mutableLiveData;

    public void init(Activity activity, String productId){
        if (mutableLiveData != null){
            return;
        }
        ProductInfoRepository productInfoRepository = ProductInfoRepository.getInstance();
        mutableLiveData = productInfoRepository.getProductInfo(activity, productId);
    }

    public LiveData<ProductInfo> getProductInfo(){
        return mutableLiveData;
    }
}
