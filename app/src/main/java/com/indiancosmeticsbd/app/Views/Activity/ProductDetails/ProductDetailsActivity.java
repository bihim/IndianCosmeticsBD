package com.indiancosmeticsbd.app.Views.Activity.ProductDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
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
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.Model.ProductDetails.ProductInfo;
import com.indiancosmeticsbd.app.Model.ProductDetails.ProductReviewAdapterModel;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.ProductInfoViewModel;
import com.indiancosmeticsbd.app.Views.Activity.Account.SignInActivity;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.CartActivity;
import com.indiancosmeticsbd.app.Views.Activity.Orders.OrderSubmitActivity;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.lang.reflect.Type;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import per.wsj.library.AndRatingBar;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART_DIRECT_ORDER;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.PRODUCT_IMAGE_BASE_URL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.WEBSITE_URL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.WISHLIST;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_username;

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

    private MaterialButton buttonPlus, buttonMinus, buttonAddToCart, buttonOrderNow;
    private ImageButton imageButtonWishList, imageButtonShare;

    private AndRatingBar ratingBar;

    private ImageView imageViewProduct;

    private boolean isProductExists = false;

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
            isProductExists = true;
            SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString(CART, "");
            Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
            ArrayList<Cart> carts = gson.fromJson(json, type);
            for (Cart cart : carts) {
                if (id.equals(cart.getProductId())){
                    quantity = Integer.parseInt(cart.getQuantity());
                    textViewCartText.setText(cart.getQuantity());
                }
            }
        }
        setImageButtonWishList();
        //isWishlistAdded = productExistsWishList();
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
                    buttonPlus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonColorLight)));
                    buttonMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonColorLight)));
                } else {
                    textViewCartText.setText(String.valueOf(quantity));
                    buttonPlus.setEnabled(false);
                    buttonMinus.setEnabled(true);
                    buttonPlus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyLight)));
                    buttonMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonColorLight)));
                    //Toasty.info(ProductDetailsActivity.this, "Can not add more product", Toast.LENGTH_SHORT).show();
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
                    buttonPlus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonColorLight)));
                    buttonMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyLight)));
                    Toasty.info(ProductDetailsActivity.this, "Can not remove more product", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    if (quantity > 1) {
                        textViewCartText.setText(String.valueOf(quantity));
                        buttonMinus.setEnabled(true);
                        buttonPlus.setEnabled(true);
                        buttonPlus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonColorLight)));
                        buttonMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonColorLight)));
                    } else {
                        textViewCartText.setText(String.valueOf(quantity));
                        buttonMinus.setEnabled(false);
                        buttonPlus.setEnabled(true);
                        buttonPlus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.buttonColorLight)));
                        buttonMinus.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.greyLight)));
                        Toasty.info(ProductDetailsActivity.this, "Can not remove more product", Toast.LENGTH_SHORT).show();
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
                        i.putExtra(Intent.EXTRA_TEXT, WEBSITE_URL + content.getProductLink().replace("PROTOCOLHTTP_HOSTPROJECT_FOLDER/",""));
                        startActivity(Intent.createChooser(i, "Share URL"));
                    }
                });
                textViewBrandName.setText("By " + content.getBrand());
                textViewStock.setText(content.getStock() + " pieces left");
                if (content.getDiscount() == 0) {
                    textViewDiscount.setVisibility(View.GONE);
                    textViewPrice.setText("৳" + content.getPrice());
                } else {
                    textViewDiscount.setVisibility(View.VISIBLE);
                    double discount = content.getDiscount();
                    double discountPrice = content.getPrice()*(discount/100);
                    double actualPrice = content.getPrice() - discountPrice;
                    textViewDiscount.setText("৳"+content.getPrice());
                    textViewPrice.setText("৳" + actualPrice);
                }
                setRecyclerViewSize(content.getAvailableSizes());
                String url = PRODUCT_IMAGE_BASE_URL + "/" + id + "/1.jpg";
                Glide.with(this).load(url).into(imageViewProduct);
                Log.d("PRODUCT_DETAILS", "getProductDetails: " + content.getAllImages().size());
                textViewTotalViews.setText(content.getTotalViews() + "");
                ProductInfo.Rating rating = content.getRating();
                textViewTotalRating.setText("(" + rating.getTotalReviewer() + ")");
                ratingBar.setRating(rating.getAverageRating().floatValue());
                textViewDescription.setText(Html.fromHtml(content.getDescription()));
                textViewProductRating.setText(String.valueOf(content.getRating().getAverageRating()));
                setRecyclerViewRating(content.getProductReviews());
                setSliderView(content.getAllImages());
                setQuantity(content.getStock());

                buttonOrderNow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToCartDirectOrder(id, content.getBrand(), content.getName(), String.valueOf(content.getPrice()), String.valueOf(quantity), String.valueOf(content.getDiscount()), content.getAvailableSizes().get(selectedItemPosition), url, String.valueOf(content.getStock()));
                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                        if (sharedPreferences.contains(user_username)){
                            Intent intent = new Intent(ProductDetailsActivity.this, OrderSubmitActivity.class);
                            intent.putExtra("directOrder", true);
                            startActivity(intent);
                        }
                        else{
                            startActivity(new Intent(ProductDetailsActivity.this, SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
                        }
                    }
                });

                buttonAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToCart(id, content.getBrand(), content.getName(), String.valueOf(content.getPrice()), String.valueOf(quantity), String.valueOf(content.getDiscount()), content.getAvailableSizes().get(selectedItemPosition), url, String.valueOf(content.getStock()));
                        Snackbar.make(v, isProductExists ? R.string.snackbar_text_updated: R.string.snackbar_text, Snackbar.LENGTH_LONG).setAction(R.string.continue_cart, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onBackPressed();
                            }
                        }).show();
                    }
                });
                imageButtonWishList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToWishList(id, content.getBrand(), content.getName(), String.valueOf(content.getPrice()), String.valueOf(quantity), String.valueOf(content.getDiscount()), content.getAvailableSizes().get(selectedItemPosition), url, String.valueOf(content.getStock()));
                        setImageButtonWishList();
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

        Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
        ArrayList<Cart> carts = gson.fromJson(json, type);
        int i = 0;
        for (Cart cart : carts) {
            Log.d("CartsListItems", "viewCart: Item No " + i + ": " + cart.getProductId());
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
            Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
            ArrayList<Cart> carts = gson.fromJson(json, type);
            ArrayList<String> productIds = new ArrayList<>();
            for (Cart cart : carts) {
                productIds.add(cart.getProductId());
            }
            return productIds.contains(id);
        }

    }

    private boolean productExistsWishList(){
        SharedPreferences sharedPreferences = getSharedPreferences(WISHLIST, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(WISHLIST, "");
        if (json.equals("")){
            return false;
        }
        else{
            Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
            ArrayList<Cart> carts = gson.fromJson(json, type);
            ArrayList<String> productIds = new ArrayList<>();
            for (Cart cart : carts) {
                productIds.add(cart.getProductId());
            }
            return productIds.contains(id);
        }

    }

    private void addToCart(String id, String brandName, String productName, String price, String quantity, String discount, String size, String imageUrl, String stock) {
        SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (productExists()){
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
        }
        else{
            String cartExists = sharedPreferences.getString(CART, "");
            if (cartExists.equals("")){
                ArrayList<Cart> carts = new ArrayList<>();
                carts.add(new Cart(id, brandName, productName, price, quantity, discount, size, imageUrl, stock));
                Gson gson = new Gson();
                String json = gson.toJson(carts);
                editor.putString(CART, json);
            }
            else{
                Gson gson = new Gson();
                String json = sharedPreferences.getString(CART, "");
                Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
                ArrayList<Cart> carts = gson.fromJson(json, type);
                carts.add(new Cart(id, brandName, productName, price, quantity, discount, size, imageUrl, stock));
                String addedJson = gson.toJson(carts);
                editor.putString(CART, addedJson);
            }
        }
        editor.apply();

        //AddToCartModel addToCartModel = new AddToCartModel("100101", "200ml", "red", "100", "200tk", "www.facebook.com");
    }
    private void addToCartDirectOrder(String id, String brandName, String productName, String price, String quantity, String discount, String size, String imageUrl, String stock) {
        SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        ArrayList<Cart> carts = new ArrayList<>();
        carts.add(new Cart(id, brandName, productName, price, quantity, discount, size, imageUrl, stock));
        Gson gson = new Gson();
        String json = gson.toJson(carts);
        editor.putString(CART_DIRECT_ORDER, json);
        /*if (productExists()){
            Gson gson = new Gson();
            String json = sharedPreferences.getString(CART_DIRECT_ORDER, "");
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
            editor.putString(CART_DIRECT_ORDER, updatedJson);
        }
        else{
            String cartExists = sharedPreferences.getString(CART_DIRECT_ORDER, "");
            if (cartExists.equals("")){
                ArrayList<Cart> carts = new ArrayList<>();
                carts.add(new Cart(id, brandName, productName, price, quantity, discount, size, imageUrl, stock));
                Gson gson = new Gson();
                String json = gson.toJson(carts);
                editor.putString(CART_DIRECT_ORDER, json);
            }
            else{
                Gson gson = new Gson();
                String json = sharedPreferences.getString(CART_DIRECT_ORDER, "");
                Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
                ArrayList<Cart> carts = gson.fromJson(json, type);
                carts.add(new Cart(id, brandName, productName, price, quantity, discount, size, imageUrl, stock));
                String addedJson = gson.toJson(carts);
                editor.putString(CART_DIRECT_ORDER, addedJson);
            }
        }*/
        editor.apply();

        //AddToCartModel addToCartModel = new AddToCartModel("100101", "200ml", "red", "100", "200tk", "www.facebook.com");
    }

    private void addToWishList(String id, String brandName, String productName, String price, String quantity, String discount, String size, String imageUrl, String stock) {
        SharedPreferences sharedPreferences = getSharedPreferences(WISHLIST, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (productExistsWishList()){
            Gson gson = new Gson();
            String json = sharedPreferences.getString(WISHLIST, "");
            Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
            ArrayList<Cart> carts = gson.fromJson(json, type);

            for (int i = carts.size()-1; i >= 0; i--){
                if (carts.get(i).getProductId().equals(id)){
                    carts.remove(i);
                }
            }
            String updatedJson = gson.toJson(carts);
            editor.putString(WISHLIST, updatedJson);
        }
        else{
            String cartExists = sharedPreferences.getString(WISHLIST, "");
            if (cartExists.equals("")){
                ArrayList<Cart> carts = new ArrayList<>();
                carts.add(new Cart(id, brandName, productName, price, quantity, discount, size, imageUrl, stock));
                Gson gson = new Gson();
                String json = gson.toJson(carts);
                editor.putString(WISHLIST, json);
            }
            else{
                Gson gson = new Gson();
                String json = sharedPreferences.getString(WISHLIST, "");
                Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
                ArrayList<Cart> carts = gson.fromJson(json, type);
                carts.add(new Cart(id, brandName, productName, price, quantity, discount, size, imageUrl, stock));
                String addedJson = gson.toJson(carts);
                editor.putString(WISHLIST, addedJson);
            }
        }
        editor.apply();
    }

    private void setImageButtonWishList(){
        if (productExistsWishList()){
            imageButtonWishList.setImageResource(R.drawable.ic_wish_red);
        }
        else{
            imageButtonWishList.setImageResource(R.drawable.ic_wish_outlined);
        }
    }

    /*private void updateCart(){
        SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(CART, "");
        Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
        ArrayList<Cart> carts = gson.fromJson(json, type);
        for (int i = 0; i < carts.size(); i++){
            if (carts.get(i).getProductId().equals(id)){
                String id = carts.get(i).getProductId();
                String productName = carts.get(i).getProductName();
                String brandName = carts.get(i).getBrandName();
                String price = carts.get(i).getPrice();
                String updateQuantity = String.valueOf(quantity);
                String discount = carts.get(i).getDiscount();
                String size = carts.get(i).getSize();
                String imageUrl = carts.get(i).getImageUrl();
                carts.set(i, new Cart(id, brandName, productName, price, updateQuantity, discount, size, imageUrl));
            }
        }
    }*/

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
        buttonOrderNow = findViewById(R.id.product_details_order_now);

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