package com.indiancosmeticsbd.app.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiancosmeticsbd.app.Model.Orders.ViewOrdersModel;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.Orders.OrderStatusActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ViewOrderAdapter extends RecyclerView.Adapter<ViewOrderAdapter.ViewOrderViewHolder> {

    private final ArrayList<ViewOrdersModel> viewOrdersModels;
    private final Context context;

    public ViewOrderAdapter(ArrayList<ViewOrdersModel> viewOrdersModels, Context context) {
        this.viewOrdersModels = viewOrdersModels;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_orders, parent, false);
        return new ViewOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewOrderViewHolder holder, int position) {
        ViewOrdersModel selectedItem = viewOrdersModels.get(position);
        holder.textView.setText("Order No: " + selectedItem.getOrderNumber());
        try {
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
            String inputDateStr = selectedItem.getOrderDate().split(" ")[0];
            Date date = inputFormat.parse(inputDateStr);
            String outputDateStr = outputFormat.format(date);
            holder.textView2.setText(outputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderStatusActivity.class);
                intent.putExtra("id", selectedItem.getOrderNumber());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return viewOrdersModels.size();
    }

    public class ViewOrderViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;

        public ViewOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerview_order_number);
            textView2 = itemView.findViewById(R.id.recyclerview_order_date);
        }
    }
}
