package com.indiancosmeticsbd.app.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.indiancosmeticsbd.app.Adapter.SliderAdapterExample;
import com.indiancosmeticsbd.app.Model.BannerSlider.BannerSliderModel;
import com.indiancosmeticsbd.app.Model.BannerSlider.SliderItem;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.BannerSliderTopViewModel;
import com.indiancosmeticsbd.app.Views.Activity.Account.AccountActivity;
import com.indiancosmeticsbd.app.Views.Activity.Account.SignInActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

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
        setSliderView();
    }

    private void getBannerSlider() {
        BannerSliderTopViewModel bannerSliderTopViewModel = new ViewModelProvider(this).get(BannerSliderTopViewModel.class);
        bannerSliderTopViewModel.init();
        bannerSliderTopViewModel.getBannerSlider().observe(this, bannerSliderModel -> {
            List<BannerSliderModel.Content> contents = new ArrayList<>(bannerSliderModel.getContent());
            for (BannerSliderModel.Content content : contents) {
                SliderItem sliderItem = new SliderItem();
                sliderItem.setImageUrl(content.getImageLink() + content.getImage());
                sliderAdapterExample.addItem(sliderItem);
                Log.d("BANNERSLIDE", "onResponse: content: " + content.getImage());
            }
            if (contents.isEmpty()) {
                cardViewBannerTop.setVisibility(View.GONE);
            } else {
                cardViewBannerTop.setVisibility(View.VISIBLE);
                lottieAnimationView.setVisibility(View.GONE);
                sliderView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setSliderView() {
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
        getBannerSlider();
    }

    private void setBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_nav_home) {
                    //
                } else if (item.getItemId() == R.id.bottom_nav_wishlist) {
                    startActivity(new Intent(MainActivity.this, WishListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.bottom_nav_cart) {
                    startActivity(new Intent(MainActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.bottom_nav_account) {
                    startActivity(new Intent(MainActivity.this, SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    overridePendingTransition(0, 0);
                }
                return true;
            }
        });
    }

    private void setNavDrawer() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void findViewById() {
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
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
    }
}