package com.indiancosmeticsbd.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.indiancosmeticsbd.app.Adapter.ProductDetails.ProductSizesAdapter;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.ProductDetails.ProductDetailsActivity;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>
{
    private ArrayList<Cart> cartArrayList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void plusClick(int position);
        void minusClick(int position);
        void onDeleteClicked(int position);
        void onItemClicked(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public CartAdapter(ArrayList<Cart> cartArrayList, Context context) {
        this.cartArrayList = cartArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart cart = cartArrayList.get(position);
        holder.brandsName.setText(cart.getBrandName());
        holder.productName.setText(cart.getProductName());
        Glide.with(context.getApplicationContext()).load(cart.getImageUrl()).into(holder.imageView);
        int price = Integer.parseInt(cart.getPrice());
        int quantity = Integer.parseInt(cart.getQuantity());
        holder.priceXquantity.setText("৳"+price+" x "+quantity);
        holder.totalPrice.setText("৳"+(price*quantity));
        if (cart.getDiscount().equals("0")){
            holder.discount.setVisibility(View.GONE);
        }
        else{
            holder.discount.setVisibility(View.VISIBLE);
            holder.discount.setText(cart.getDiscount());
        }
        holder.size.setText(cart.getSize());
        holder.quantity.setText(cart.getQuantity());
    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView brandsName, productName, priceXquantity, totalPrice, discount, size, quantity;
        ImageView imageView;
        MaterialCardView plus, minus;
        ImageButton delete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            brandsName = itemView.findViewById(R.id.recyclerview_cart_brand_name);
            productName = itemView.findViewById(R.id.recyclerview_cart_product_name);
            priceXquantity = itemView.findViewById(R.id.recyclerview_cart_price);
            totalPrice = itemView.findViewById(R.id.recyclerview_cart_total_price);
            discount = itemView.findViewById(R.id.recyclerview_cart_discount);
            size = itemView.findViewById(R.id.recyclerview_cart_size);
            quantity = itemView.findViewById(R.id.recyclerview_cart_quantity);
            imageView = itemView.findViewById(R.id.recyclerview_cart_image);
            plus = itemView.findViewById(R.id.recyclerview_cart_plus);
            minus = itemView.findViewById(R.id.recyclerview_cart_minus);
            delete = itemView.findViewById(R.id.recyclerview_cart_delete);

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.plusClick(position);
                        }
                    }
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.minusClick(position);
                        }
                    }
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
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
        }
    }
}
