package com.indiancosmeticsbd.app.Views.Activity.Orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.NotificationAdapter;
import com.indiancosmeticsbd.app.Adapter.ViewOrderAdapter;
import com.indiancosmeticsbd.app.Model.Notifications.NotificationModel;
import com.indiancosmeticsbd.app.Model.Orders.ViewOrdersModel;
import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_date;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_notification;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_orders;

public class ViewOrdersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ViewOrderAdapter viewOrderAdapter;
    private ArrayList<ViewOrdersModel> viewOrdersModelArrayList;
    private LinearLayout empty;

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
        String getOrders = sharedPreferences.getString(user_orders, "null");
        String getDate = sharedPreferences.getString(user_date, "null");
        viewOrdersModelArrayList = new ArrayList<>();

        if (!getOrders.equals("null")){
            String[] orders = getOrders.split(",");
            String[] dates = getDate.split(",");
            for (int i = 0; i < orders.length; i++){
                viewOrdersModelArrayList.add(new ViewOrdersModel(orders[i], dates[i]));
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

    private void setTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
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
        recyclerView = findViewById(R.id.view_order_recyclerview);
        empty = findViewById(R.id.empty_image);
    }
}