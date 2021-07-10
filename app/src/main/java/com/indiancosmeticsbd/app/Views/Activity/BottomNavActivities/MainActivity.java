package com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.indiancosmeticsbd.app.Adapter.BottomBannerAdapter;
import com.indiancosmeticsbd.app.Adapter.CategoryWise.CategoriesByViewAdapter;
import com.indiancosmeticsbd.app.Adapter.ProductCategoriesAdapter;
import com.indiancosmeticsbd.app.Adapter.SliderAdapterExample;
import com.indiancosmeticsbd.app.Views.Dialogs.ExitDialog;
import com.indiancosmeticsbd.app.Views.Dialogs.NumberDialog;
import com.indiancosmeticsbd.app.Model.BannerSlider.BannerSliderModel;
import com.indiancosmeticsbd.app.Model.BannerSlider.SliderItem;
import com.indiancosmeticsbd.app.Model.Category.CategorySelectedModel;
import com.indiancosmeticsbd.app.Model.CategoryWiseViewModel.CategoryWiseViewModel;
import com.indiancosmeticsbd.app.Model.CategoryWiseViewModel.CategoryWiseViewModel2;
import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesAdapterModel;
import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesModel;
import com.indiancosmeticsbd.app.Model.ProductList.ProductListModel;
import com.indiancosmeticsbd.app.Model.ProductList.Products;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.BannerSliderTopViewModel;
import com.indiancosmeticsbd.app.ViewModel.ProductCategoriesViewModel;
import com.indiancosmeticsbd.app.ViewModel.ProductListViewModel;
import com.indiancosmeticsbd.app.Views.Activity.Account.AccountActivity;
import com.indiancosmeticsbd.app.Views.Activity.Account.SignInActivity;
import com.indiancosmeticsbd.app.Views.Activity.Orders.ViewOrdersActivity;
import com.indiancosmeticsbd.app.Views.Activity.ProductListActivity;
import com.indiancosmeticsbd.app.Views.Activity.SearchActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.HOME_PAGE_BOTTOM;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.HOME_PAGE_MIDDLE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.HOME_PAGE_TOP;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHOWBADGE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.NOTIFICATION_SHOW;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.WISHLIST;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_date;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_email;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_first_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_last_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_orders;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_username;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    /*Nav Drawer*/
    private MaterialButton signInButton;
    private SwitchCompat switchCompatTheme;
    private ImageView imageViewTheme;
    private MaterialButton navHelpline;
    private MaterialButton navOrders;
    private MaterialButton navRateUs;

    private LinearLayout navNoUser, navUserInfo;
    private TextView navUserName, navUserEmail;

    /*Product Categories*/
    private ArrayList<ProductCategoriesAdapterModel> productCategoriesModelAdapterArrayList;
    private ProductCategoriesAdapter productCategoriesAdapter;
    private MaterialCardView materialCardViewProductCategories;
    private LottieAnimationView lottieAnimationViewProductCategories;
    private RecyclerView recyclerViewProductCategories;
    private ProductCategoriesViewModel productCategoriesViewModel;
    private ArrayList<CategorySelectedModel> categories;

    private ImageButton searchButton;

    private SharedPreferences sharedPreferences;

    /*Recyclerview withing recyclerview*/
    private ArrayList<CategoryWiseViewModel> categoryWiseViewModelArrayList = new ArrayList<>();

    private MaterialCardView dummyCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setNavDrawer();
        setBottomNavigationView();
        setSliderView(HOME_PAGE_TOP, R.id.imageSlider, R.id.banner_slider);
        setSliderView(HOME_PAGE_MIDDLE, R.id.imageSlider_mid, R.id.banner_slider_mid);
        //setSliderView(HOME_PAGE_BOTTOM, R.id.imageSlider_bottom, R.id.banner_slider_bottom);
        setBottomBanner();
        setRecyclerViewProductCategories();
        themeSwitch();
        SHOWBADGE(this, CART, R.id.bottom_nav_cart, bottomNavigationView);
        SHOWBADGE(this, WISHLIST, R.id.bottom_nav_wishlist, bottomNavigationView);
        NOTIFICATION_SHOW(this, bottomNavigationView);
        testUserOrder();
    }

    private void setBottomBanner(){
        MaterialCardView materialCardView = findViewById(R.id.banner_slider_bottom);
        RecyclerView recyclerView = findViewById(R.id.main_bottom_recyclerview);
        ArrayList<String> imageUrl = new ArrayList<>();
        BottomBannerAdapter bottomBannerAdapter = new BottomBannerAdapter(imageUrl, this);
        BannerSliderTopViewModel bannerSliderTopViewModel = new ViewModelProvider(this).get(BannerSliderTopViewModel.class);
        bannerSliderTopViewModel.init();
        bannerSliderTopViewModel.getBannerSlider(HOME_PAGE_BOTTOM).observe(this, bannerSliderModel -> {
            if (bannerSliderModel == null) {
                setBottomBanner();
            } else {
                List<BannerSliderModel.Content> contents = new ArrayList<>(bannerSliderModel.getContent());
                for (BannerSliderModel.Content content : contents) {
                    imageUrl.add(content.getImage());
                    Log.d("BANNERSLIDE", "onResponse: content: " + content.getImage());
                }
                if (contents.isEmpty()) {
                    materialCardView.setVisibility(View.GONE);
                } else {
                    materialCardView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
            recyclerView.setAdapter(bottomBannerAdapter);
        });
    }

    private void setRecyclerViewCategoryWiseView2(List<CategoryWiseViewModel> categoryWiseView2, int recyclerviewID) {
        RecyclerView recyclerViewCategoryWiseView = findViewById(recyclerviewID);
        recyclerViewCategoryWiseView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCategoryWiseView.setHasFixedSize(true);
        recyclerViewCategoryWiseView.setNestedScrollingEnabled(false);
        ArrayList<CategoryWiseViewModel2> categoryWiseViewModel2s = new ArrayList<>();
        CategoriesByViewAdapter categoriesByViewAdapter2 = new CategoriesByViewAdapter(categoryWiseViewModel2s, this);
        categoriesByViewAdapter2.setOnItemClickListener(new CategoriesByViewAdapter.OnItemClickListener() {
            @Override
            public void onViewAllClicked(int position) {
                CategoryWiseViewModel2 selectedItem = categoryWiseViewModel2s.get(position);
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra("id", selectedItem.getId());
                intent.putExtra("name", selectedItem.getName());
                startActivity(intent);
            }
        });
        for (CategoryWiseViewModel getCategoryWiseView : categoryWiseView2) {
            ProductListViewModel productListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
            productListViewModel.init();
            productListViewModel.getProductList(this, "main", getCategoryWiseView.getId(), "", "", "", "", "", true).observe(this, products -> {
                dummyCardView.setVisibility(View.GONE);
                recyclerViewCategoryWiseView.setAdapter(categoriesByViewAdapter2);
                ArrayList<Products.Content> content = products.getContent();
                ArrayList<ProductListModel> contentArrayList = new ArrayList<>();
                Log.d("PRODUCTLISTTING", "setRecyclerView: " + getCategoryWiseView.getName());
                Log.d("GETTINGSIZE", "setRecyclerViewCategoryWiseView2: size:"+content.size());
                for (Products.Content contents : content) {
                    contentArrayList.add(new ProductListModel(contents.getId(), contents.getName(), contents.getBrand(), contents.getPrice(), contents.getViews(), contents.getStock(), contents.getDiscount(), contents.getThumbnail()));
                }
                if (content.size()!=0){
                    categoryWiseViewModel2s.add(new CategoryWiseViewModel2(getCategoryWiseView.getId(), getCategoryWiseView.getName(), contentArrayList));
                }
                if (contentArrayList.isEmpty()) {
                    recyclerViewCategoryWiseView.setVisibility(View.GONE);
                } else {
                    recyclerViewCategoryWiseView.setVisibility(View.VISIBLE);
                }
            });
        }

    }

    private void setTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }

    private void themeSwitch() {
        /*False == Light Theme
         * True == Dark Theme*/
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        switchCompatTheme.setChecked(!theme.equals("light"));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean drawerOpen = getIntent().getBooleanExtra("drawer_open", false);
        if (drawerOpen) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        if (theme.equals("light")) {
            switchCompatTheme.setChecked(false);
            imageViewTheme.setImageResource(R.drawable.ic_sun);
        } else {
            switchCompatTheme.setChecked(true);
            imageViewTheme.setImageResource(R.drawable.ic_moon);
        }
        switchCompatTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (theme.equals("light")) {
                    editor.putString("theme", "dark");
                    switchCompatTheme.setChecked(false);
                } else {
                    editor.putString("theme", "light");
                    switchCompatTheme.setChecked(true);
                }

                editor.apply();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                intent.putExtra("drawer_open", true);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        searchButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SearchActivity.class)));
    }

    private void setRecyclerViewProductCategories() {
        /*Summery
        * Here I have taken an extra arraylist in order to get all the header of one main category
        * That means the json is like this..
        * {
      "id": "1",
      "main": "indian products",
      "header": "skin products",
      "sub": "",
      "position": "1"
    },
    {
      "id": "2",
      "main": "thai products",
      "header": "skin products",
      "sub": "",
      "position": "2"
    },
    {
      "id": "3",
      "main": "the soumi's can products",
      "header": "skin products",
      "sub": "",
      "position": "3"
    },
    {
      "id": "18",
      "main": "indian products",
      "header": "slimming products",
      "sub": "",
      "position": "1"
    }
    * you can see that header repeats. And the api which I have got is not good. It didn't give me image and category searching is weird.
    * Another thing is I need id to get products. Urrrgh!!
    * Whatever have fun*/
        productCategoriesModelAdapterArrayList = new ArrayList<>();
        categories = new ArrayList<>();
        recyclerViewProductCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewProductCategories.setHasFixedSize(true);
        productCategoriesAdapter = new ProductCategoriesAdapter(productCategoriesModelAdapterArrayList, categories, this);
        productCategoriesViewModel = new ViewModelProvider(this).get(ProductCategoriesViewModel.class);
        productCategoriesViewModel.init();
        productCategoriesViewModel.getProductCategories().observe(this, productCategoriesModel -> {
            if (productCategoriesModel == null) {
                setRecyclerViewProductCategories();
            } else {
                ArrayList<ProductCategoriesModel.Content> content = productCategoriesModel.getContent();
                ArrayList<String> productChecking = new ArrayList<>();
                /*Here contents.main has multiple repeat. That is why it is filtered with productChecking*/
                for (ProductCategoriesModel.Content contents : content) {
                    String title = contents.getMain();
                    String header = contents.getHeader();
                    String getImageLink = contents.getCatimg();
                    String imageLink = getImageLink.substring(0, getImageLink.length()-5)+"2.jpg";
                    Log.d("IMAGELINKS", "setRecyclerViewProductCategories: "+imageLink);
                    categories.add(new CategorySelectedModel(title, header, contents.getId()));
                    if (!productChecking.contains(title)) {
                        productChecking.add(title);
                        productCategoriesModelAdapterArrayList.add(new ProductCategoriesAdapterModel(title, imageLink));
                        categoryWiseViewModelArrayList.add(new CategoryWiseViewModel(contents.getId(), title));
                        Log.d("GETIDSALONGWITH", "setRecyclerViewProductCategories: title: " + title + " id: " + contents.getId());
                    }
                }

                List<CategoryWiseViewModel> categoryWiseViewModelFirstTwo = categoryWiseViewModelArrayList.subList(0, 2);
                List<CategoryWiseViewModel> categoryWiseViewModelLastItems = categoryWiseViewModelArrayList.subList(2, categoryWiseViewModelArrayList.size());
                setRecyclerViewCategoryWiseView2(categoryWiseViewModelFirstTwo, R.id.main_category_wise_recyclerview);
                new Handler(Looper.getMainLooper()).postDelayed(() -> setRecyclerViewCategoryWiseView2(categoryWiseViewModelLastItems, R.id.main_category_wise_recyclerview_after_mid), 1000);
                //setRecyclerViewCategoryWiseView2(categoryWiseViewModelLastItems, R.id.main_category_wise_recyclerview_after_mid);
                Log.d("SIZEDATA", "setRecyclerViewProductCategories: 1:"+categoryWiseViewModelFirstTwo.size()+" 2:"+categoryWiseViewModelLastItems.size()+" 3:"+categoryWiseViewModelArrayList.size());

                if (productCategoriesModelAdapterArrayList.isEmpty()) {
                    materialCardViewProductCategories.setVisibility(View.GONE);
                } else {
                    materialCardViewProductCategories.setVisibility(View.VISIBLE);
                    lottieAnimationViewProductCategories.setVisibility(View.GONE);
                    recyclerViewProductCategories.setVisibility(View.VISIBLE);
                }
                recyclerViewProductCategories.setAdapter(productCategoriesAdapter);
            }
        });
    }

    private void getBannerSlider(SliderAdapterExample sliderAdapterExample, String bannerSliderPosition, SliderView sliderView, MaterialCardView materialCardView) {
        BannerSliderTopViewModel bannerSliderTopViewModel = new ViewModelProvider(this).get(BannerSliderTopViewModel.class);
        bannerSliderTopViewModel.init();
        bannerSliderTopViewModel.getBannerSlider(bannerSliderPosition).observe(this, bannerSliderModel -> {
            if (bannerSliderModel == null) {
                getBannerSlider(sliderAdapterExample, bannerSliderPosition, sliderView, materialCardView);
            } else {
                List<BannerSliderModel.Content> contents = new ArrayList<>(bannerSliderModel.getContent());
                for (BannerSliderModel.Content content : contents) {
                    SliderItem sliderItem = new SliderItem();
                    /*String mainImage = content.getImage().replace("PROTOCOLHTTP_HOSTPROJECT_FOLDERimages/slider/", "");
                    sliderItem.setImageUrl("https://indiancosmeticsbd.com/images/slider/" + mainImage);*/
                    sliderItem.setImageUrl(content.getImage());
                    sliderAdapterExample.addItem(sliderItem);
                    Log.d("BANNERSLIDE", "onResponse: content: " + content.getImage());
                }
                if (contents.isEmpty()) {
                    materialCardView.setVisibility(View.GONE);
                } else {
                    materialCardView.setVisibility(View.VISIBLE);
                    sliderView.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setSliderView(String bannerSliderPosition, int sliderId, int cardId) {
        SliderView sliderView = findViewById(sliderId);
        MaterialCardView materialCardView = findViewById(cardId);
        SliderAdapterExample sliderAdapterExample = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(sliderAdapterExample);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(true);
        sliderView.startAutoCycle();
        getBannerSlider(sliderAdapterExample, bannerSliderPosition, sliderView, materialCardView);
    }

    private void setBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_nav_home) {
                    //
                } else if (item.getItemId() == R.id.bottom_nav_wishlist) {
                    startActivity(new Intent(MainActivity.this, WishListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.bottom_nav_cart) {
                    startActivity(new Intent(MainActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.bottom_nav_account) {

                    SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                    if (sharedPreferences.contains(user_username)) {
                        startActivity(new Intent(MainActivity.this, AccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    } else {
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.Open, R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navHelpline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberDialog numberDialog = new NumberDialog(MainActivity.this);
                numberDialog.showDialog();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        navOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferences.contains(user_username)) {
                    startActivity(new Intent(MainActivity.this, ViewOrdersActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                }
            }
        });

        navRateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String appPackageName = getPackageName(); // package name of the app
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
            }
        });

        if (sharedPreferences.contains(user_username)){
            navNoUser.setVisibility(View.GONE);
            navUserInfo.setVisibility(View.VISIBLE);
            navUserName.setText(sharedPreferences.getString(user_first_name,"")+" "+sharedPreferences.getString(user_last_name, " "));
            navUserEmail.setText(sharedPreferences.getString(user_email, ""));
        }
        else{
            navNoUser.setVisibility(View.VISIBLE);
            navUserInfo.setVisibility(View.GONE);
        }

    }

    private void testUserOrder() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                String getOrders = sharedPreferences.getString(user_orders, "");
                String getDate = sharedPreferences.getString(user_date, "");
                Log.d("USERORDERS", "onClick: order: " + getOrders + " date: " + getDate);*/
                startActivity(new Intent(MainActivity.this, SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                overridePendingTransition(0, 0);
            }
        });
    }

    private void findViewById() {
        toolbar = findViewById(R.id.toolbar_main);
        drawerLayout = findViewById(R.id.nav_drawer);
        navigationView = findViewById(R.id.navigation_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        signInButton = findViewById(R.id.sign_in_main);

        materialCardViewProductCategories = findViewById(R.id.product_categories_main);
        lottieAnimationViewProductCategories = findViewById(R.id.animationView_products);
        recyclerViewProductCategories = findViewById(R.id.recyclerview_product_categories);

        switchCompatTheme = findViewById(R.id.nav_theme_switch);
        imageViewTheme = findViewById(R.id.imageView_theme);
        searchButton = findViewById(R.id.searchButton);
        //recyclerViewCategoryWiseView = findViewById(R.id.main_category_wise_recyclerview);

        dummyCardView = findViewById(R.id.dummy_card);
        navHelpline = findViewById(R.id.nav_order_helpline);
        navOrders = findViewById(R.id.nav_orders);

        navNoUser = findViewById(R.id.nav_no_user);
        navUserInfo = findViewById(R.id.nav_user_info);
        navUserName = findViewById(R.id.nav_drawer_profile_text);
        navUserEmail = findViewById(R.id.nav_drawer_profile_email);
        navRateUs = findViewById(R.id.nav_rate_us);
    }

    @Override
    public void onBackPressed() {
        ExitDialog exitDialog = new ExitDialog(this);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            exitDialog.dismissDialog();
        } else {
            //super.onBackPressed();
            exitDialog.showDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_home);
        SHOWBADGE(this, CART, R.id.bottom_nav_cart, bottomNavigationView);
        SHOWBADGE(this, WISHLIST, R.id.bottom_nav_wishlist, bottomNavigationView);
        NOTIFICATION_SHOW(this, bottomNavigationView);
    }
}