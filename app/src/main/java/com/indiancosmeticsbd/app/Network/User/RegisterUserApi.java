package com.indiancosmeticsbd.app.Network.User;

import com.indiancosmeticsbd.app.Model.ForgotPassword.ForgotPasswordModel;
import com.indiancosmeticsbd.app.Model.SignIn.UserValidate;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public interface RegisterUserApi {

    @FormUrlEncoded
    @POST(END_POINT)
    Call<ForgotPasswordModel> getRegisterUser(@Field("api_token") String apiToken,
                                              @Field("determiner") String determiner,
                                              @Field("username") String username,
                                              @Field("password") String password,
                                              @Field("first_name") String first_name,
                                              @Field("last_name") String last_name,
                                              @Field("email") String email,
                                              @Field("address_line_1") String address_line_1,
                                              @Field("state") String state,
                                              @Field("city") String city,
                                              @Field("postalcode") String postalcode,
                                              @Field("phone") String phone);
}
