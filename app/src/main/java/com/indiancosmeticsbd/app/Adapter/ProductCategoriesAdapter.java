package com.indiancosmeticsbd.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.indiancosmeticsbd.app.Model.Category.CategorySelectedModel;
import com.indiancosmeticsbd.app.Model.ProductCategories.ProductCategoriesAdapterModel;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.ProductCategoryItemActivity;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductCategoriesAdapter extends RecyclerView.Adapter<ProductCategoriesAdapter.ProductCategoriesViewHolder> {

    private ArrayList<ProductCategoriesAdapterModel> categoriesArrayList;
    private ArrayList<CategorySelectedModel> categorySelectedModels;
    private Context context;

    public ProductCategoriesAdapter(ArrayList<ProductCategoriesAdapterModel> categoriesArrayList, Context context) {
        this.categoriesArrayList = categoriesArrayList;
        this.context = context;
    }

    public ProductCategoriesAdapter(ArrayList<ProductCategoriesAdapterModel> categoriesArrayList, ArrayList<CategorySelectedModel> categorySelectedModels, Context context) {
        this.categoriesArrayList = categoriesArrayList;
        this.categorySelectedModels = categorySelectedModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_product_categories, parent, false);
        return new ProductCategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductCategoriesViewHolder holder, int position) {
        ProductCategoriesAdapterModel selectedItem = categoriesArrayList.get(position);
        String title = titleText(selectedItem.getTitle());
        holder.textViewCategoryName.setText(title);
        //holder.circleImageViewCategoryPicture.setImageResource(selectedItem.getImageLink());
        Glide.with(context.getApplicationContext()).load(selectedItem.getImageLink()).into(holder.circleImageViewCategoryPicture);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> categories = new ArrayList<>();
                for (CategorySelectedModel model: categorySelectedModels){
                    if (model.getMain().equals(selectedItem.getTitle())){
                        categories.add(model.getHeader()+","+model.getId());
                    }
                }
                Intent intent = new Intent(context, ProductCategoryItemActivity.class);
                intent.putStringArrayListExtra("categories", categories);
                intent.putExtra("category_name", title);
                context.startActivity(intent);
            }
        });
    }

    private String titleText(String title){
        if (title.equals("keya-seth-aromatherapy")) {
            String[] titleSplet = title.split("-");
            StringBuilder bobTheBuilder = new StringBuilder();
            for (int i = 0; i < titleSplet.length; i++) {
                bobTheBuilder.append(Character.toUpperCase(titleSplet[i].charAt(0))).append(titleSplet[i].substring(1));
                if (titleSplet.length != i) {
                    bobTheBuilder.append(" ");
                } else {
                    break;
                }
            }
            return bobTheBuilder.toString();
        } else {
            return Character.toUpperCase(title.charAt(0)) + title.substring(1);
            //return title;
        }
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
