package com.indiancosmeticsbd.app.Network.ProductCategories;

import androidx.lifecycle.MutableLiveData;

import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesModel;
import com.indiancosmeticsbd.app.Network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.PRODUCT_CATEGORIES;

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

    public MutableLiveData<ProductCategoriesModel> getProductCategories(String main, String header){
        MutableLiveData<ProductCategoriesModel> productCategoriesLiveData = new MutableLiveData<>();
        productCategoriesApi.getProductCategories(API_TOKEN, PRODUCT_CATEGORIES, main, header).enqueue(new Callback<ProductCategoriesModel>() {
            @Override
            public void onResponse(Call<ProductCategoriesModel> call, Response<ProductCategoriesModel> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null){
                        ProductCategoriesModel productCategoriesModel = response.body();
                        if (productCategoriesModel.getStatus().equals("SUCCESS")){
                            productCategoriesLiveData.setValue(response.body());
                        }
                        else{
                            productCategoriesLiveData.setValue(null);
                        }
                    }
                    else{
                        productCategoriesLiveData.setValue(null);
                    }
                }
                else {
                    productCategoriesLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ProductCategoriesModel> call, Throwable t) {
                productCategoriesLiveData.setValue(null);
            }
        });
        return productCategoriesLiveData;
    }
}
