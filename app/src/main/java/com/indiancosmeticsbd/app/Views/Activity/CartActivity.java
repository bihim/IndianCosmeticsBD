package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Model.ProductDetails.AddToCartModel;
import com.indiancosmeticsbd.app.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        viewCart();

    }
    private void viewCart() {
        SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CART, "");

        Type type = new TypeToken<ArrayList<AddToCartModel>>() {}.getType();
        ArrayList<AddToCartModel> addToCartModels = gson.fromJson(json, type);
        int i = 1;
        for (AddToCartModel addToCartModel : addToCartModels) {
            Log.d("CartsListItems", "viewCart: Item No " + i + ": " + addToCartModel.getProductId() +" and quantity: "+addToCartModel.getQuantity());
            i++;
        }
    }
    private void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }
}