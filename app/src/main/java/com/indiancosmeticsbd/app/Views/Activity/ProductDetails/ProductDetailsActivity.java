package com.indiancosmeticsbd.app.Views.Activity.ProductDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.ProductDetails.ProductReviewAdapter;
import com.indiancosmeticsbd.app.Adapter.ProductDetails.ProductSizesAdapter;
import com.indiancosmeticsbd.app.Adapter.SliderAdapterExample;
import com.indiancosmeticsbd.app.Model.BannerSlider.SliderItem;
import com.indiancosmeticsbd.app.Model.ProductDetails.AddToCartModel;
import com.indiancosmeticsbd.app.Model.ProductDetails.ProductInfo;
import com.indiancosmeticsbd.app.Model.ProductDetails.ProductReviewAdapterModel;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.ProductInfoViewModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import per.wsj.library.AndRatingBar;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.PRODUCT_IMAGE_BASE_URL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.WEBSITE_URL;

public class ProductDetailsActivity extends AppCompatActivity {


    /*Id and product name*/
    private String id, toolbarName;

    private int selectedItemPosition = 0;
    private int quantity = 1;

    private NestedScrollView nestedScrollView;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private TextView textViewBrandName, textViewPrice, textViewDiscount, textViewDescription, textViewCartText, textViewStock, textViewProductRating, textViewTotalRating, textViewTotalViews;

    private RecyclerView recyclerViewSize;
    private ProductSizesAdapter productSizesAdapter;

    private RecyclerView recyclerViewRating;
    private ProductReviewAdapter productReviewAdapter;
    private ArrayList<ProductReviewAdapterModel> productReviewAdapterModelArrayList;

    private MaterialButton buttonPlus, buttonMinus, buttonAddToCart;
    private ImageButton imageButtonWishList, imageButtonShare;

    private AndRatingBar ratingBar;

    private ImageView imageViewProduct;

