package com.indiancosmeticsbd.app.Adapter.ProductDetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiancosmeticsbd.app.Model.ProductDetails.ProductReviewAdapterModel;
import com.indiancosmeticsbd.app.R;

import java.util.ArrayList;

public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.ProductReviewVewHolder>
{
    private final ArrayList<ProductReviewAdapterModel> productReviewList;
    private final Context context;

    public ProductReviewAdapter(ArrayList<ProductReviewAdapterModel> productReviewList, Context context) {
        this.productReviewList = productReviewList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductReviewVewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_review, parent, false);
        return new ProductReviewVewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductReviewVewHolder holder, int position) {
        ProductReviewAdapterModel selectedItem = productReviewList.get(position);
        holder.textViewUserName.setText(selectedItem.getUsername());
        holder.textViewDate.setText(selectedItem.getDate());
        holder.textViewDescription.setText(selectedItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return productReviewList.size();
    }

    public class ProductReviewVewHolder extends RecyclerView.ViewHolder {
        TextView textViewUserName, textViewDate, textViewDescription;
        public ProductReviewVewHolder(@NonNull View itemView) {
            super(itemView);
            textViewUserName = itemView.findViewById(R.id.recyclerview_review_username);
            textViewDate = itemView.findViewById(R.id.recyclerview_review_date);
            textViewDescription = itemView.findViewById(R.id.recyclerview_review_description);
        }
    }
}
