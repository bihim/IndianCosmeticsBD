package com.indiancosmeticsbd.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesModel;
import com.indiancosmeticsbd.app.Network.ProductCategories.ProductCategoriesModelAdapter;
import com.indiancosmeticsbd.app.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductCategoriesAdapter extends RecyclerView.Adapter<ProductCategoriesAdapter.ProductCategoriesViewHolder> {

    private ArrayList<ProductCategoriesModelAdapter> categoriesArrayList;
    private Context context;

    public ProductCategoriesAdapter(ArrayList<ProductCategoriesModelAdapter> categoriesArrayList, Context context) {
        this.categoriesArrayList = categoriesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_categories, parent, false);
        return new ProductCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCategoriesViewHolder holder, int position) {
        ProductCategoriesModelAdapter selectedItem = categoriesArrayList.get(position);
        holder.textViewCategoryName.setText(selectedItem.getTitle());
        holder.circleImageViewCategoryPicture.setImageResource(selectedItem.getImageLink());
    }

    @Override
    public int getItemCount() {
        return categoriesArrayList.size();
    }

    class ProductCategoriesViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategoryName;
        CircleImageView circleImageViewCategoryPicture;

        public ProductCategoriesViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategoryName = itemView.findViewById(R.id.recyclerview_categories_textView);
            circleImageViewCategoryPicture = itemView.findViewById(R.id.recyclerview_categories_imageView);
        }
    }
}
