package com.indiancosmeticsbd.app.Adapter.ProductDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indiancosmeticsbd.app.Adapter.CartAdapter;
import com.indiancosmeticsbd.app.R;

import java.util.ArrayList;

public class SliderImageClickAdapter extends RecyclerView.Adapter<SliderImageClickAdapter.SliderImageClickViewHolder>
{
    private OnItemClickListener onItemClickListener;

    private ArrayList<String> imageLink;
    private Context context;

    public SliderImageClickAdapter(ArrayList<String> imageLink, Context context) {
        this.imageLink = imageLink;
        this.context = context;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SliderImageClickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_slider_image_click, parent, false);
        return new SliderImageClickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderImageClickViewHolder holder, int position) {
        Glide.with(context.getApplicationContext()).load(imageLink.get(position)).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageLink.size();
    }

    class SliderImageClickViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        public SliderImageClickViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.slider_image_click_recycler);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
