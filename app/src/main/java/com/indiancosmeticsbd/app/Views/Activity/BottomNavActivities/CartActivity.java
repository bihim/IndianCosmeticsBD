package com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.CartAdapter;
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

public class CartActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private ArrayList<Cart> cartArrayList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ConstraintLayout constraintLayoutCheckout;
    private TextView textViewTotal;
    private LinearLayout empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        setBottomNavigation(R.id.bottom_navigation);
        recyclerView = findViewById(R.id.cart_recyclerview);
        textViewTotal = findViewById(R.id.cart_total);
        empty = findViewById(R.id.empty_image);
        constraintLayoutCheckout = findViewById(R.id.checkout_card);
        sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
        setCommonThings();
    }

    private void setRecyclerview(){
        cartAdapter = new CartAdapter(cartArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(cartAdapter);
        isCartEmpty(false);
        cartAdapter.setOnItemClickListener(new CartAdapter.OnItemClickListener() {
            @Override
            public void plusClick(int position) {
                editor = sharedPreferences.edit();
                int quantity = Integer.parseInt(cartArrayList.get(position).getQuantity());
                String id = cartArrayList.get(position).getProductId();
                //int stock = Integer.parseInt(cartArrayList.get(position).getStock());
                quantity++;
                cartArrayList.get(position).setQuantity(String.valueOf(quantity));
                cartAdapter.notifyItemChanged(position);
                Gson gson = new Gson();
                String json = sharedPreferences.getString(CART, "");
                Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
                ArrayList<Cart> carts = gson.fromJson(json, type);
                for (int i = 0; i < carts.size(); i++){
                    if (carts.get(i).getProductId().equals(id)){
                        String updatedId = carts.get(i).getProductId();
                        String updatedProductName = carts.get(i).getProductName();
                        String updatedBrandName = carts.get(i).getBrandName();
                        String updatedPrice = carts.get(i).getPrice();
                        String updateQuantity = String.valueOf(quantity);
                        String updatedDiscount = carts.get(i).getDiscount();
                        String updatedSize = carts.get(i).getSize();
                        String updatedImageUrl = carts.get(i).getImageUrl();
                        String updatedStock = carts.get(i).getStock();
                        carts.set(i, new Cart(updatedId, updatedBrandName, updatedProductName, updatedPrice, updateQuantity, updatedDiscount, updatedSize, updatedImageUrl, updatedStock));
                    }
                }
                String updatedJson = gson.toJson(carts);
                editor.putString(CART, updatedJson);
                editor.commit();
                //editor.apply();
                totalPrice();
            }

            @Override
            public void minusClick(int position) {
                editor = sharedPreferences.edit();
                int quantity = Integer.parseInt(cartArrayList.get(position).getQuantity());
                String id = cartArrayList.get(position).getProductId();
                quantity--;
                cartArrayList.get(position).setQuantity(String.valueOf(quantity));
                cartAdapter.notifyItemChanged(position);
                Gson gson = new Gson();
                String json = sharedPreferences.getString(CART, "");
                Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
                ArrayList<Cart> carts = gson.fromJson(json, type);
                for (int i = 0; i < carts.size(); i++){
                    if (carts.get(i).getProductId().equals(id)){
                        String updatedId = carts.get(i).getProductId();
                        String updatedProductName = carts.get(i).getProductName();
                        String updatedBrandName = carts.get(i).getBrandName();
                        String updatedPrice = carts.get(i).getPrice();
                        String updateQuantity = String.valueOf(quantity);
                        String updatedDiscount = carts.get(i).getDiscount();
                        String updatedSize = carts.get(i).getSize();
                        String updatedImageUrl = carts.get(i).getImageUrl();
                        String updatedStock = carts.get(i).getStock();
                        carts.set(i, new Cart(updatedId, updatedBrandName, updatedProductName, updatedPrice, updateQuantity, updatedDiscount, updatedSize, updatedImageUrl, updatedStock));
                    }
                }
                String updatedJson = gson.toJson(carts);
                editor.putString(CART, updatedJson);
                editor.commit();
                //editor.apply();
                totalPrice();
            }

            @Override
            public void onDeleteClicked(int position) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String id = cartArrayList.get(position).getProductId();
                Gson gson = new Gson();
                String json = sharedPreferences.getString(CART, "");
                Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
                ArrayList<Cart> carts = gson.fromJson(json, type);

                for (int i = carts.size() - 1; i >= 0; i--) {
                    if (carts.get(i).getProductId().equals(id)) {
                        carts.remove(i);
                    }
                }
                String updatedJson = gson.toJson(carts);
                editor.putString(CART, updatedJson);
                editor.apply();
                cartArrayList.remove(position);
                cartAdapter.notifyItemRemoved(position);
                totalPrice();
                if (!cartArrayList.isEmpty()){
                    constraintLayoutCheckout.setVisibility(View.VISIBLE);
                    constraintLayoutCheckout.setBackground(ContextCompat.getDrawable(CartActivity.this, R.drawable.bottom_nav_card_round));
                    bottomNavigationView.setBackground(ContextCompat.getDrawable(CartActivity.this, R.drawable.bottom_nav_card_not_round));
                }
                else{
                    isCartEmpty(true);
                    constraintLayoutCheckout.setVisibility(View.GONE);
                    constraintLayoutCheckout.setBackground(ContextCompat.getDrawable(CartActivity.this, R.drawable.bottom_nav_card_not_round));
                    bottomNavigationView.setBackground(ContextCompat.getDrawable(CartActivity.this, R.drawable.bottom_nav_card_round));
                }
                SHOWBADGE(CartActivity.this, CART, R.id.bottom_nav_cart, bottomNavigationView);
            }
            @Override
            public void onItemClicked(int position) {
                Cart cart = cartArrayList.get(position);
                Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
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

    private void totalPrice(){
        int totalCount = 0;
        for (Cart cart: cartArrayList){
            int price = Integer.parseInt(cart.getPrice());
            int quantity = Integer.parseInt(cart.getQuantity());
            totalCount = totalCount+(price*quantity);
        }
        textViewTotal.setText("৳"+totalCount);
    }

    private ArrayList<Cart> viewCart() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CART, "");
        Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
        ArrayList<Cart> carts = gson.fromJson(json, type);
        /*int i = 1;
        for (Cart cart : carts) {
            Log.d("CartsListItems", "viewCart: Item No " + i + ": " + cart.getProductId() +" and quantity: "+ cart.getQuantity()+ "cart brand: "+cart.getBrandName());
            i++;
        }*/
        if (json.equals(""))
        {
            return new ArrayList<>();
        }
        else{
            return carts;
        }
    }

    private void setBottomNavigation(int bottomNavigationId){
        bottomNavigationView = findViewById(bottomNavigationId);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if(item.getItemId() == R.id.bottom_nav_home){
               /* startActivity(new Intent(CartActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(0, 0);*/
                onBackPressed();
            }
            else if(item.getItemId() == R.id.bottom_nav_wishlist){
                startActivity(new Intent(CartActivity.this, WishListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(0, 0);
            }
            else if(item.getItemId() == R.id.bottom_nav_cart){
                /*startActivity(new Intent(CartActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(0, 0);*/
            }
            else if(item.getItemId() == R.id.bottom_nav_account){
                startActivity(new Intent(CartActivity.this, SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(0, 0);
            }
            return true;
        });

    }

    private void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }

    private void setToolbar(int toolbarId, int backButtonId){
        MaterialToolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        ImageButton backButton = findViewById(backButtonId);
        backButton.setOnClickListener(v -> onBackPressed());

    }

    private void setCommonThings(){
        cartArrayList = viewCart();
        if (!cartArrayList.isEmpty()){
            setRecyclerview();
            totalPrice();
            isCartEmpty(false);
            constraintLayoutCheckout.setVisibility(View.VISIBLE);
            constraintLayoutCheckout.setBackground(ContextCompat.getDrawable(this, R.drawable.bottom_nav_card_round));
            bottomNavigationView.setBackground(ContextCompat.getDrawable(this, R.drawable.bottom_nav_card_not_round));
        }
        else{
            isCartEmpty(true);
            constraintLayoutCheckout.setVisibility(View.GONE);
            constraintLayoutCheckout.setBackground(ContextCompat.getDrawable(this, R.drawable.bottom_nav_card_not_round));
            bottomNavigationView.setBackground(ContextCompat.getDrawable(this, R.drawable.bottom_nav_card_round));
        }
        SHOWBADGE(this, CART, R.id.bottom_nav_cart, bottomNavigationView);
        SHOWBADGE(this, WISHLIST, R.id.bottom_nav_wishlist, bottomNavigationView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_cart);
        setCommonThings();
    }
}