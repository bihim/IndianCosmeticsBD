package com.indiancosmeticsbd.app.Network.ForgotPassword;

import com.indiancosmeticsbd.app.Model.ContactInfo.ContactInfo;
import com.indiancosmeticsbd.app.Model.ForgotPassword.ForgotPasswordModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public interface ForgotPasswordApi
{
    @FormUrlEncoded
    @POST(END_POINT)
    Call<ForgotPasswordModel> getForgotPassword(@Field("api_token") String apiToken, @Field("determiner") String determiner, @Field("username") String username);
}
