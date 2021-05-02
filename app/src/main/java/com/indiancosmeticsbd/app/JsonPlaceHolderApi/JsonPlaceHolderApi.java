package com.indiancosmeticsbd.app.JsonPlaceHolderApi;

import com.indiancosmeticsbd.app.Model.BannerSlider.BannerSliderModel;
import com.indiancosmeticsbd.app.Model.ContactInfo.ContactInfo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.END_POINT;

public interface JsonPlaceHolderApi {

    @FormUrlEncoded
    @POST(END_POINT)
    Call<BannerSliderModel> getBannerSlider(@Field("api_token") String apiToken, @Field("determiner") String determiner, @Field("field") String field);

    @FormUrlEncoded
    @POST(END_POINT)
    Call<ContactInfo> getContactInfo(@Field("api_token") String apiToken, @Field("determiner") String determiner);

}
