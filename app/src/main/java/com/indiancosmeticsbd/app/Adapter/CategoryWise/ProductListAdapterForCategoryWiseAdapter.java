package com.indiancosmeticsbd.app.Adapter.CategoryWise;

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
import com.google.android.material.button.MaterialButton;
import com.indiancosmeticsbd.app.Adapter.CartAdapter;
import com.indiancosmeticsbd.app.Model.ProductList.ProductListModel;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.ProductDetails.ProductDetailsActivity;

import java.util.ArrayList;

public class ProductListAdapterForCategoryWiseAdapter extends RecyclerView.Adapter<ProductListAdapterForCategoryWiseAdapter.ProductListViewHolder>
{
    private final ArrayList<ProductListModel> productsArrayList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ProductListAdapterForCategoryWiseAdapter(ArrayList<ProductListModel> productsArrayList, Context context) {
        this.productsArrayList = productsArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_product_list_for_category_wise, parent, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        ProductListModel selectedItem = productsArrayList.get(position);
        String getUrl = "https://indiancosmeticsbd.com/proimg/"+selectedItem.getId()+"/1.jpg";
        Glide.with(context.getApplicationContext()).load(getUrl).placeholder(R.drawable.ic_picture).into(holder.imageViewProductView);
        holder.textViewProductName.setText(selectedItem.getName());
        holder.textViewProductBrand.setText("by "+selectedItem.getBrand());
        holder.textViewProductPrice.setText("৳"+selectedItem.getPrice());
        Log.d("PRODUCTLISasdasd", "onBindViewHolder: "+getUrl);
        if (selectedItem.getDiscount()==0){
            holder.textViewProductDiscount.setVisibility(View.GONE);
        }
        else{
            holder.textViewProductDiscount.setVisibility(View.VISIBLE);
            double actualPrice = selectedItem.getDiscount();
            double discount = selectedItem.getPrice()*(actualPrice/100);
            double discountPrice = selectedItem.getPrice()-discount;
            holder.textViewProductDiscount.setText("৳"+discountPrice);
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

    }

    @Override
    public int getItemCount() {
        return productsArrayList.size();
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder {
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

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClicked(position);
                    }
                }
            });
        }
    }
}
