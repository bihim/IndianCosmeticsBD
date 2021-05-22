package com.indiancosmeticsbd.app.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.CartAdapter;
import com.indiancosmeticsbd.app.Adapter.WishListAdapter;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.Account.SignInActivity;
import com.indiancosmeticsbd.app.Views.Activity.ProductDetails.ProductDetailsActivity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHOWBADGE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.WISHLIST;

public class WishListActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private SharedPreferences sharedPreferences;

    private ArrayList<Cart> wishListArrayList;
    private RecyclerView recyclerView;
    private WishListAdapter wishListAdapter;

    private LinearLayout empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        setBottomNavigation(R.id.bottom_navigation);
        recyclerView = findViewById(R.id.wishlist_recyclerview);
        empty = findViewById(R.id.empty_image);
        sharedPreferences = getSharedPreferences(WISHLIST, MODE_PRIVATE);
        wishListArrayList = viewWishList();
        SHOWBADGE(this, CART, R.id.bottom_nav_cart, bottomNavigationView);
        SHOWBADGE(this, WISHLIST, R.id.bottom_nav_wishlist, bottomNavigationView);
        if (!wishListArrayList.isEmpty())
        {
            setRecyclerview();
        }
        isCartEmpty(wishListArrayList.isEmpty());
    }

    private void setRecyclerview(){
        wishListAdapter = new WishListAdapter(wishListArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(wishListAdapter);
        isCartEmpty(false);
        wishListAdapter.setOnItemClickListener(new WishListAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClicked(int position) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String id = wishListArrayList.get(position).getProductId();
                Gson gson = new Gson();
                String json = sharedPreferences.getString(WISHLIST, "");
                Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
                ArrayList<Cart> carts = gson.fromJson(json, type);
                for (int i = carts.size() - 1; i >= 0; i--) {
                    if (carts.get(i).getProductId().equals(id)) {
                        carts.remove(i);
                    }
                }
                String updatedJson = gson.toJson(carts);
                editor.putString(WISHLIST, updatedJson);
                editor.apply();
                wishListArrayList.remove(position);
                wishListAdapter.notifyItemRemoved(position);
                isCartEmpty(wishListArrayList.isEmpty());
                SHOWBADGE(WishListActivity.this, WISHLIST, R.id.bottom_nav_wishlist, bottomNavigationView);
            }

            @Override
            public void onItemClicked(int position) {
                Cart cart = wishListArrayList.get(position);
                Intent intent = new Intent(WishListActivity.this, ProductDetailsActivity.class);
                Log.d("PRODUCT_DETAILS_CART", "onClick: id: "+cart.getProductId());
                intent.putExtra("id", Integer.parseInt(cart.getProductId()));
                intent.putExtra("name", cart.getProductName());
                startActivity(intent);
            }
        });
    }

    private void isCartEmpty(boolean value){
        if (value){
            empty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        else{
            empty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }

    private ArrayList<Cart> viewWishList() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(WISHLIST, "");
        Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
        ArrayList<Cart> carts = gson.fromJson(json, type);
        if (json.equals(""))
        {
            return new ArrayList<>();
        }
        else{
            return carts;
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
        if (!wishListArrayList.isEmpty())
        {
            setRecyclerview();
        }
        isCartEmpty(wishListArrayList.isEmpty());
        SHOWBADGE(this, CART, R.id.bottom_nav_cart, bottomNavigationView);
        SHOWBADGE(this, WISHLIST, R.id.bottom_nav_wishlist, bottomNavigationView);
    }
}