package com.indiancosmeticsbd.app.Network.ProductInfo;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.indiancosmeticsbd.app.Views.Dialogs.LoadingDialog;
import com.indiancosmeticsbd.app.Model.ProductDetails.ProductInfo;
import com.indiancosmeticsbd.app.Network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.PRODUCT_INFO;

public class ProductInfoRepository
{
    private static ProductInfoRepository productInfoRepository;
    private final ProductInfoApi productInfoApi;

    public static ProductInfoRepository getInstance(){
        if (productInfoRepository == null){
            productInfoRepository = new ProductInfoRepository();
        }
        return productInfoRepository;
    }

    public ProductInfoRepository(){
        productInfoApi = RetrofitService.createService(ProductInfoApi.class);
    }

    public MutableLiveData<ProductInfo> getProductInfo(Activity activity, String productId){
        MutableLiveData<ProductInfo> productInfoMutableLiveData = new MutableLiveData<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.showDialog();
        productInfoApi.getProductsList(API_TOKEN, PRODUCT_INFO, productId).enqueue(new Callback<ProductInfo>() {
            @Override
            public void onResponse(Call<ProductInfo> call, Response<ProductInfo> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        ProductInfo productInfo = response.body();
                        Log.d("PRODUCT_DETAILS", "onResponse: response: "+productInfo.getStatus());
                        Log.d("PRODUCT_DETAILS", "onResponse: response: "+productInfo.getErrorno());
                        Log.d("PRODUCT_DETAILS", "onResponse: response: "+productInfo.getError());
                        Log.d("PRODUCT_DETAILS", "onResponse: response: "+productInfo.getDescription());
                        if (productInfo.getStatus().equals("SUCCESS")){
                            productInfoMutableLiveData.setValue(response.body());
                            loadingDialog.dismissDialog();
                        }
                        else{
                            productInfoMutableLiveData.setValue(null);
                        }
                    }
                    else {
                        productInfoMutableLiveData.setValue(null);
                    }
                }
                else {
                    productInfoMutableLiveData.setValue(null);
                }
                loadingDialog.dismissDialog();
            }
            @Override
            public void onFailure(Call<ProductInfo> call, Throwable t) {
                productInfoMutableLiveData.setValue(null);
                loadingDialog.dismissDialog();
            }
        });
        return productInfoMutableLiveData;
    }
}
