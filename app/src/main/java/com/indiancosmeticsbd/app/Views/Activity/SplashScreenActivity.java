package com.indiancosmeticsbd.app.Views.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;
import com.indiancosmeticsbd.app.Model.ContactInfo.ContactInfo;
import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.ContactInfoViewModel;
import com.indiancosmeticsbd.app.ViewModel.UserInfoViewModel;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.MainActivity;

import java.util.ArrayList;
import java.util.List;

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
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_address;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_after_notification_size;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_city;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_country;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_district;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_email;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_first_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_id;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_last_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_mobile_number;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_notification;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_password;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_postalcode;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_previous_notification_size;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_token;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_username;


public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getContactInfoMVVM();
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        if (sharedPreferences.contains(user_previous_notification_size)){
            String emailAddress = sharedPreferences.getString(user_email, "test@mail.com");
            String password = sharedPreferences.getString(user_password, "test1234");
            setSignInButton(emailAddress, password);
        }
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

    private void setSignInButton(String emailAddress, String password){
        Gson gson = new Gson();
        List<UserInfo.Notification> notifications = new ArrayList<>();

        UserInfoViewModel userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        userInfoViewModel.init();
        userInfoViewModel.getUserInfo(emailAddress, password, this, false).observe(this, userInfo -> {
            UserInfo.Content value = userInfo.getContent();
            notifications.addAll(value.getNotifications());
            int notificationSize = notifications.size();
            String notification_json = gson.toJson(notifications);

            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(user_id, value.getId());
            editor.putString(user_username, value.getUsername());
            editor.putString(user_token, value.getToken());
            editor.putString(user_first_name, value.getFirstName());
            editor.putString(user_last_name, value.getLastName());
            editor.putString(user_email, value.getEmail());
            editor.putString(user_address, value.getAddress());
            editor.putString(user_city, value.getCity());
            editor.putString(user_district, value.getDistrict());
            editor.putString(user_country, value.getCountry());
            editor.putString(user_postalcode, value.getPostalcode());
            editor.putString(user_mobile_number, value.getMobileNumber());
            /*Notification Part
             * First time previous notification size will be 0 and new will assign to it.
             * If it matches then no badge will show */

            //editor.putInt(user_previous_notification_size, 0);
            editor.putInt(user_after_notification_size, notificationSize);
            editor.putString(user_notification, notification_json);

            editor.apply();
        });
    }
}