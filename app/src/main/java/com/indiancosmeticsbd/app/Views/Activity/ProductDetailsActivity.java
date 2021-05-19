package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.ProductDetails.ProductReviewAdapter;
import com.indiancosmeticsbd.app.Adapter.ProductDetails.ProductSizesAdapter;
import com.indiancosmeticsbd.app.Model.ProductDetails.AddToCartModel;
import com.indiancosmeticsbd.app.Model.ProductDetails.ProductReviewAdapterModel;
import com.indiancosmeticsbd.app.R;

import java.lang.reflect.Type;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class ProductDetailsActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;

    private TextView textViewBrandName, textViewPrice, textViewDiscount, textViewDescription;

    private RecyclerView recyclerViewSize;
    private ProductSizesAdapter productSizesAdapter;
    private ArrayList<String> arrayListSize;

    private RecyclerView recyclerViewRating;
    private ProductReviewAdapter productReviewAdapter;
    private ArrayList<ProductReviewAdapterModel> productReviewAdapterModelArrayList;

    private MaterialButton buttonPlus, buttonMinus, buttonAddToCart;
    private ImageButton imageButtonWishList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        recyclerViewSize = findViewById(R.id.product_sizes_recyclerview);
        recyclerViewRating = findViewById(R.id.product_details_review);
        setRecyclerViewSize();
        setRecyclerViewRating();
        MaterialButton materialButton = findViewById(R.id.add_cart);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
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

    private void setRecyclerViewRating(){
        productReviewAdapterModelArrayList = new ArrayList<>();
        recyclerViewRating.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewRating.setHasFixedSize(true);
        productReviewAdapter = new ProductReviewAdapter(productReviewAdapterModelArrayList, this);

        productReviewAdapterModelArrayList.add(new ProductReviewAdapterModel("Test User", "test@mail.com", getString(R.string.demo_date), getString(R.string.demo_description)));
        productReviewAdapterModelArrayList.add(new ProductReviewAdapterModel("Test User2", "test2@mail.com", getString(R.string.demo_date), getString(R.string.demo_description)));
        productReviewAdapterModelArrayList.add(new ProductReviewAdapterModel("Test User3", "test3@mail.com", getString(R.string.demo_date), getString(R.string.demo_description)));
        productReviewAdapterModelArrayList.add(new ProductReviewAdapterModel("Test User4", "test4@mail.com", getString(R.string.demo_date), getString(R.string.demo_description)));
        productReviewAdapterModelArrayList.add(new ProductReviewAdapterModel("Test User5", "test5@mail.com", getString(R.string.demo_date), getString(R.string.demo_description)));

        recyclerViewRating.setAdapter(productReviewAdapter);
    }

    private void setRecyclerViewSize(){
        arrayListSize = new ArrayList<>();
        recyclerViewSize.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewSize.setHasFixedSize(true);
        productSizesAdapter = new ProductSizesAdapter(arrayListSize, this);

        arrayListSize.add("200 ml");
        arrayListSize.add("500 ml");
        arrayListSize.add("100 ml");
        arrayListSize.add("450 ml");
        arrayListSize.add("1000 ml");

        productSizesAdapter.setOnItemClickListener(new ProductSizesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toasty.info(ProductDetailsActivity.this, arrayListSize.get(position), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerViewSize.setAdapter(productSizesAdapter);
    }

    private void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }
}