package com.indiancosmeticsbd.app.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.indiancosmeticsbd.app.JsonPlaceHolderApi.JsonPlaceHolderApi;
import com.indiancosmeticsbd.app.Model.BannerSlider.BannerSliderModel;
import com.indiancosmeticsbd.app.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.BANNER_SLIDER;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.BASE_URL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.HOME_PAGE_TOP;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private JsonPlaceHolderApi jsonPlaceHolderApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setNavDrawer();
        setBottomNavigationView();
        retrofitSetup();
        //retrofitTest();
    }


    private void retrofitSetup(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    private void retrofitTest(){
        Call<BannerSliderModel> call = jsonPlaceHolderApi.getBannerSlider(API_TOKEN, BANNER_SLIDER, HOME_PAGE_TOP);
        call.enqueue(new Callback<BannerSliderModel>() {
            @Override
            public void onResponse(Call<BannerSliderModel> call, Response<BannerSliderModel> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                List<BannerSliderModel.Content> contents = new ArrayList(response.body().getContent());
                for (BannerSliderModel.Content content : contents){
                    Log.d("BANNERSLIDE", "onResponse: content: "+content.getImage());
                }

            }

            @Override
            public void onFailure(Call<BannerSliderModel> call, Throwable t) {

            }
        });
    }

    private void setBottomNavigationView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.bottom_nav_home){
                    //
                }
                else if(item.getItemId() == R.id.bottom_nav_wishlist){
                    startActivity(new Intent(MainActivity.this, WishListActivity.class));
                }
                else if(item.getItemId() == R.id.bottom_nav_cart){
                    startActivity(new Intent(MainActivity.this, CartActivity.class));
                }
                else if(item.getItemId() == R.id.bottom_nav_account){
                    startActivity(new Intent(MainActivity.this, AccountActivity.class));
                }
                return true;
            }
        });
    }
    private void setNavDrawer(){
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
    private void findViewById(){
        toolbar = findViewById(R.id.toolbar_main);
        drawerLayout = findViewById(R.id.nav_drawer);
        navigationView = findViewById(R.id.navigation_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
    }
}