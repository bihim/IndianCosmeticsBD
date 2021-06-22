package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.NotificationAdapter;
import com.indiancosmeticsbd.app.Model.Notifications.NotificationModel;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_after_notification_size;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_notification;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_previous_notification_size;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private NotificationAdapter notificationAdapter;
    private ArrayList<NotificationModel> notificationModelArrayList;
    private LinearLayout empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        setNotification();
        setRecyclerView();
    }
    private void setTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }
    private void setRecyclerView() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(user_notification, "");
        Type type = new TypeToken<ArrayList<UserInfo.Notification>>() {
        }.getType();
        ArrayList<UserInfo.Notification> notifications = gson.fromJson(json, type);
        notificationModelArrayList = new ArrayList<>();
        for (UserInfo.Notification notification : notifications) {
            notificationModelArrayList.add(new NotificationModel(notification.getNotificationType(), notification.getNotificationText(), notification.getLink(), notification.getStatus()));
        }

        if (notifications.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }

        notificationAdapter = new NotificationAdapter(notificationModelArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(notificationAdapter);
    }

    private void setNotification() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int notification_value = sharedPreferences.getInt(user_after_notification_size, 0);
        editor.putInt(user_previous_notification_size, notification_value);
        editor.apply();
    }

    private void setToolbar(int toolbarId, int backButtonId) {
        MaterialToolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        ImageButton backButton = findViewById(backButtonId);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void findViewById() {
        recyclerView = findViewById(R.id.notification_recyclerview);
        empty = findViewById(R.id.empty_image);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}