package com.indiancosmeticsbd.app.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.indiancosmeticsbd.app.Adapter.SliderAdapterExample;
import com.indiancosmeticsbd.app.JsonPlaceHolderApi.JsonPlaceHolderApi;
import com.indiancosmeticsbd.app.Model.BannerSlider.BannerSliderModel;
import com.indiancosmeticsbd.app.Model.BannerSlider.SliderItem;
import com.indiancosmeticsbd.app.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.smarteist.autoimageslider.SliderViewAdapter;

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
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CONTACT_INFO;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.EMAIL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.HOME_PAGE_TOP;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    /*Banner Slider Top*/
    private MaterialCardView cardViewBannerTop;
    private SliderView sliderView;
    private SliderAdapterExample sliderAdapterExample;
    private LottieAnimationView lottieAnimationView;

    /*Nav Drawer*/
    private MaterialButton signInButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setNavDrawer();
        setBottomNavigationView();
        retrofitSetup();
        //retrofitTest();
        getBanner();
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences(CONTACT_INFO, MODE_PRIVATE);
                Toast.makeText(MainActivity.this, sharedPreferences.getString(EMAIL, ""), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void retrofitSetup(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    }

    private void getBanner(){

        sliderAdapterExample = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(sliderAdapterExample);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();

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
                    SliderItem sliderItem = new SliderItem();
                    sliderItem.setImageUrl(content.getImageLink()+content.getImage());
                    sliderAdapterExample.addItem(sliderItem);
                    Log.d("BANNERSLIDE", "onResponse: content: "+content.getImage());
                }
                if (contents.isEmpty()){
                    cardViewBannerTop.setVisibility(View.GONE);
                }
                else{
                    cardViewBannerTop.setVisibility(View.VISIBLE);
                    lottieAnimationView.setVisibility(View.GONE);
                    sliderView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<BannerSliderModel> call, Throwable t) {

            }
        });
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
                    startActivity(new Intent(MainActivity.this, WishListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    overridePendingTransition(0, 0);
                }
                else if(item.getItemId() == R.id.bottom_nav_cart){
                    startActivity(new Intent(MainActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    overridePendingTransition(0, 0);
                }
                else if(item.getItemId() == R.id.bottom_nav_account){
                    startActivity(new Intent(MainActivity.this, AccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    overridePendingTransition(0, 0);
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
        sliderView = findViewById(R.id.imageSlider);
        lottieAnimationView = findViewById(R.id.animationView);
        cardViewBannerTop = findViewById(R.id.banner_slider);
        signInButton = findViewById(R.id.sign_in_main);
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