package com.indiancosmeticsbd.app.Views.Activity.Orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.NotificationAdapter;
import com.indiancosmeticsbd.app.Adapter.ViewOrderAdapter;
import com.indiancosmeticsbd.app.Model.Notifications.NotificationModel;
import com.indiancosmeticsbd.app.Model.Orders.ViewOrdersModel;
import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.UserInfoViewModel;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_address;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_after_notification_size;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_city;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_country;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_date;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_district;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_email;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_first_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_id;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_last_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_mobile_number;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_notification;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_orders;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_password;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_postalcode;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_previous_notification_size;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_token;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_username;

public class ViewOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ViewOrderAdapter viewOrderAdapter;
    private ArrayList<ViewOrdersModel> viewOrdersModelArrayList;
    private LinearLayout empty;
    private LinearLayout retry;
    private LottieAnimationView lottieAnimationView;
    private MaterialButton buttonRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_orders);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        setRecyclerView();
    }

    private void setRecyclerView() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        if (sharedPreferences.contains(user_previous_notification_size)) {
            String emailAddress = sharedPreferences.getString(user_email, "test@mail.com");
            String password = sharedPreferences.getString(user_password, "test1234");
            setSignInButton(emailAddress, password);
        }

        Logger.addLogAdapter(new AndroidLogAdapter());
        String getOrders = sharedPreferences.getString(user_orders, "null");
        String getDate = sharedPreferences.getString(user_date, "null");
        //Logger.d("Orders: "+getOrders+"\nDate: "+getDate);
        viewOrdersModelArrayList = new ArrayList<>();
        if (!getOrders.equals("null")){
            String[] orders = getOrders.split(",");
            String[] dates = getDate.split(",");
            for (int i = 0; i < orders.length; i++){
                if (!orders[i].equals("null")){
                    viewOrdersModelArrayList.add(new ViewOrdersModel(orders[i], dates[i]));
                }
            }
        }

        if (viewOrdersModelArrayList.size() == 0) {
            recyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }

        viewOrderAdapter = new ViewOrderAdapter(viewOrdersModelArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(viewOrderAdapter);
    }

    private void setSignInButton(String emailAddress, String password) {
        retry.setVisibility(View.GONE);
        lottieAnimationView.setVisibility(View.VISIBLE);
        Gson gson = new Gson();
        List<UserInfo.Notification> notifications = new ArrayList<>();
        UserInfoViewModel userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        userInfoViewModel.init();
        userInfoViewModel.getUserInfo(emailAddress, password, this, false).observe(this, userInfo -> {
            if (userInfo.getContent()!=null){
                retry.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.GONE);
                UserInfo.Content value = userInfo.getContent();
                if (value.getCustomerOrders().size() != 0) {
                    StringBuilder listCustomerOrders = new StringBuilder();
                    StringBuilder listCustomerDate = new StringBuilder();
                    for (UserInfo.CustomerOrder customerOrder : value.getCustomerOrders()) {
                        listCustomerOrders.append(customerOrder.getOrderNo()).append(",");
                        listCustomerDate.append(customerOrder.getDate()).append(",");
                    }
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
                    editor.putString(user_orders, listCustomerOrders.toString());
                    editor.putString(user_date, listCustomerDate.toString());

                    editor.apply();
                }
                else{
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(user_orders, "null");
                    editor.putString(user_date, "null");
                    editor.apply();
                }
            }
            else{
                retry.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.GONE);
                buttonRetry.setOnClickListener(v -> {
                    buttonRetry.setVisibility(View.GONE);
                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                    if (sharedPreferences.contains(user_previous_notification_size)) {
                        String emailAddress1 = sharedPreferences.getString(user_email, "test@mail.com");
                        String password1 = sharedPreferences.getString(user_password, "test1234");
                        setSignInButton(emailAddress1, password1);
                    }
                });
            }
        });
    }

    private void setTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }
    private void setToolbar(int toolbarId, int backButtonId) {
        MaterialToolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        ImageButton backButton = findViewById(backButtonId);
        backButton.setOnClickListener(v -> onBackPressed());
    }
    private void findViewById() {
        recyclerView = findViewById(R.id.view_order_recyclerview);
        empty = findViewById(R.id.empty_image);
        retry = findViewById(R.id.splash_retry_layout);
        lottieAnimationView = findViewById(R.id.lottie_loading);
        buttonRetry = findViewById(R.id.splash_retry);
    }
}