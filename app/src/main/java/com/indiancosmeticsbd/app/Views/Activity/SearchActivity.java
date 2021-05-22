package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageButton;
import android.widget.LinearLayout;


import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.indiancosmeticsbd.app.Adapter.ProductListAdapter;
import com.indiancosmeticsbd.app.Model.ProductList.ProductListModel;
import com.indiancosmeticsbd.app.Model.ProductList.Products;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.ProductListViewModel;

import java.util.ArrayList;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private ArrayList<ProductListModel> contentArrayList;

    private LinearLayout empty;
    private LottieAnimationView lottieAnimationView;
    private TextInputEditText textInputEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        setRecyclerView("");
        textInputEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                setRecyclerView(v.getText().toString());
                return true;
            }
            return false;
        });
    }

    private void setRecyclerView(String searchData){
        isLoaded(false);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        contentArrayList = new ArrayList<>();
        productListAdapter = new ProductListAdapter(contentArrayList, this);
        ProductListViewModel productListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        productListViewModel.init();
        productListViewModel.getProductList(this, "", "", "", searchData, "", "", "", true).observe(this, products -> {
            if (products!=null){
                isLoaded(true);
                ArrayList<Products.Content> content = products.getContent();
                for (Products.Content contents: content){
                    Log.d("PRODUCTLISTTING", "setRecyclerView: "+contents.getName());
                    contentArrayList.add(new ProductListModel(contents.getId(), contents.getName(), contents.getBrand(), contents.getPrice(), contents.getViews(), contents.getStock(), contents.getDiscount(), contents.getThumbnail()));
                }
                if (contentArrayList.isEmpty()){
                    recyclerView.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.GONE);
                    empty.setVisibility(View.VISIBLE);
                }
                else{
                    lottieAnimationView.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
                recyclerView.setAdapter(productListAdapter);
            }
            else{
                setRecyclerView(searchData);
            }
        });
    }

    private void isLoaded(boolean loaded){
        if (loaded){
            lottieAnimationView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
        else{
            lottieAnimationView.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
        empty.setVisibility(View.GONE);
    }

    private void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }

    private void setToolbar(int toolbarId, int backButtonId){
        MaterialToolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        ImageButton backButton = findViewById(backButtonId);
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void findViewById(){
        recyclerView = findViewById(R.id.recyclerview_search_product_list);
        empty = findViewById(R.id.empty_image);
        textInputEditText = findViewById(R.id.search_edittext);
        lottieAnimationView = findViewById(R.id.search_lottie);
    }

}