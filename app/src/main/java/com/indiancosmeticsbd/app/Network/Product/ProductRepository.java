package com.indiancosmeticsbd.app.Network.Product;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.indiancosmeticsbd.app.Views.Dialogs.LoadingDialog;
import com.indiancosmeticsbd.app.Model.ProductList.Products;
import com.indiancosmeticsbd.app.Network.RetrofitService;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.PRODUCT_LIST;

public class ProductRepository {
    private static ProductRepository productRepository;
    private final ProductApi productApi;

    public static ProductRepository getInstance() {
        if (productRepository == null) {
            productRepository = new ProductRepository();
        }
        return productRepository;
    }

    public ProductRepository() {
        productApi = RetrofitService.createService(ProductApi.class);
    }

    public MutableLiveData<Products> getProductList(Activity activity, String fetchScope, String categoryId, String sort, String search, String productLimit, String productOffset, String filters, boolean isSearch) {
        MutableLiveData<Products> productsMutableLiveData = new MutableLiveData<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        if (!isSearch){
            loadingDialog.showDialog();
        }

        productApi.getProductsList(API_TOKEN, PRODUCT_LIST, fetchScope, categoryId, sort, search, productLimit, productOffset, filters).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response.isSuccessful()){
                    if (response.body()!=null)
                    {
                        String status = response.body().getStatus();
                        if (status.equals("SUCCESS")){
                            if (!isSearch){
                                loadingDialog.dismissDialog();
                            }
                            productsMutableLiveData.setValue(response.body());
                        }
                        else {
                            Toasty.error(activity, "Status Failed", Toasty.LENGTH_SHORT).show();
                            productsMutableLiveData.setValue(null);
                        }
                    }
                    else {
                        Toasty.error(activity, "Body null", Toasty.LENGTH_SHORT).show();
                        productsMutableLiveData.setValue(null);
                    }
                }
                else {
                    Toasty.error(activity, "Response not successful", Toasty.LENGTH_SHORT).show();
                    productsMutableLiveData.setValue(null);
                }
                if (!isSearch)
                {
                    loadingDialog.dismissDialog();
                }
            }
            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                productsMutableLiveData.setValue(null);
                Toasty.error(activity, "ERROR OCCURRED", Toasty.LENGTH_SHORT).show();
                if (isSearch){
                    loadingDialog.dismissDialog();
                }

            }
        });
        return productsMutableLiveData;
    }
}