    /*Banner Slider Top*/
    private SliderView sliderView;
    private SliderAdapterExample sliderAdapterExample;
    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        findViewById();
        setToolbar(R.id.toolbar, R.id.back_button);
        getProductDetails();
        if (productExists()){
            buttonAddToCart.setText("UPDATE CART");
            SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(CART, "");
            Type type = new TypeToken<ArrayList<AddToCartModel>>() {}.getType();
            ArrayList<AddToCartModel> addToCartModels = gson.fromJson(json, type);
            for (AddToCartModel addToCartModel : addToCartModels) {
                if (id.equals(addToCartModel.getProductId())){
                    quantity = Integer.parseInt(addToCartModel.getQuantity());
                    textViewCartText.setText(addToCartModel.getQuantity());
                }
            }
        }
    }


    private void setQuantity(Integer stock) {
        //textViewCartText.setText(String.valueOf(quantity));
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantity++;
                if (quantity < stock) {
                    textViewCartText.setText(String.valueOf(quantity));
                    buttonPlus.setEnabled(true);
                    buttonMinus.setEnabled(true);
                } else {
                    textViewCartText.setText(String.valueOf(quantity));
                    buttonPlus.setEnabled(false);
                    buttonMinus.setEnabled(true);
                    Toasty.info(ProductDetailsActivity.this, "Can not add more product: " + quantity, Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity == 1) {
                    textViewCartText.setText(String.valueOf(quantity));
                    buttonMinus.setEnabled(false);
                    buttonPlus.setEnabled(true);
                    Toasty.info(ProductDetailsActivity.this, "Can not remove more product: " + quantity, Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    if (quantity > 1) {
                        textViewCartText.setText(String.valueOf(quantity));
                        buttonMinus.setEnabled(true);
                        buttonPlus.setEnabled(true);
                    } else {
                        textViewCartText.setText(String.valueOf(quantity));
                        buttonMinus.setEnabled(false);
                        buttonPlus.setEnabled(true);
                        Toasty.info(ProductDetailsActivity.this, "Can not remove more product: " + quantity, Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    private void getProductDetails() {
        nestedScrollView.setVisibility(View.GONE);
        ProductInfoViewModel productInfoViewModel = new ViewModelProvider(this).get(ProductInfoViewModel.class);
        Log.d("PRODUCT_DETAILS", "getProductDetails: Product ID: " + id);
        productInfoViewModel.init(this, id);
        productInfoViewModel.getProductInfo().observe(this, productInfo -> {
            if (productInfo != null) {
                collapsingToolbarLayout.setTitle(toolbarName);
                nestedScrollView.setVisibility(View.VISIBLE);
                ProductInfo.Content content = productInfo.getContent();
                Log.d("PRODUCT_DETAILS", "getProductDetails: " + content.getBrand());
                /*Sharing Url*/
                imageButtonShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, toolbarName);
                        i.putExtra(Intent.EXTRA_TEXT, WEBSITE_URL + content.getProductLink());
                        startActivity(Intent.createChooser(i, "Share URL"));
                    }
                });
                textViewBrandName.setText("By " + content.getBrand());
                textViewPrice.setText("৳" + content.getPrice());
                textViewStock.setText(content.getStock() + " pieces left");
                if (content.getDiscount() == 0) {
                    textViewDiscount.setVisibility(View.GONE);
                } else {
                    textViewDiscount.setVisibility(View.VISIBLE);
                    textViewDiscount.setText("৳" + content.getDiscount() + " off!");
                }
                setRecyclerViewSize(content.getAvailableSizes());
                String url = PRODUCT_IMAGE_BASE_URL + "/" + id + "/1.jpg";
                Glide.with(this).load(url).into(imageViewProduct);
                Log.d("PRODUCT_DETAILS", "getProductDetails: " + content.getAllImages().getDefault().size());
                textViewTotalViews.setText(content.getTotalViews() + "");
                ProductInfo.Rating rating = content.getRating();
                textViewTotalRating.setText("(" + rating.getTotalReviewer() + ")");
                ratingBar.setRating(rating.getAverageRating().floatValue());
                textViewDescription.setText(Html.fromHtml(content.getDescription()));
                textViewProductRating.setText(String.valueOf(content.getRating().getAverageRating()));
                setRecyclerViewRating(content.getProductReviews());
                setSliderView(content.getAllImages().getDefault());
                setQuantity(content.getStock());
                buttonAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToCart(id, content.getBrand(), content.getName(), String.valueOf(content.getPrice()), String.valueOf(quantity), String.valueOf(content.getDiscount()), content.getAvailableSizes().get(selectedItemPosition), url);
                        Snackbar.make(v, R.string.snackbar_text, Snackbar.LENGTH_LONG).setAction(R.string.continue_cart, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        }).show();
                    }
                });
            }
        });
    }

    private void setSliderView(ArrayList<String> images) {
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

        for (int i = 0; i < images.size(); i++) {
            String url = PRODUCT_IMAGE_BASE_URL + "/" + id + "/" + (i + 1) + ".jpg";
            SliderItem sliderItem = new SliderItem();
            sliderItem.setImageUrl(url);
            sliderAdapterExample.addItem(sliderItem);
        }
        if (images.isEmpty()) {
            lottieAnimationView.setVisibility(View.VISIBLE);
            sliderView.setVisibility(View.GONE);
        } else {
            lottieAnimationView.setVisibility(View.GONE);
            sliderView.setVisibility(View.VISIBLE);
        }

    }

    private void viewCart() {
        SharedPreferences sharedPreferences = getSharedPreferences("CartOnly", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cart", "");

        Type type = new TypeToken<ArrayList<AddToCartModel>>() {}.getType();
        ArrayList<AddToCartModel> addToCartModels = gson.fromJson(json, type);
        int i = 0;
        for (AddToCartModel addToCartModel : addToCartModels) {
            Log.d("CartsListItems", "viewCart: Item No " + i + ": " + addToCartModel.getProductId());
        }
    }

    private boolean productExists(){
        SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CART, "");
        if (json.equals("")){
            return false;
        }
        else{
            Type type = new TypeToken<ArrayList<AddToCartModel>>() {}.getType();
            ArrayList<AddToCartModel> addToCartModels = gson.fromJson(json, type);
            ArrayList<String> productIds = new ArrayList<>();
            for (AddToCartModel addToCartModel : addToCartModels) {
                productIds.add(addToCartModel.getProductId());
            }
            return productIds.contains(id);
        }

    }

    private void addToCart(String id, String brandName, String productName, String price, String quantity, String discount, String size, String imageUrl) {
        SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (productExists()){
            Gson gson = new Gson();
            String json = sharedPreferences.getString(CART, "");
            Type type = new TypeToken<ArrayList<AddToCartModel>>() {}.getType();
            ArrayList<AddToCartModel> addToCartModels = gson.fromJson(json, type);
            for (int i = 0; i < addToCartModels.size(); i++){
                if (addToCartModels.get(i).getProductId().equals(id)){
                    String updatedId = addToCartModels.get(i).getProductId();
                    String updatedProductName = addToCartModels.get(i).getProductName();
                    String updatedBrandName = addToCartModels.get(i).getBrandName();
                    String updatedPrice = addToCartModels.get(i).getPrice();
                    String updateQuantity = String.valueOf(quantity);
                    String updatedDiscount = addToCartModels.get(i).getDiscount();
                    String updatedSize = addToCartModels.get(i).getSize();
                    String updatedImageUrl = addToCartModels.get(i).getImageUrl();
                    addToCartModels.set(i, new AddToCartModel(updatedId, updatedBrandName, updatedProductName, updatedPrice, updateQuantity, updatedDiscount, updatedSize, updatedImageUrl));
                }
            }
            String updatedJson = gson.toJson(addToCartModels);
            editor.putString(CART, updatedJson);
        }
        else{
            String cartExists = sharedPreferences.getString(CART, "");
            if (cartExists.equals("")){
                ArrayList<AddToCartModel> addToCartModels = new ArrayList<>();
                addToCartModels.add(new AddToCartModel(id, brandName, productName, price, quantity, discount, size, imageUrl));
                Gson gson = new Gson();
                String json = gson.toJson(addToCartModels);
                editor.putString(CART, json);
            }
            else{
                Gson gson = new Gson();
                String json = sharedPreferences.getString(CART, "");
                Type type = new TypeToken<ArrayList<AddToCartModel>>() {}.getType();
                ArrayList<AddToCartModel> addToCartModels = gson.fromJson(json, type);
                addToCartModels.add(new AddToCartModel(id, brandName, productName, price, quantity, discount, size, imageUrl));
                String addedJson = gson.toJson(addToCartModels);
                editor.putString(CART, addedJson);
            }
        }
        editor.apply();

        //AddToCartModel addToCartModel = new AddToCartModel("100101", "200ml", "red", "100", "200tk", "www.facebook.com");
    }

    private void updateCart(){
        SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CART, "");
        Type type = new TypeToken<ArrayList<AddToCartModel>>() {}.getType();
        ArrayList<AddToCartModel> addToCartModels = gson.fromJson(json, type);
        for (int i = 0; i < addToCartModels.size(); i++){
            if (addToCartModels.get(i).getProductId().equals(id)){
                String id = addToCartModels.get(i).getProductId();
                String productName = addToCartModels.get(i).getProductName();
                String brandName = addToCartModels.get(i).getBrandName();
                String price = addToCartModels.get(i).getPrice();
                String updateQuantity = String.valueOf(quantity);
                String discount = addToCartModels.get(i).getDiscount();
                String size = addToCartModels.get(i).getSize();
                String imageUrl = addToCartModels.get(i).getImageUrl();
                addToCartModels.set(i, new AddToCartModel(id, brandName, productName, price, updateQuantity, discount, size, imageUrl));
            }
        }
    }

    private void setRecyclerViewRating(ArrayList<ProductInfo.ProductReview> productReviewArrayList) {
        productReviewAdapterModelArrayList = new ArrayList<>();
        recyclerViewRating.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRating.setHasFixedSize(true);
        productReviewAdapter = new ProductReviewAdapter(productReviewAdapterModelArrayList, this);

        for (ProductInfo.ProductReview productReview : productReviewArrayList) {
            String username = productReview.getUsername();
            String reviewText = productReview.getReviewText();
            String[] dates = productReview.getDate().split(" ");
            String date = dates[0];
            productReviewAdapterModelArrayList.add(new ProductReviewAdapterModel(username, date, reviewText));
        }
        recyclerViewRating.setAdapter(productReviewAdapter);
    }

    private void setRecyclerViewSize(ArrayList<String> sizes) {
        recyclerViewSize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSize.setHasFixedSize(true);
        productSizesAdapter = new ProductSizesAdapter(sizes, this);
        productSizesAdapter.setOnItemClickListener(new ProductSizesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectedItemPosition = position;
                //Toasty.info(ProductDetailsActivity.this, selectedItemPosition+"", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewSize.setAdapter(productSizesAdapter);
    }

    private void setToolbar(int toolbarId, int backButtonId) {
        MaterialToolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        ImageButton backButton = findViewById(backButtonId);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbarName = getIntent().getStringExtra("name");
        Log.d("PRODUCT_DETAILS", "setToolbar: Product name: " + toolbarName);
        id = String.valueOf(getIntent().getIntExtra("id", 0));

    }

    private void findViewById() {
        nestedScrollView = findViewById(R.id.product_details_main);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        recyclerViewSize = findViewById(R.id.product_sizes_recyclerview);
        recyclerViewRating = findViewById(R.id.product_details_review);

        textViewBrandName = findViewById(R.id.product_details_brand_name);
        textViewPrice = findViewById(R.id.product_details_price);
        textViewDiscount = findViewById(R.id.product_details_discount);
        textViewDescription = findViewById(R.id.product_details_description);

        buttonPlus = findViewById(R.id.product_details_cart_plus);
        buttonMinus = findViewById(R.id.product_details_cart_minus);
        buttonAddToCart = findViewById(R.id.product_details_cart_add);
        textViewCartText = findViewById(R.id.product_details_cart_text);

        imageButtonShare = findViewById(R.id.product_details_share);
        imageButtonWishList = findViewById(R.id.product_details_wish);
        textViewStock = findViewById(R.id.product_details_stock);

        imageViewProduct = findViewById(R.id.product_details_image);
        textViewProductRating = findViewById(R.id.product_details_rating);
        ratingBar = findViewById(R.id.product_details_rating_in_stars);
        textViewTotalRating = findViewById(R.id.product_details_total_ratings);
        textViewTotalViews = findViewById(R.id.product_details_total_views);

        sliderView = findViewById(R.id.imageSlider);
        lottieAnimationView = findViewById(R.id.animationView);

    }

    private void setTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}