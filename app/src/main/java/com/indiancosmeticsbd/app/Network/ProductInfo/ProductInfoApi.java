package com.indiancosmeticsbd.app.Network.ProductInfo;

import com.indiancosmeticsbd.app.Model.ProductDetails.ProductInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public interface ProductInfoApi {
    @FormUrlEncoded
    @POST(END_POINT)
    Call<ProductInfo> getProductsList(@Field("api_token") String apiToken, @Field("determiner") String determiner, @Field("field") String field);
}
