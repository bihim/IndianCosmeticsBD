package com.indiancosmeticsbd.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.ProductDetails.PhotoViewActivity;

import java.util.ArrayList;

public class BottomBannerAdapter extends RecyclerView.Adapter<BottomBannerAdapter.BottomBannerViewHolder> {

    private final ArrayList<String> imageList;
    private final Context context;

    public BottomBannerAdapter(ArrayList<String> imageList, Context context) {
        this.imageList = imageList;
        this.context = context;
    }

    @NonNull
    @Override
    public BottomBannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_bottom_banner, parent, false);
        return new BottomBannerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BottomBannerAdapter.BottomBannerViewHolder holder, int position) {
        String imageUrl = imageList.get(position);
        Glide.with(context.getApplicationContext()).load(imageUrl).placeholder(R.drawable.ic_picture).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, PhotoViewActivity.class).putExtra("image_url", imageUrl));
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class BottomBannerViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public BottomBannerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recyclerview_bottom_image);
        }
    }
}
