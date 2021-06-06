package com.indiancosmeticsbd.app.Views.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.CategoryWise.CategoriesByViewAdapter;
import com.indiancosmeticsbd.app.Adapter.CategoryWise.CategoriesByViewAdapter2;
import com.indiancosmeticsbd.app.Adapter.ProductCategoriesAdapter;
import com.indiancosmeticsbd.app.Adapter.ProductListAdapter;
import com.indiancosmeticsbd.app.Adapter.SliderAdapterExample;
import com.indiancosmeticsbd.app.Model.BannerSlider.BannerSliderModel;
import com.indiancosmeticsbd.app.Model.BannerSlider.SliderItem;
import com.indiancosmeticsbd.app.Model.Category.CategorySelectedModel;
import com.indiancosmeticsbd.app.Model.CategoryWiseViewModel.CategoryWiseViewModel;
import com.indiancosmeticsbd.app.Model.CategoryWiseViewModel.CategoryWiseViewModel2;
import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesAdapterModel;
import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesModel;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.Model.ProductList.ProductListModel;
import com.indiancosmeticsbd.app.Model.ProductList.Products;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.BannerSliderTopViewModel;
import com.indiancosmeticsbd.app.ViewModel.ProductCategoriesViewModel;
import com.indiancosmeticsbd.app.ViewModel.ProductListViewModel;
import com.indiancosmeticsbd.app.Views.Activity.Account.AccountActivity;
import com.indiancosmeticsbd.app.Views.Activity.Account.SignInActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHOWBADGE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.WISHLIST;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_username;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    /*Banner Slider Top*/
    private MaterialCardView cardViewBannerTop;
    private SliderView sliderView;
    private SliderAdapterExample sliderAdapterExample;

    /*Nav Drawer*/
    private MaterialButton signInButton;
    private SwitchCompat switchCompatTheme;
    private ImageView imageViewTheme;

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
    private RecyclerView recyclerViewCategoryWiseView;
    private CategoriesByViewAdapter categoriesByViewAdapter;
    private ArrayList<ProductListModel> contentArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        setNavDrawer();
        setBottomNavigationView();
        setSliderView();
        setRecyclerViewProductCategories();
        themeSwitch();
        SHOWBADGE(this, CART, R.id.bottom_nav_cart, bottomNavigationView);
        SHOWBADGE(this, WISHLIST, R.id.bottom_nav_wishlist, bottomNavigationView);
    }

    private void setRecyclerViewCategoryWiseView(String id, String title) {
        ArrayList<CategoryWiseViewModel> categoryWiseViewModels = new ArrayList<>();
        categoryWiseViewModels.add(new CategoryWiseViewModel(id, title));
        ProductListViewModel productListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        productListViewModel.init();
        productListViewModel.getProductList(this, "main", id, "", "", "", "", "", true).observe(this, products -> {
            recyclerViewCategoryWiseView.setLayoutManager(new LinearLayoutManager(this));
            recyclerViewCategoryWiseView.setHasFixedSize(true);
            categoriesByViewAdapter = new CategoriesByViewAdapter(categoryWiseViewModels, contentArrayList, this);
            categoriesByViewAdapter.setOnItemClickListener(new CategoriesByViewAdapter.OnItemClickListener() {
                @Override
                public void onViewAllClicked(int position) {
                    CategoryWiseViewModel selectedItem = categoryWiseViewModels.get(position);
                    Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                    intent.putExtra("id", selectedItem.getId());
                    intent.putExtra("name", selectedItem.getName());
                    startActivity(intent);
                }
            });
            recyclerViewCategoryWiseView.setAdapter(categoriesByViewAdapter);

            ArrayList<Products.Content> content = products.getContent();
            //contentArrayList.clear();
            Log.d("PRODUCTLISTTING", "setRecyclerView: " + title);
            for (Products.Content contents : content) {
                //Log.d("PRODUCTLISTTING", "setRecyclerView: "+categoryWiseViewModel.getName()+" brand: "+contents.getBrand());
                contentArrayList.add(new ProductListModel(contents.getId(), contents.getName(), contents.getBrand(), contents.getPrice(), contents.getViews(), contents.getStock(), contents.getDiscount(), contents.getThumbnail()));
            }
            if (contentArrayList.isEmpty()) {
                recyclerViewCategoryWiseView.setVisibility(View.GONE);
            } else {
                recyclerViewCategoryWiseView.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setRecyclerViewCategoryWiseView2(ArrayList<CategoryWiseViewModel> categoryWiseView2) {
        recyclerViewCategoryWiseView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCategoryWiseView.setHasFixedSize(true);
        ArrayList<CategoryWiseViewModel2> categoryWiseViewModel2s = new ArrayList<>();
        CategoriesByViewAdapter2 categoriesByViewAdapter2 = new CategoriesByViewAdapter2(categoryWiseViewModel2s, this);
        categoriesByViewAdapter2.setOnItemClickListener(new CategoriesByViewAdapter2.OnItemClickListener() {
            @Override
            public void onViewAllClicked(int position) {
                CategoryWiseViewModel2 selectedItem = categoryWiseViewModel2s.get(position);
                Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                intent.putExtra("id", selectedItem.getId());
                intent.putExtra("name", selectedItem.getName());
                startActivity(intent);
            }
        });
        for (CategoryWiseViewModel getCategoryWiseView: categoryWiseView2){
            ProductListViewModel productListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
            productListViewModel.init();
            productListViewModel.getProductList(this, "main", getCategoryWiseView.getId(), "", "", "", "", "", true).observe(this, products -> {

                recyclerViewCategoryWiseView.setAdapter(categoriesByViewAdapter2);
                ArrayList<Products.Content> content = products.getContent();
                ArrayList<ProductListModel> contentArrayList = new ArrayList<>();
                //contentArrayList.clear();
                Log.d("PRODUCTLISTTING", "setRecyclerView: " + getCategoryWiseView.getName());
                for (Products.Content contents : content) {
                    //Log.d("PRODUCTLISTTING", "setRecyclerView: "+categoryWiseViewModel.getName()+" brand: "+contents.getBrand());
                    contentArrayList.add(new ProductListModel(contents.getId(), contents.getName(), contents.getBrand(), contents.getPrice(), contents.getViews(), contents.getStock(), contents.getDiscount(), contents.getThumbnail()));
                }
                categoryWiseViewModel2s.add(new CategoryWiseViewModel2(getCategoryWiseView.getId(), getCategoryWiseView.getName(), contentArrayList));
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
                Intent intent = new Intent(getIntent());
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
                    categories.add(new CategorySelectedModel(title, header, contents.getId()));
                    if (!productChecking.contains(title)) {
                        productChecking.add(title);
                        categoryWiseViewModelArrayList.add(new CategoryWiseViewModel(contents.getId(), title));
                        Log.d("GETIDSALONGWITH", "setRecyclerViewProductCategories: title: " + title + " id: " + contents.getId());
                    }
                }
                setRecyclerViewCategoryWiseView2(categoryWiseViewModelArrayList);

                for (String items : productChecking) {
                    productCategoriesModelAdapterArrayList.add(new ProductCategoriesAdapterModel(items));
                }

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

    private void getBannerSlider() {
        BannerSliderTopViewModel bannerSliderTopViewModel = new ViewModelProvider(this).get(BannerSliderTopViewModel.class);
        bannerSliderTopViewModel.init();
        bannerSliderTopViewModel.getBannerSlider().observe(this, bannerSliderModel -> {
            if (bannerSliderModel == null) {
                getBannerSlider();
            } else {
                List<BannerSliderModel.Content> contents = new ArrayList<>(bannerSliderModel.getContent());
                for (BannerSliderModel.Content content : contents) {
                    SliderItem sliderItem = new SliderItem();
                    String mainImage = content.getImage().replace("PROTOCOLHTTP_HOSTPROJECT_FOLDERimages/slider/", "");
                    sliderItem.setImageUrl("https://indiancosmeticsbd.com/images/slider/" + mainImage);
                    sliderAdapterExample.addItem(sliderItem);
                    Log.d("BANNERSLIDE", "onResponse: content: " + content.getImage());
                }
                if (contents.isEmpty()) {
                    cardViewBannerTop.setVisibility(View.GONE);
                } else {
                    cardViewBannerTop.setVisibility(View.VISIBLE);
                    sliderView.setVisibility(View.VISIBLE);
                }
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
                    if (sharedPreferences.contains(user_username)) {
                        startActivity(new Intent(MainActivity.this, AccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
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
    }

    private void findViewById() {
        toolbar = findViewById(R.id.toolbar_main);
        drawerLayout = findViewById(R.id.nav_drawer);
        navigationView = findViewById(R.id.navigation_main);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        sliderView = findViewById(R.id.imageSlider);
        cardViewBannerTop = findViewById(R.id.banner_slider);
        signInButton = findViewById(R.id.sign_in_main);

        materialCardViewProductCategories = findViewById(R.id.product_categories_main);
        lottieAnimationViewProductCategories = findViewById(R.id.animationView_products);
        recyclerViewProductCategories = findViewById(R.id.recyclerview_product_categories);

        switchCompatTheme = findViewById(R.id.nav_theme_switch);
        imageViewTheme = findViewById(R.id.imageView_theme);
        searchButton = findViewById(R.id.searchButton);
        recyclerViewCategoryWiseView = findViewById(R.id.main_category_wise_recyclerview);
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
        SHOWBADGE(this, CART, R.id.bottom_nav_cart, bottomNavigationView);
        SHOWBADGE(this, WISHLIST, R.id.bottom_nav_wishlist, bottomNavigationView);
    }
}