package com.indiancosmeticsbd.app.Network.Product;

import com.indiancosmeticsbd.app.Model.ProductList.Products;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public interface ProductApi {
    @FormUrlEncoded
    @POST(END_POINT)
    Call<Products> getProductsList(@Field("api_token") String apiToken, @Field("determiner") String determiner, @Field("fetch_scope") String fetch_scope, @Field("category_id") String category_id, @Field("sort") String sort, @Field("search") String search, @Field("product_limit") String product_limit, @Field("product_offset") String product_offset, @Field("filters") String filters);
}
