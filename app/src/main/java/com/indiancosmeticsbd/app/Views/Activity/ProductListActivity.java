package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.indiancosmeticsbd.app.Adapter.ProductListAdapter;
import com.indiancosmeticsbd.app.Model.ProductList.ProductListModel;
import com.indiancosmeticsbd.app.Model.ProductList.Products;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.ProductListViewModel;

import java.util.ArrayList;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class ProductListActivity extends AppCompatActivity {

    private String categoryName;
    private String categoryId;
    private TextView toolbarText;
    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private ArrayList<ProductListModel> contentArrayList;
    private ProductListViewModel productListViewModel;

    private LinearLayout empty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        categoryName = getIntent().getStringExtra("name");
        categoryId = getIntent().getStringExtra("id");
        toolbarText.setText(categoryName);
        setRecyclerView();
    }

    private void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        contentArrayList = new ArrayList<>();
        productListAdapter = new ProductListAdapter(contentArrayList, this);
        productListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        productListViewModel.init();
        productListViewModel.getProductList(this, "main", categoryId, "", "", "", "", "", false).observe(this, products -> {
            ArrayList<Products.Content> content = products.getContent();
            for (Products.Content contents: content){
                Log.d("PRODUCTLISTTING", "setRecyclerView: "+contents.getName());
                contentArrayList.add(new ProductListModel(contents.getId(), contents.getName(), contents.getBrand(), contents.getPrice(), contents.getViews(), contents.getStock(), contents.getDiscount(), contents.getThumbnail()));
            }
            if (contentArrayList.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                empty.setVisibility(View.VISIBLE);
            }
            else{
                recyclerView.setVisibility(View.VISIBLE);
                empty.setVisibility(View.GONE);
            }
            recyclerView.setAdapter(productListAdapter);
        });
    }

    private void findViewById(){
        toolbarText = findViewById(R.id.toolbar_text);
        recyclerView = findViewById(R.id.recyclerview_product_list);
        empty = findViewById(R.id.empty_image);
    }

    private void setToolbar(int toolbarId, int backButtonId){
        MaterialToolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        ImageButton backButton = findViewById(backButtonId);
        backButton.setOnClickListener(v -> onBackPressed());

    }
}