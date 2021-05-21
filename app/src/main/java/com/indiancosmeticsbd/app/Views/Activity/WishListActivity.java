package com.indiancosmeticsbd.app.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.Account.SignInActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.WISHLIST;

public class WishListActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        setBottomNavigation(R.id.bottom_navigation);
        viewWishList();
    }

    private void viewWishList() {
        SharedPreferences sharedPreferences = getSharedPreferences(WISHLIST, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(WISHLIST, "");

        Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
        ArrayList<Cart> carts = gson.fromJson(json, type);
        int i = 1;
        for (Cart cart : carts) {
            Log.d("CartsListItems", "viewCart: Item No " + i + ": " + cart.getProductId() +" and quantity: "+ cart.getQuantity());
            i++;
        }
    }

    private void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }


    private void setBottomNavigation(int bottomNavigationId){
        bottomNavigationView = findViewById(bottomNavigationId);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.bottom_nav_home){
                    startActivity(new Intent(WishListActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);
                }
                else if(item.getItemId() == R.id.bottom_nav_wishlist){
                    //startActivity(new Intent(WishListActivity.this, WishListActivity.class));
                }
                else if(item.getItemId() == R.id.bottom_nav_cart){
                    startActivity(new Intent(WishListActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);
                }
                else if(item.getItemId() == R.id.bottom_nav_account){
                    startActivity(new Intent(WishListActivity.this, SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);
                }
                return true;
            }
        });

    }

    private void setToolbar(int toolbarId, int backButtonId){
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_wishlist);
    }
}