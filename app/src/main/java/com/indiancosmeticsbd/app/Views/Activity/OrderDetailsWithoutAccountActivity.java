package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.OrderSubmitProductAdapter;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.R;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART_DIRECT_ORDER;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class OrderDetailsWithoutAccountActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_order_details_without_account);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        setRecyclerview();
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
        String json = sharedPreferences.getString(CART_DIRECT_ORDER, "");;
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

    }
}