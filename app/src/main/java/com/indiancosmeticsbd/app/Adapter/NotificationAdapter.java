package com.indiancosmeticsbd.app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiancosmeticsbd.app.Model.Notifications.NotificationModel;
import com.indiancosmeticsbd.app.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    private final ArrayList<NotificationModel> notificationModels;
    private final Context context;

    public NotificationAdapter(ArrayList<NotificationModel> notificationModels, Context context) {
        this.notificationModels = notificationModels;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        NotificationModel notificationModel = notificationModels.get(position);
        holder.textView.setText(notificationModel.getNotificationType());
        holder.textView2.setText(notificationModel.getNotificationText());
    }

    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView textView2;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyclerview_notification_type);
            textView2 = itemView.findViewById(R.id.recyclerview_notification_text);
        }
    }
}
