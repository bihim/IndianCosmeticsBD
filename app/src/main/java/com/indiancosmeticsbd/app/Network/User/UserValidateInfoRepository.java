package com.indiancosmeticsbd.app.Network.User;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.indiancosmeticsbd.app.Views.Dialogs.LoadingDialog;
import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.Model.SignIn.UserValidate;
import com.indiancosmeticsbd.app.Network.RetrofitService;
import com.indiancosmeticsbd.app.Views.Activity.Account.AccountActivity;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COLUMN_TYPE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COLUMN_TYPE_TOKEN;
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

    public MutableLiveData<UserInfo> getUserInfo(Activity activity, String username, String password, boolean isComingFromSignIn, boolean isSignIn) {
        MutableLiveData<UserInfo> userInfoLiveData = new MutableLiveData<>();
        LoadingDialog loadingDialog = new LoadingDialog(activity);
        if (isComingFromSignIn){
            loadingDialog.showDialog();
        }

        userValidateInfoApi.getUserValidate(API_TOKEN, USER_VALIDATE, username, password).enqueue(new Callback<UserValidate>() {
            @Override
            public void onResponse(Call<UserValidate> call, Response<UserValidate> response) {
                if (response.isSuccessful()) {
                    if (response.body()!=null){
                        UserValidate userValidate = response.body();
                        //boolean content = userValidate.getContent();
                        UserValidate.Content content = response.body().getContent();
                        String status = userValidate.getStatus();
                        if (status.equals("SUCCESS")){
                            if (content.getSuccess()){
                                if (isSignIn){
                                    userValidateInfoApi.getUserInfo(API_TOKEN, USER_INFO, COLUMN_TYPE_TOKEN, content.getToken()).enqueue(new Callback<UserInfo>() {
                                        @Override
                                        public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                                            if (response.isSuccessful()) {
                                                if (isComingFromSignIn){
                                                    loadingDialog.dismissDialog();
                                                }
                                                if (response.body()!=null){
                                                    UserInfo userInfo = response.body();
                                                    String status = userInfo.getStatus();
                                                    if (status.equals("SUCCESS")){
                                                        userInfoLiveData.setValue(response.body());
                                                        Log.d("LOGININFO", "onResponse: I am here success");
                                                        if (isComingFromSignIn){
                                                            Toasty.success(activity, "Logged In", Toasty.LENGTH_LONG).show();
                                                            activity.startActivity(new Intent(activity, AccountActivity.class));
                                                            activity.finish();
                                                        }

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
                                else{
                                    userValidateInfoApi.getUserInfo(API_TOKEN, USER_INFO, COLUMN_TYPE, username).enqueue(new Callback<UserInfo>() {
                                        @Override
                                        public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                                            if (response.isSuccessful()) {
                                                if (isComingFromSignIn){
                                                    loadingDialog.dismissDialog();
                                                }
                                                if (response.body()!=null){
                                                    UserInfo userInfo = response.body();
                                                    String status = userInfo.getStatus();
                                                    if (status.equals("SUCCESS")){
                                                        userInfoLiveData.setValue(response.body());
                                                        Log.d("LOGININFO", "onResponse: I am here success");
                                                        if (isComingFromSignIn){
                                                            Toasty.success(activity, "Logged In", Toasty.LENGTH_LONG).show();
                                                            activity.startActivity(new Intent(activity, AccountActivity.class));
                                                            activity.finish();
                                                        }

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
                            else{
                                Toasty.error(activity, "Username or Password is not valid", Toasty.LENGTH_LONG).show();
                            }
                        }
                        else{
                            Toasty.error(activity, "Username or Password is not valid", Toasty.LENGTH_LONG).show();
                            Log.d("LOGININFO", "onResponse: I am here success");
                        }
                    }
                } else {
                    userInfoLiveData.setValue(null);
                }
                if (isComingFromSignIn){
                    loadingDialog.dismissDialog();
                }

            }
            @Override
            public void onFailure(Call<UserValidate> call, Throwable t) {
                userInfoLiveData.setValue(null);
            }
        });
        return userInfoLiveData;
    }
}
