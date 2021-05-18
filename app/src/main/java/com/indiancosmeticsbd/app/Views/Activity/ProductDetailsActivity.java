package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.indiancosmeticsbd.app.Adapter.ProductDetails.ProductSizesAdapter;
import com.indiancosmeticsbd.app.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class ProductDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerViewSize;
    private ProductSizesAdapter productSizesAdapter;
    private ArrayList<String> arrayListSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        recyclerViewSize = findViewById(R.id.product_sizes_recyclerview);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        //collapsingToolbarLayout.setTitle(getTitle());
        setRecyclerViewSize();
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