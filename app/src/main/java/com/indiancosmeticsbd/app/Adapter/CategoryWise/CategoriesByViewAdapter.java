package com.indiancosmeticsbd.app.Adapter.CategoryWise;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool;

import com.google.android.material.button.MaterialButton;
import com.indiancosmeticsbd.app.Model.CategoryWiseViewModel.CategoryWiseViewModel;
import com.indiancosmeticsbd.app.Model.ProductList.ProductListModel;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.ProductDetails.ProductDetailsActivity;

import java.util.ArrayList;

public class CategoriesByViewAdapter extends RecyclerView.Adapter<CategoriesByViewAdapter.CategoriesByViewHolder> {
    final private ArrayList<CategoryWiseViewModel> categoryWiseViewModelArrayList;
    final private ArrayList<ProductListModel> productsArrayList;
    private Context context;
    private OnItemClickListener onItemClickListener;
    //private RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();

    public CategoriesByViewAdapter(ArrayList<CategoryWiseViewModel> categoryWiseViewModelArrayList, ArrayList<ProductListModel> productsArrayList, Context context) {
        this.categoryWiseViewModelArrayList = categoryWiseViewModelArrayList;
        this.productsArrayList = productsArrayList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public CategoriesByViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_categorywise_view, parent, false);
        return new CategoriesByViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesByViewAdapter.CategoriesByViewHolder holder, int position) {
        CategoryWiseViewModel categoryWiseViewModel = categoryWiseViewModelArrayList.get(position);
        holder.textView.setText(categoryWiseViewModel.getName());


        ProductListAdapterForCategoryWiseAdapter productListAdapterForCategoryWiseAdapter = new ProductListAdapterForCategoryWiseAdapter(productsArrayList, context);
        productListAdapterForCategoryWiseAdapter.setOnItemClickListener(new ProductListAdapterForCategoryWiseAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                ProductListModel selectedItem = productsArrayList.get(position);
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id", selectedItem.getId());
                Log.d("PRODUCT_LIST", "onClick: id; " + selectedItem.getId());
                intent.putExtra("name", selectedItem.getName());
                context.startActivity(intent);
            }
        });
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerView.setAdapter(productListAdapterForCategoryWiseAdapter);
      //  holder.recyclerView.setRecycledViewPool(recycledViewPool);
    }

    @Override
    public int getItemCount() {
        return (categoryWiseViewModelArrayList != null ? categoryWiseViewModelArrayList.size() : 0);
    }

    public interface OnItemClickListener {
        void onViewAllClicked(int position);
    }

    public class CategoriesByViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        MaterialButton materialButton;
        RecyclerView recyclerView;

        public CategoriesByViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerview_category_by_view_item_name);
            materialButton = itemView.findViewById(R.id.recyclerview_category_by_view_item_button);
            recyclerView = itemView.findViewById(R.id.recyclerview_category_by_view_item_recyclerview);

            materialButton.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onViewAllClicked(position);
                    }
                }
            });
        }
    }
}
