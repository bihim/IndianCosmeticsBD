package com.indiancosmeticsbd.app.Network.BannerSliderTop;

import android.util.Log;
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
                    Log.d("BANNER_SLIDER", "onResponse: "+response.body().getStatus());
                }
                else{
                    bannerSliderModelMutableLiveData.setValue(null);
                    Log.d("BANNER_SLIDER", "onResponse: "+response.body().getStatus());
                }
            }

            @Override
            public void onFailure(Call<BannerSliderModel> call, Throwable t) {
                bannerSliderModelMutableLiveData.setValue(null);
                Log.d("BANNER_SLIDER", "onResponse: "+t.getMessage());
            }
        });
        return bannerSliderModelMutableLiveData;
    }
}
