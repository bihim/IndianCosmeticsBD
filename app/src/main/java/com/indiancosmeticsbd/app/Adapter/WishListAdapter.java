package com.indiancosmeticsbd.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.R;

import java.util.ArrayList;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishListViewHolder>
{
    private ArrayList<Cart> wishListArray;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onDeleteClicked(int position);
        void onItemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public WishListAdapter(ArrayList<Cart> wishListArray, Context context) {
        this.wishListArray = wishListArray;
        this.context = context;
    }

    @NonNull
    @Override
    public WishListViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_wishlist, parent, false);
        return new WishListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListViewHolder holder, int position) {
        Cart wishlist = wishListArray.get(position);
        holder.brandName.setText(wishlist.getBrandName());
        holder.productName.setText(wishlist.getProductName());
        Glide.with(context.getApplicationContext()).load(wishlist.getImageUrl()).into(holder.imageView);
        int price = Integer.parseInt(wishlist.getPrice());
        if (wishlist.getDiscount().equals("0")){
            holder.price.setVisibility(View.GONE);
            holder.priceAfterDiscount.setText("৳"+price);
        }
        else{
            double discount = price*(Double.parseDouble(wishlist.getDiscount())/100);
            double actualPrice = price-discount;
            holder.priceAfterDiscount.setText("৳"+actualPrice);
            holder.priceAfterDiscount.setVisibility(View.VISIBLE);
            holder.price.setText(price);
        }
    }

    @Override
    public int getItemCount() {
        return wishListArray.size();
    }

    public class WishListViewHolder extends RecyclerView.ViewHolder {
        TextView productName, brandName, price, priceAfterDiscount;
        ImageView imageView;
        ImageButton imageButton;
        public WishListViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.recyclerview_wishlist_product_name);
            brandName = itemView.findViewById(R.id.recyclerview_wishlist_brand_name);
            price = itemView.findViewById(R.id.recyclerview_wishlist_price);
            priceAfterDiscount = itemView.findViewById(R.id.recyclerview_wishlist_price_after_discount);

            imageView = itemView.findViewById(R.id.recyclerview_wishlist_image);
            imageButton = itemView.findViewById(R.id.recyclerview_wishlist_delete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClicked(position);
                        }
                    }
                }
            });

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onDeleteClicked(position);
                        }
                    }
                }
            });
        }
    }
}
