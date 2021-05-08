package com.indiancosmeticsbd.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiancosmeticsbd.app.Model.Category.CategoryAdapterModel;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.ProductListActivity;

import java.util.ArrayList;

public class CategoriesSelectedAdapter extends RecyclerView.Adapter<CategoriesSelectedAdapter.CategoriesSelectedViewHolder>
{
    private ArrayList<CategoryAdapterModel> categoryAdapterModels;
    private Context context;

    public CategoriesSelectedAdapter(ArrayList<CategoryAdapterModel> categoryAdapterModels, Context context) {
        this.categoryAdapterModels = categoryAdapterModels;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoriesSelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_categories_click, parent, false);
        return new CategoriesSelectedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesSelectedViewHolder holder, int position) {
        CategoryAdapterModel selectedItem = categoryAdapterModels.get(position);
        String categoryName = Character.toUpperCase(selectedItem.getName().charAt(0)) + selectedItem.getName().substring(1);
        int iconId = selectedItem.getIcon();
        holder.textViewCategory.setText(categoryName);
        holder.imageViewCategory.setImageResource(iconId);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("id", selectedItem.getId());
                intent.putExtra("name", categoryName);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryAdapterModels.size();
    }

    public class CategoriesSelectedViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCategory;
        ImageView imageViewCategory;
        public CategoriesSelectedViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.recyclerview_category_click_textview);
            imageViewCategory = itemView.findViewById(R.id.recyclerview_category_click_imageview);
        }
    }
}
