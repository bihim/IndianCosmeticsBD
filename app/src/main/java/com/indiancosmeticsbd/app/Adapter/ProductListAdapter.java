package com.indiancosmeticsbd.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indiancosmeticsbd.app.Model.ProductList.ProductListModel;
import com.indiancosmeticsbd.app.Model.ProductList.Products;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.ProductDetailsActivity;

import java.util.ArrayList;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder>
{
    private ArrayList<ProductListModel> productsArrayList;
    private Context context;

    public ProductListAdapter(ArrayList<ProductListModel> productsArrayList, Context context) {
        this.productsArrayList = productsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_product_list, parent, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        ProductListModel selectedItem = productsArrayList.get(position);
        String getUrl = "https://indiancosmeticsbd.com/"+selectedItem.getThumbnail().replace("thumbnail", "1");
        Glide.with(context.getApplicationContext()).load(getUrl).placeholder(R.drawable.ic_no_image).into(holder.imageViewProductView);
        holder.textViewProductName.setText(selectedItem.getName());
        holder.textViewProductBrand.setText("by "+selectedItem.getBrand());
        holder.textViewProductPrice.setText("৳"+selectedItem.getPrice());
        Log.d("PRODUCTLISasdasd", "onBindViewHolder: "+getUrl);
        if (selectedItem.getDiscount()==0){
            holder.textViewProductDiscount.setVisibility(View.GONE);
        }
        else{
            holder.textViewProductDiscount.setVisibility(View.VISIBLE);
            holder.textViewProductDiscount.setText("৳"+selectedItem.getDiscount());
        }
        if (selectedItem.getViews() == 0){
            holder.textViewProductViews.setVisibility(View.GONE);
        }
        else{
            holder.textViewProductViews.setVisibility(View.VISIBLE);
            holder.textViewProductViews.setText(selectedItem.getViews()+"");
        }
        if (selectedItem.getStock() == 0){
            holder.textViewProductStock.setText("Stock Out");
        }
        else{
            holder.textViewProductStock.setText(selectedItem.getStock()+"pc left");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id", selectedItem.getId());
                Log.d("PRODUCT_LIST", "onClick: id; "+selectedItem.getId());
                intent.putExtra("name", selectedItem.getName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public static class ProductListViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewProductView;
        TextView textViewProductName, textViewProductBrand, textViewProductPrice, textViewProductDiscount, textViewProductViews, textViewProductStock;
        public ProductListViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProductView = itemView.findViewById(R.id.recyclerview_product_image);
            textViewProductName = itemView.findViewById(R.id.recyclerview_product_name);
            textViewProductBrand = itemView.findViewById(R.id.recyclerview_product_brand);
            textViewProductPrice = itemView.findViewById(R.id.recyclerview_product_price);
            textViewProductDiscount = itemView.findViewById(R.id.recyclerview_product_discount);
            textViewProductViews = itemView.findViewById(R.id.recyclerview_product_views);
            textViewProductStock = itemView.findViewById(R.id.recyclerview_product_stock);
        }
    }
}
