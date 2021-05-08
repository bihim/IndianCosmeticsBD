package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.indiancosmeticsbd.app.Adapter.CategoriesSelectedAdapter;
import com.indiancosmeticsbd.app.Model.Category.CategoryAdapterModel;
import com.indiancosmeticsbd.app.R;

import java.util.ArrayList;

public class ProductCategoryItemActivity extends AppCompatActivity {
    private TextView textViewNoData;
    private RecyclerView recyclerViewCategory;
    private TextView toolbarText;
    private ArrayList<String> categories;
    private String categoryName;
    private ArrayList<CategoryAdapterModel> categoryAdapterModels;
    private CategoriesSelectedAdapter categoriesSelectedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category_item);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        categoryName = getIntent().getStringExtra("category_name");
        toolbarText.setText(categoryName);
        categories = new ArrayList<>(getIntent().getStringArrayListExtra("categories"));
        setRecyclerViewCategory();
    }

    private void setRecyclerViewCategory(){
        categoryAdapterModels = new ArrayList<>();
        categoriesSelectedAdapter = new CategoriesSelectedAdapter(categoryAdapterModels, this);
        //recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewCategory.setHasFixedSize(true);
        for (String category: categories){
            String []splet = category.split(",");
            String categoryName = splet[0];
            String categoryId = splet[1];
            categoryAdapterModels.add(new CategoryAdapterModel(categoryId, categoryName));
        }
        recyclerViewCategory.setAdapter(categoriesSelectedAdapter);
    }

    private void findViewById(){
        textViewNoData = findViewById(R.id.no_data);
        recyclerViewCategory = findViewById(R.id.recyclerview_categories_item);
        toolbarText = findViewById(R.id.toolbar_text);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}