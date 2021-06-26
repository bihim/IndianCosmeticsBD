package com.indiancosmeticsbd.app.Network.Order;

import com.indiancosmeticsbd.app.Model.Orders.OrderStatusModel;
import com.indiancosmeticsbd.app.Model.Orders.TransactionModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public interface OrderInfoApi
{
    @FormUrlEncoded
    @POST(END_POINT)
    Call<OrderStatusModel> getCODInfo(@Field("api_token") String apiToken, @Field("determiner") String determiner, @Field("field") String field);
}
