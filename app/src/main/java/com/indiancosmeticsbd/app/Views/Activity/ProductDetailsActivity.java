package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.ProductDetails.ProductReviewAdapter;
import com.indiancosmeticsbd.app.Adapter.ProductDetails.ProductSizesAdapter;
import com.indiancosmeticsbd.app.Model.ProductDetails.AddToCartModel;
import com.indiancosmeticsbd.app.Model.ProductDetails.ProductInfo;
import com.indiancosmeticsbd.app.Model.ProductDetails.ProductReviewAdapterModel;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.ProductInfoViewModel;

import java.lang.reflect.Type;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import per.wsj.library.AndRatingBar;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.PRODUCT_IMAGE_BASE_URL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.WEBSITE_URL;

public class ProductDetailsActivity extends AppCompatActivity {


    /*Id and product name*/
    private String id, toolbarName;

    private int selectedItemPosition = 0;

    private NestedScrollView nestedScrollView;

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private TextView textViewBrandName, textViewPrice, textViewDiscount, textViewDescription, textViewCartText, textViewStock, textViewProductRating, textViewTotalRating, textViewTotalViews;

    private RecyclerView recyclerViewSize;
    private ProductSizesAdapter productSizesAdapter;
    private ArrayList<String> arrayListSize;

    private RecyclerView recyclerViewRating;
    private ProductReviewAdapter productReviewAdapter;
    private ArrayList<ProductReviewAdapterModel> productReviewAdapterModelArrayList;

    private MaterialButton buttonPlus, buttonMinus, buttonAddToCart;
    private ImageButton imageButtonWishList, imageButtonShare;

    private AndRatingBar ratingBar;

    private ImageView imageViewProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        findViewById();
        setToolbar(R.id.toolbar, R.id.back_button);
        getProductDetails();
    }


    private void getProductDetails(){
        nestedScrollView.setVisibility(View.GONE);
        ProductInfoViewModel productInfoViewModel = new ViewModelProvider(this).get(ProductInfoViewModel.class);
        Log.d("PRODUCT_DETAILS", "getProductDetails: Product ID: "+id);
        productInfoViewModel.init(this, id);
        productInfoViewModel.getProductInfo().observe(this, productInfo -> {
            if (productInfo!=null){
                collapsingToolbarLayout.setTitle(toolbarName);
                nestedScrollView.setVisibility(View.VISIBLE);
                ProductInfo.Content content = productInfo.getContent();
                Log.d("PRODUCT_DETAILS", "getProductDetails: "+content.getBrand());
                /*Sharing Url*/
                imageButtonShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Intent.ACTION_SEND);
                        i.setType("text/plain");
                        i.putExtra(Intent.EXTRA_SUBJECT, toolbarName);
                        i.putExtra(Intent.EXTRA_TEXT, WEBSITE_URL+content.getProductLink());
                        startActivity(Intent.createChooser(i, "Share URL"));
                    }
                });
                textViewBrandName.setText("By "+content.getBrand());
                textViewPrice.setText("৳"+content.getPrice());
                textViewStock.setText(content.getStock()+" pieces left");
                if (content.getDiscount() == 0){
                    textViewDiscount.setVisibility(View.GONE);
                }
                else{
                    textViewDiscount.setVisibility(View.VISIBLE);
                    textViewDiscount.setText("৳"+content.getDiscount()+" off!");
                }
                setRecyclerViewSize(content.getAvailableSizes());
                String url =PRODUCT_IMAGE_BASE_URL+"/"+id+"/1.jpg";
                Glide.with(this).load(url).into(imageViewProduct);
                Log.d("PRODUCT_DETAILS", "getProductDetails: "+content.getAllImages().getDefault().size());
                textViewTotalViews.setText(content.getTotalViews()+"");
                ProductInfo.Rating rating = content.getRating();
                textViewTotalRating.setText("("+rating.getTotalReviewer()+")");
                ratingBar.setRating(rating.getAverageRating().floatValue());
                textViewDescription.setText(Html.fromHtml(content.getDescription()));
                textViewProductRating.setText(String.valueOf(content.getRating().getAverageRating()));
                setRecyclerViewRating(content.getProductReviews());
            }
        });
    }

    private void viewCart(){
        SharedPreferences sharedPreferences = getSharedPreferences("CartOnly", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("cart", "");

        Type type = new TypeToken<ArrayList<AddToCartModel>>(){}.getType();
        ArrayList<AddToCartModel> addToCartModels = gson.fromJson(json, type);
        int i = 0;
        for (AddToCartModel addToCartModel : addToCartModels){
            Log.d("CartsListItems", "viewCart: Item No "+i+": "+addToCartModel.getProductId());
        }
    }

    private void addToCart(){
        ArrayList<AddToCartModel> addToCartModels = new ArrayList<>();
        addToCartModels.add(new AddToCartModel("100101", "200ml", "red", "100", "200tk", "www.facebook.com"));
        addToCartModels.add(new AddToCartModel("100101", "200ml", "red", "100", "200tk", "www.facebook.com"));
        addToCartModels.add(new AddToCartModel("100102", "200ml", "red", "100", "200tk", "www.facebook.com"));
        addToCartModels.add(new AddToCartModel("100103", "200ml", "red", "100", "200tk", "www.facebook.com"));
        addToCartModels.add(new AddToCartModel("100104", "200ml", "red", "100", "200tk", "www.facebook.com"));
        SharedPreferences sharedPreferences = getSharedPreferences("CartOnly", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(addToCartModels);
        editor.putString("cart", json);
        editor.apply();
        //AddToCartModel addToCartModel = new AddToCartModel("100101", "200ml", "red", "100", "200tk", "www.facebook.com");

    }

    private void setRecyclerViewRating(ArrayList<ProductInfo.ProductReview> productReviewArrayList){
        productReviewAdapterModelArrayList = new ArrayList<>();
        recyclerViewRating.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRating.setHasFixedSize(true);
        productReviewAdapter = new ProductReviewAdapter(productReviewAdapterModelArrayList, this);

        for (ProductInfo.ProductReview productReview: productReviewArrayList){
            String username = productReview.getUsername();
            String reviewText = productReview.getReviewText();
            String[] dates = productReview.getDate().split(" ");
            String date = dates[0];
            productReviewAdapterModelArrayList.add(new ProductReviewAdapterModel(username, date, reviewText));
        }
        recyclerViewRating.setAdapter(productReviewAdapter);
    }

    private void setRecyclerViewSize(ArrayList<String> sizes){
        recyclerViewSize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSize.setHasFixedSize(true);
        productSizesAdapter = new ProductSizesAdapter(sizes, this);
        productSizesAdapter.setOnItemClickListener(new ProductSizesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                selectedItemPosition = position;
                Toasty.info(ProductDetailsActivity.this, sizes.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewSize.setAdapter(productSizesAdapter);
    }

    private void setToolbar(int toolbarId, int backButtonId){
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
        Log.d("PRODUCT_DETAILS", "setToolbar: Product name: "+toolbarName);
        id = String.valueOf(getIntent().getIntExtra("id", 0));

    }

    private void findViewById(){
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

    }

    private void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}