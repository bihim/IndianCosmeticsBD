package com.indiancosmeticsbd.app.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indiancosmeticsbd.app.Model.BannerSlider.BannerSliderModel;
import com.indiancosmeticsbd.app.Network.BannerSliderTop.BannerSliderTopRepository;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.BANNER_SLIDER;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.HOME_PAGE_TOP;

public class BannerSliderTopViewModel extends ViewModel {
    private MutableLiveData<BannerSliderModel> mutableLiveData;

    public void init() {
        if (mutableLiveData != null) {
            return;
        }
    }

    public LiveData<BannerSliderModel> getBannerSlider(String bannerSliderPosition) {
        BannerSliderTopRepository bannerSliderTopRepository = BannerSliderTopRepository.getInstance();
        mutableLiveData = bannerSliderTopRepository.getBannerSlider(API_TOKEN, BANNER_SLIDER, bannerSliderPosition);
        return mutableLiveData;
    }
}
