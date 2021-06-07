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
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.MainActivity;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_ADDRESS;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_ADDRESS2;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_EMAIL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_FACEBOOK;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_GMAIL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_INSTAGRAM;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_LINKEDIN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_MOBILE_1;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_MOBILE_2;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_MOBILE_3;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_PHONE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_SKYPE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_TWITTER;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_YAHOO;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_YOUTUBE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;


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
            if(contactInfo!=null){
                ContactInfo.Content content = contactInfo.getContent();
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(COMPANY_ADDRESS, content.getAddress());
                editor.putString(COMPANY_ADDRESS2, content.getAddress2());
                editor.putString(COMPANY_MOBILE_1, content.getMobile1());
                editor.putString(COMPANY_MOBILE_2, content.getMobile2());
                editor.putString(COMPANY_MOBILE_3, content.getMobile3());
                editor.putString(COMPANY_PHONE, content.getPhone());
                editor.putString(COMPANY_EMAIL, content.getEmail());
                editor.putString(COMPANY_FACEBOOK, content.getFacebook());
                editor.putString(COMPANY_TWITTER, content.getTwitter());
                editor.putString(COMPANY_INSTAGRAM, content.getInstagram());
                editor.putString(COMPANY_LINKEDIN, content.getLinkedin());
                editor.putString(COMPANY_GMAIL, content.getGmail());
                editor.putString(COMPANY_YOUTUBE, content.getYoutube());
                editor.putString(COMPANY_YAHOO, content.getYahoo());
                editor.putString(COMPANY_SKYPE, content.getSkype());
                editor.apply();
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}