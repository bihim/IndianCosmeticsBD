package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.indiancosmeticsbd.app.JsonPlaceHolderApi.JsonPlaceHolderApi;
import com.indiancosmeticsbd.app.Model.ContactInfo.ContactInfo;
import com.indiancosmeticsbd.app.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.ADDRESS;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.ADDRESS2;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.BASE_URL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CONTACT_INFO;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.EMAIL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.FACEBOOK;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.GMAIL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.INSTAGRAM;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.LINKEDIN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.MOBILE_1;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.MOBILE_2;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.MOBILE_3;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.PHONE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SKYPE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.TWITTER;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.YAHOO;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.YOUTUBE;

public class SplashScreenActivity extends AppCompatActivity {

    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            }
        }, 3000);*/
        retrofitSetup();

    }

    private void retrofitSetup(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        getContactInfo();
    }

    private void getContactInfo(){
        Call<ContactInfo> call = jsonPlaceHolderApi.getContactInfo(API_TOKEN, CONTACT_INFO);

        call.enqueue(new Callback<ContactInfo>() {
            @Override
            public void onResponse(Call<ContactInfo> call, Response<ContactInfo> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(SplashScreenActivity.this, "", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body()!=null){
                    ContactInfo.Content content = response.body().getContent();
                    SharedPreferences sharedPreferences = getSharedPreferences(CONTACT_INFO, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(ADDRESS, content.getAddress());
                    editor.putString(ADDRESS2, content.getAddress2());
                    editor.putString(MOBILE_1, content.getMobile1());
                    editor.putString(MOBILE_2, content.getMobile2());
                    editor.putString(MOBILE_3, content.getMobile3());
                    editor.putString(PHONE, content.getPhone());
                    editor.putString(EMAIL, content.getEmail());
                    editor.putString(FACEBOOK, content.getFacebook());
                    editor.putString(TWITTER, content.getTwitter());
                    editor.putString(INSTAGRAM, content.getInstagram());
                    editor.putString(LINKEDIN, content.getLinkedin());
                    editor.putString(GMAIL, content.getGmail());
                    editor.putString(YOUTUBE, content.getYoutube());
                    editor.putString(YAHOO, content.getYahoo());
                    editor.putString(SKYPE, content.getSkype());
                    editor.apply();
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ContactInfo> call, Throwable t) {

            }
        });
    }
}