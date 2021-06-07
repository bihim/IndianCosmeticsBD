package com.indiancosmeticsbd.app.Adapter.CategoryWise;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.indiancosmeticsbd.app.Model.CategoryWiseViewModel.CategoryWiseViewModel2;
import com.indiancosmeticsbd.app.Model.ProductList.ProductListModel;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.ProductDetails.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class CategoriesByViewAdapter extends RecyclerView.Adapter<CategoriesByViewAdapter.CategoriesByViewHolder> {

    private final ArrayList<CategoryWiseViewModel2> categoryWiseViewModelArrayList;
    private final Context context;
    private OnItemClickListener onItemClickListener;
    private final RecyclerView.RecycledViewPool recycledViewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<Integer> colorList = new ArrayList<>();
    private Random random = new Random();

    public CategoriesByViewAdapter(ArrayList<CategoryWiseViewModel2> categoryWiseViewModelArrayList, Context context) {
        this.categoryWiseViewModelArrayList = categoryWiseViewModelArrayList;
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
        CategoryWiseViewModel2 categoryWiseViewModel = categoryWiseViewModelArrayList.get(position);
        holder.textView.setText(categoryWiseViewModel.getName());

        colorList.add(R.color.color_1);
        colorList.add(R.color.color_2);
        colorList.add(R.color.color_3);
        colorList.add(R.color.color_4);
        colorList.add(R.color.color_5);
        colorList.add(R.color.color_6);

        if (position < colorList.size()){
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(colorList.get(position)));
        }
        else{
            holder.linearLayout.setBackgroundColor(context.getResources().getColor(colorList.get(random.nextInt(colorList.size()))));
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManager.setInitialPrefetchItemCount(categoryWiseViewModel.getProductListModels().size());


        ProductListAdapterForCategoryWiseAdapter productListAdapterForCategoryWiseAdapter = new ProductListAdapterForCategoryWiseAdapter(categoryWiseViewModel.getProductListModels(), context);
        productListAdapterForCategoryWiseAdapter.setOnItemClickListener(new ProductListAdapterForCategoryWiseAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                ProductListModel selectedItem = categoryWiseViewModel.getProductListModels().get(position);
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("id", selectedItem.getId());
                Log.d("PRODUCT_LIST", "onClick: id; " + selectedItem.getId());
                intent.putExtra("name", selectedItem.getName());
                context.startActivity(intent);
            }
        });

        holder.recyclerView.setLayoutManager(linearLayoutManager);
        holder.recyclerView.setAdapter(productListAdapterForCategoryWiseAdapter);
        holder.recyclerView.setRecycledViewPool(recycledViewPool);

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
        LinearLayout linearLayout;

        public CategoriesByViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerview_category_by_view_item_name);
            materialButton = itemView.findViewById(R.id.recyclerview_category_by_view_item_button);
            recyclerView = itemView.findViewById(R.id.recyclerview_category_by_view_item_recyclerview);
            linearLayout = itemView.findViewById(R.id.recyclerview_category_by_view_item_color);

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
