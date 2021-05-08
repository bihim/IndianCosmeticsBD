package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.indiancosmeticsbd.app.Adapter.ProductListAdapter;
import com.indiancosmeticsbd.app.Model.ProductList.ProductListModel;
import com.indiancosmeticsbd.app.Model.ProductList.Products;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.ProductListViewModel;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

    private String categoryName;
    private String categoryId;
    private TextView toolbarText;
    private TextView textViewNoData;

    private RecyclerView recyclerView;
    private ProductListAdapter productListAdapter;
    private ArrayList<ProductListModel> contentArrayList;
    private ProductListViewModel productListViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        categoryName = getIntent().getStringExtra("name");
        categoryId = getIntent().getStringExtra("id");
        toolbarText.setText(categoryName);
        setRecyclerView();
    }

    private void setRecyclerView(){
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        contentArrayList = new ArrayList<>();
        productListAdapter = new ProductListAdapter(contentArrayList, this);
        productListViewModel = new ViewModelProvider(this).get(ProductListViewModel.class);
        productListViewModel.init();
        productListViewModel.getProductList(this, "header", categoryId, "", "", "", "", "").observe(this, products -> {
            ArrayList<Products.Content> content = products.getContent();
            for (Products.Content contents: content){
                Log.d("PRODUCTLISTTING", "setRecyclerView: "+contents.getName());
                contentArrayList.add(new ProductListModel(contents.getId(), contents.getName(), contents.getBrand(), contents.getPrice(), contents.getViews(), contents.getStock(), contents.getDiscount(), contents.getThumbnail()));
            }
            if (contentArrayList.isEmpty()){
                recyclerView.setVisibility(View.GONE);
                textViewNoData.setVisibility(View.VISIBLE);
            }
            else{
                recyclerView.setVisibility(View.VISIBLE);
                textViewNoData.setVisibility(View.GONE);
            }
            recyclerView.setAdapter(productListAdapter);
        });
    }

    private void findViewById(){
        toolbarText = findViewById(R.id.toolbar_text);
        recyclerView = findViewById(R.id.recyclerview_product_list);
        textViewNoData = findViewById(R.id.no_data);
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

    }
}