package com.indiancosmeticsbd.app.Views.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.indiancosmeticsbd.app.Model.ContactInfo.ContactInfo;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.ContactInfoViewModel;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.ADDRESS;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.ADDRESS2;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getContactInfoMVVM();
    }

    private void getContactInfoMVVM(){
        ContactInfoViewModel contactInfoViewModel = new ViewModelProvider(this).get(ContactInfoViewModel.class);
        contactInfoViewModel.init();
        contactInfoViewModel.getContactInfo().observe(this, contactInfo -> {
            ContactInfo.Content content = contactInfo.getContent();
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
        });
    }
}