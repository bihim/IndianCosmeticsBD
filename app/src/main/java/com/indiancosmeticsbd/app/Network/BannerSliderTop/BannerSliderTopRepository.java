package com.indiancosmeticsbd.app.Network.BannerSliderTop;

import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.indiancosmeticsbd.app.Model.BannerSlider.BannerSliderModel;
import com.indiancosmeticsbd.app.Network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BannerSliderTopRepository 
{
    private static BannerSliderTopRepository bannerSliderTopRepository;
    private BannerSliderTopApi bannerSliderTopApi;
    
    public static BannerSliderTopRepository getInstance(){
        if (bannerSliderTopRepository== null){
            bannerSliderTopRepository = new BannerSliderTopRepository();
        }
        return bannerSliderTopRepository;
    }
    
    public BannerSliderTopRepository(){
        bannerSliderTopApi = RetrofitService.createService(BannerSliderTopApi.class);
    }
    
    public MutableLiveData<BannerSliderModel> getBannerSlider(String api_token, String determiner, String field){
        MutableLiveData<BannerSliderModel> bannerSliderModelMutableLiveData = new MutableLiveData<>();
        bannerSliderTopApi.getBannerSlider(api_token, determiner, field).enqueue(new Callback<BannerSliderModel>() {
            @Override
            public void onResponse(Call<BannerSliderModel> call, Response<BannerSliderModel> response) {
                if (response.isSuccessful()){
                    bannerSliderModelMutableLiveData.setValue(response.body());
                }
                else{
                    bannerSliderModelMutableLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<BannerSliderModel> call, Throwable t) {
                bannerSliderModelMutableLiveData.setValue(null);
            }
        });
        return bannerSliderModelMutableLiveData;
    }
}
