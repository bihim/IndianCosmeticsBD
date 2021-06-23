package com.indiancosmeticsbd.app.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
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
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.R;

import java.util.ArrayList;

public class OrderSubmitProductAdapter extends RecyclerView.Adapter<OrderSubmitProductAdapter.CartViewHolder>
{
    private ArrayList<Cart> cartArrayList;
    private Context context;

    public OrderSubmitProductAdapter(ArrayList<Cart> cartArrayList, Context context) {
        this.cartArrayList = cartArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_order_submit_products, parent, false);
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
        int stock = Integer.parseInt(cart.getStock());
        if (cart.getDiscount().equals("0")){
            holder.discount.setVisibility(View.GONE);
            holder.priceXquantity.setText("৳"+price+" x "+quantity);
            holder.totalPrice.setText("৳"+(price*quantity));
        }
        else{
            double discount = price*(Double.parseDouble(cart.getDiscount())/100);
            double actualPrice = price-discount;
            holder.priceXquantity.setText("৳"+actualPrice+" x "+quantity);
            holder.totalPrice.setText("৳"+(actualPrice*quantity));
            holder.discount.setVisibility(View.VISIBLE);
            holder.discount.setText(price);
        }
        holder.size.setText(cart.getSize());

    }

    @Override
    public int getItemCount() {
        return cartArrayList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        TextView brandsName, productName, priceXquantity, totalPrice, discount, size;
        ImageView imageView;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            brandsName = itemView.findViewById(R.id.recyclerview_cart_brand_name);
            productName = itemView.findViewById(R.id.recyclerview_cart_product_name);
            priceXquantity = itemView.findViewById(R.id.recyclerview_cart_price);
            totalPrice = itemView.findViewById(R.id.recyclerview_cart_total_price);
            discount = itemView.findViewById(R.id.recyclerview_cart_discount);
            size = itemView.findViewById(R.id.recyclerview_cart_size);
            imageView = itemView.findViewById(R.id.recyclerview_cart_image);
        }
    }
}
