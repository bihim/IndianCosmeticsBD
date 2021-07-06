package com.indiancosmeticsbd.app.Views.Activity.Orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.CartAdapter;
import com.indiancosmeticsbd.app.Adapter.OrderSubmitProductAdapter;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.CartActivity;
import com.indiancosmeticsbd.app.Views.Activity.ProductDetails.ProductDetailsActivity;
import com.indiancosmeticsbd.app.Views.Dialogs.BkashOrderSubmitDialog;
import com.indiancosmeticsbd.app.Views.Dialogs.CODOrderSubmitDialog;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART_DIRECT_ORDER;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHOWBADGE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_address;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_first_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_last_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_mobile_number;

public class OrderSubmitActivity extends AppCompatActivity {

    private TextView textViewName;
    private TextView textViewPhoneNumber;
    private TextView textViewAddress;
    private TextView textViewTotal;
    private TextView textViewDelivery;
    private RecyclerView recyclerView;
    private MaterialButton buttonSubmitCOD;
    private MaterialButton buttonSubmitBkash;
    private OrderSubmitProductAdapter orderSubmitProductAdapter;
    private ArrayList<Cart> cartArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_submit);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        setDeliveredAddress();
        setRecyclerview();

        buttonSubmitCOD.setOnClickListener(v -> {
            boolean isDirectOrder = getIntent().getBooleanExtra("directOrder", false);
            CODOrderSubmitDialog codOrderSubmitDialog = new CODOrderSubmitDialog(OrderSubmitActivity.this);
            codOrderSubmitDialog.showDialog(isDirectOrder);
        });

        buttonSubmitBkash.setOnClickListener(v -> {
            BkashOrderSubmitDialog bkashOrderSubmitDialog = new BkashOrderSubmitDialog(OrderSubmitActivity.this);
            boolean isDirectOrder = getIntent().getBooleanExtra("directOrder", false);
            bkashOrderSubmitDialog.showDialog(totalPrice(), isDirectOrder);
        });
    }

    private void setRecyclerview(){
        cartArrayList = viewCart();
        orderSubmitProductAdapter = new OrderSubmitProductAdapter(cartArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(orderSubmitProductAdapter);
        textViewTotal.setText("৳"+totalPrice());
        textViewDelivery.setText("৳100");
    }
    private int totalPrice(){
        int totalCount = 0;
        for (Cart cart: cartArrayList){
            int price = Integer.parseInt(cart.getPrice());
            int quantity = Integer.parseInt(cart.getQuantity());
            totalCount = totalCount+(price*quantity);
        }
        return totalCount;
    }

    private ArrayList<Cart> viewCart() {

        Logger.addLogAdapter(new AndroidLogAdapter());
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
        boolean isDirectOrder = getIntent().getBooleanExtra("directOrder", false);
        String json = "";
        if (isDirectOrder){
            json = sharedPreferences.getString(CART_DIRECT_ORDER, "");
        }
        else{
            json = sharedPreferences.getString(CART, "");
        }
        //String json = sharedPreferences.getString(CART, "");
        Logger.d("String: "+json);
        Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
        ArrayList<Cart> carts = gson.fromJson(json, type);
        Logger.d("ArrayList: "+carts);
        if (json.equals(""))
        {
            return new ArrayList<>();
        }
        else{
            return carts;
        }
    }

    private void setDeliveredAddress(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        textViewName.setText(sharedPreferences.getString(user_first_name,"No name")+" "+sharedPreferences.getString(user_last_name, ""));
        textViewPhoneNumber.setText(sharedPreferences.getString(user_mobile_number, "No Number"));
        textViewAddress.setText(sharedPreferences.getString(user_address, "No Address"));
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
        textViewName = findViewById(R.id.order_submit_name);
        textViewPhoneNumber = findViewById(R.id.order_submit_phone_number);
        textViewAddress = findViewById(R.id.order_submit_address);
        recyclerView = findViewById(R.id.order_submit_list_product_recyclerview);
        textViewDelivery = findViewById(R.id.order_submit_delivery_cost);
        textViewTotal = findViewById(R.id.order_submit_total_amount);
        buttonSubmitCOD = findViewById(R.id.submit_order_cod);
        buttonSubmitBkash = findViewById(R.id.submit_order_bkash);
    }
}