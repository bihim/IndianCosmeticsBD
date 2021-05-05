package com.indiancosmeticsbd.app.Network.User;

import android.app.Activity;

import androidx.lifecycle.MutableLiveData;

import com.indiancosmeticsbd.app.GlobalValue.LoadingDialog;
import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.Model.SignIn.UserValidate;
import com.indiancosmeticsbd.app.Network.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COLUMN_TYPE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.USER_INFO;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.USER_VALIDATE;

public class UserValidateInfoRepository {
    private static UserValidateInfoRepository UserValidateInfoRepository;
    private final UserValidateInfoApi userValidateInfoApi;

    public static UserValidateInfoRepository getInstance() {
        if (UserValidateInfoRepository == null) {
            UserValidateInfoRepository = new UserValidateInfoRepository();
        }
        return UserValidateInfoRepository;
    }

    public UserValidateInfoRepository() {
        userValidateInfoApi = RetrofitService.createService(UserValidateInfoApi.class);
    }

    public MutableLiveData<UserInfo> getUserInfo(Activity activity, String username, String password) {
        MutableLiveData<UserInfo> userInfoLiveData = new MutableLiveData<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        loadingDialog.showDialog();
        userValidateInfoApi.getUserValidate(API_TOKEN, USER_VALIDATE, username, password).enqueue(new Callback<UserValidate>() {
            @Override
            public void onResponse(Call<UserValidate> call, Response<UserValidate> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null){
                        UserValidate userValidate = response.body();
                        boolean content = userValidate.getContent();
                        String status = userValidate.getStatus();
                        if (content && status.equals("SUCCESS")){
                            userValidateInfoApi.getUserInfo(API_TOKEN, USER_INFO, COLUMN_TYPE, username).enqueue(new Callback<UserInfo>() {
                                @Override
                                public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                                    if (response.isSuccessful()) {
                                        loadingDialog.dismissDialog();
                                        if (response.body()!=null){
                                            UserInfo userInfo = response.body();
                                            String status = userInfo.getStatus();
                                            if (status.equals("SUCCESS")){
                                                userInfoLiveData.setValue(response.body());
                                            }
                                            else{
                                                userInfoLiveData.setValue(null);
                                            }
                                        }
                                    } else {
                                        userInfoLiveData.setValue(null);
                                    }
                                }

                                @Override
                                public void onFailure(Call<UserInfo> call, Throwable t) {
                                    userInfoLiveData.setValue(null);
                                }
                            });
                        }
                    }
                } else {
                    userInfoLiveData.setValue(null);
                }
                loadingDialog.dismissDialog();
            }
            @Override
            public void onFailure(Call<UserValidate> call, Throwable t) {
                userInfoLiveData.setValue(null);
            }
        });
        return userInfoLiveData;
    }
}
