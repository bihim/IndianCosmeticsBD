package com.indiancosmeticsbd.app.Network.ProductCategories;

import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public interface ProductCategoriesApi {
    @FormUrlEncoded
    @POST(END_POINT)
    Call<ProductCategoriesModel> getProductCategories(@Field("api_token") String apiToken, @Field("determiner") String determiner, @Field("main") String main, @Field("header") String header, @Field("fetch_all") boolean fetchAll);
}
