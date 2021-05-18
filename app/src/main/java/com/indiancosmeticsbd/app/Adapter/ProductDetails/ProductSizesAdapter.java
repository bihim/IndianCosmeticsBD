package com.indiancosmeticsbd.app.Adapter.ProductDetails;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiancosmeticsbd.app.R;

import java.util.ArrayList;

public class ProductSizesAdapter extends RecyclerView.Adapter<ProductSizesAdapter.ProductSizesViewHolder>
{
    private final ArrayList<String> productSizeList;
    private final Context context;
    private OnItemClickListener onItemClickListener;
    int selected_position = 0;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public ProductSizesAdapter(ArrayList<String> productSizeList, Context context) {
        this.productSizeList = productSizeList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public ProductSizesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_product_size, parent, false);
        return new ProductSizesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductSizesViewHolder holder, int position) {
        holder.textView.setText(productSizeList.get(position));
        holder.linearLayout.setBackgroundColor(selected_position == position ? context.getResources().getColor(R.color.forgotPassDark): Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return productSizeList.size();
    }

    public class ProductSizesViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView textView;
        public ProductSizesViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.recyclerview_product_sizes_layout);
            textView = itemView.findViewById(R.id.recyclerview_product_sizes_text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
                    notifyItemChanged(selected_position);
                    selected_position = getAdapterPosition();
                    notifyItemChanged(selected_position);

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
