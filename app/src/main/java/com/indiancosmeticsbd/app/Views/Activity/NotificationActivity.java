package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.indiancosmeticsbd.app.R;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_after_notification_size;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_previous_notification_size;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setNotification();
    }

    private void setNotification(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int notification_value = sharedPreferences.getInt(user_after_notification_size, 0);
        editor.putInt(user_previous_notification_size, notification_value);
        editor.apply();
    }
}