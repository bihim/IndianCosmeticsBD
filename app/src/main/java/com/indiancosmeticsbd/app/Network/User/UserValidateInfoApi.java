package com.indiancosmeticsbd.app.Network.User;

import com.indiancosmeticsbd.app.Model.ContactInfo.ContactInfo;
import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.Model.SignIn.UserValidate;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public interface UserValidateInfoApi
{
    @FormUrlEncoded
    @POST(END_POINT)
    Call<UserValidate> getUserValidate(@Field("api_token") String apiToken, @Field("determiner") String determiner, @Field("username") String username , @Field("password") String password );

    @FormUrlEncoded
    @POST(END_POINT)
    Call<UserInfo> getUserInfo(@Field("api_token") String apiToken, @Field("determiner") String determiner, @Field("column_type") String columnType , @Field("field") String field);
}
