package com.indiancosmeticsbd.app.Views.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.indiancosmeticsbd.app.Adapter.ProductCategoriesAdapter;
import com.indiancosmeticsbd.app.Adapter.SliderAdapterExample;
import com.indiancosmeticsbd.app.Model.BannerSlider.BannerSliderModel;
import com.indiancosmeticsbd.app.Model.BannerSlider.SliderItem;
import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesModel;
import com.indiancosmeticsbd.app.Network.ProductCategories.ProductCategoriesModelAdapter;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.BannerSliderTopViewModel;
import com.indiancosmeticsbd.app.ViewModel.ProductCategoriesViewModel;
import com.indiancosmeticsbd.app.Views.Activity.Account.AccountActivity;
import com.indiancosmeticsbd.app.Views.Activity.Account.SignInActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_username;

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

    /*Product Categories*/
    private ArrayList<ProductCategoriesModelAdapter> productCategoriesModelAdapterArrayList;
    private ProductCategoriesAdapter productCategoriesAdapter;
    private MaterialCardView materialCardViewProductCategories;
    private LottieAnimationView lottieAnimationViewProductCategories;
    private RecyclerView recyclerViewProductCategories;
    private ProductCategoriesViewModel productCategoriesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setNavDrawer();
        setBottomNavigationView();
        setSliderView();
        setRecyclerViewProductCategories();
    }

    private void setRecyclerViewProductCategories(){
        productCategoriesModelAdapterArrayList = new ArrayList<>();
        recyclerViewProductCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProductCategories.setHasFixedSize(true);
        productCategoriesAdapter = new ProductCategoriesAdapter(productCategoriesModelAdapterArrayList, this);
        productCategoriesViewModel = new ViewModelProvider(this).get(ProductCategoriesViewModel.class);
        productCategoriesViewModel.init();
        productCategoriesViewModel.getProductCategories().observe(this, productCategoriesModel -> {
            ArrayList<ProductCategoriesModel.Content> content = productCategoriesModel.getContent();
            ArrayList<String> productChecking = new ArrayList<>();
            /*Here contents.main has multiple repeat. That is why it is filtered with productChecking*/
            for (ProductCategoriesModel.Content contents: content){
                String title = contents.getMain();
                if (!productChecking.contains(title)){
                    productChecking.add(title);
                }
            }
            for (String items: productChecking){
                productCategoriesModelAdapterArrayList.add(new ProductCategoriesModelAdapter(items));
            }

            if (productCategoriesModelAdapterArrayList.isEmpty()){
                materialCardViewProductCategories.setVisibility(View.GONE);
            }
            else{
                materialCardViewProductCategories.setVisibility(View.VISIBLE);
                lottieAnimationViewProductCategories.setVisibility(View.GONE);
                recyclerViewProductCategories.setVisibility(View.VISIBLE);
            }
            recyclerViewProductCategories.setAdapter(productCategoriesAdapter);
        });
    }

    private void getBannerSlider() {
        BannerSliderTopViewModel bannerSliderTopViewModel = new ViewModelProvider(this).get(BannerSliderTopViewModel.class);
        bannerSliderTopViewModel.init();
        bannerSliderTopViewModel.getBannerSlider().observe(this, bannerSliderModel -> {
            List<BannerSliderModel.Content> contents = new ArrayList<>(bannerSliderModel.getContent());
            for (BannerSliderModel.Content content : contents) {
                SliderItem sliderItem = new SliderItem();
                String mainImage = content.getImage().replace("PROTOCOLHTTP_HOSTPROJECT_FOLDERimages/slider/", "");
                sliderItem.setImageUrl("https://indiancosmeticsbd.com/images/slider/"+mainImage);
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

                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                    if (sharedPreferences.contains(user_username)){
                        startActivity(new Intent(MainActivity.this, AccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    }
                    else{
                        startActivity(new Intent(MainActivity.this, SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                    }
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

        materialCardViewProductCategories = findViewById(R.id.product_categories_main);
        lottieAnimationViewProductCategories = findViewById(R.id.animationView_products);
        recyclerViewProductCategories = findViewById(R.id.recyclerview_product_categories);
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