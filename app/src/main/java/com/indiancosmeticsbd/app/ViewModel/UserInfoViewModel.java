package com.indiancosmeticsbd.app.ViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.Network.User.UserValidateInfoRepository;

public class UserInfoViewModel extends ViewModel {
    private MutableLiveData<UserInfo> mutableLiveData;

    public void init(String emailAddress, String password, Activity activity) {
        if (mutableLiveData!=null) {
            return;
        }
        UserValidateInfoRepository userValidateInfoRepository = UserValidateInfoRepository.getInstance();
        mutableLiveData = userValidateInfoRepository.getUserInfo(activity, emailAddress, password);
    }

    /*public void init(){
        if (mutableLiveData!=null) {
            return;
        }
        UserValidateInfoRepository userValidateInfoRepository = UserValidateInfoRepository.getInstance();
        mutableLiveData = userValidateInfoRepository.getUserInfo(emailAddress.getValue(), password.getValue());
    }*/

    public LiveData<UserInfo> getUserInfo(){
        return mutableLiveData;
    }
}
