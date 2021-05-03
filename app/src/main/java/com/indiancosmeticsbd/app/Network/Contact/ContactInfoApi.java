package com.indiancosmeticsbd.app.Network.Contact;

import com.indiancosmeticsbd.app.Model.ContactInfo.ContactInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public interface ContactInfoApi
{
    @FormUrlEncoded
    @POST(END_POINT)
    Call<ContactInfo> getContactInfo(@Field("api_token") String apiToken, @Field("determiner") String determiner);
}
