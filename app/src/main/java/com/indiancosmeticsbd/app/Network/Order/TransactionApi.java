package com.indiancosmeticsbd.app.Network.Order;

import com.indiancosmeticsbd.app.Model.Orders.TransactionModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public interface TransactionApi {
    @FormUrlEncoded
    @POST(END_POINT)
    Call<TransactionModel> getCODInfo(@Field("api_token") String apiToken, @Field("determiner") String determiner, @Field("userToken") String userToken, @Field("mobileNumber") String mobileNumber, @Field("orderLocation") String orderLocation, @Field("fullAddress") String fullAddress, @Field("products") String products, @Field("paymentType") String paymentType, @Field("paymentNumber") String paymentNumber, @Field("paymentTrxnId") String paymentTrxnId);
}
