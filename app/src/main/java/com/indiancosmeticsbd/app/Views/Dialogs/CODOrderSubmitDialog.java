package com.indiancosmeticsbd.app.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Model.Orders.TransactionModel;
import com.indiancosmeticsbd.app.Model.Orders.ProductOrderModels;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.Network.Order.TransactionApi;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.MainActivity;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.BASE_URL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART_DIRECT_ORDER;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COD;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.ORDER_SUBMIT;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_date;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_orders;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_token;

public class CODOrderSubmitDialog {
    final private Activity activity;
    final private Dialog dialog;

    public CODOrderSubmitDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    public void showDialog(boolean isDirectOrder) {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_order_submit_cod);
        LinearLayout linearLayout = dialog.findViewById(R.id.forgot_email_layout);
        MaterialButton button = dialog.findViewById(R.id.cod_submit);

        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.forgot_loading);

        TextView textView = dialog.findViewById(R.id.sent_status_forgot_password);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        TransactionApi transactionApi = retrofit.create(TransactionApi.class);

        button.setOnClickListener(v -> {
            dialog.setCancelable(false);
            linearLayout.setVisibility(View.GONE);
            lottieAnimationView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String userToken = sharedPreferences.getString(user_token, "Null");

            Call<TransactionModel> call = transactionApi.getCODInfo(API_TOKEN, ORDER_SUBMIT, userToken, getProducts(isDirectOrder), COD, "Null", "");
            call.enqueue(new Callback<TransactionModel>() {
                @Override
                public void onResponse(Call<TransactionModel> call, Response<TransactionModel> response) {
                    dialog.setCancelable(true);
                    if (response.isSuccessful()) {
                        TransactionModel transactionModel = response.body();
                        if (transactionModel != null) {
                            if (transactionModel.getStatus().equals("SUCCESS")) {
                                if (transactionModel.getContent().getSuccess()) {
                                    linearLayout.setVisibility(View.GONE);
                                    lottieAnimationView.setVisibility(View.GONE);
                                    textView.setVisibility(View.VISIBLE);
                                    textView.setText(activity.getResources().getString(R.string.order_confirmation));

                                    String orderNo = String.valueOf(transactionModel.getContent().getOrderno());
                                    String userOrders = sharedPreferences.getString(user_orders, "");
                                    String userDates = sharedPreferences.getString(user_date, "");


                                    Date cDate = new Date();
                                    String fDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cDate);
                                    userDates = userDates+","+fDate;

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    userOrders = userOrders+","+orderNo;
                                    editor.putString(user_orders, userOrders);
                                    editor.putString(user_date, userDates);
                                    editor.apply();


                                    SharedPreferences sharedPreferencesCart = activity.getSharedPreferences(CART, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editorCart = sharedPreferencesCart.edit();
                                    editorCart.clear();
                                    editorCart.apply();

                                    SharedPreferences sharedPreferencesCartDirectOrder = activity.getSharedPreferences(CART_DIRECT_ORDER, Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editorCartDirectOrder = sharedPreferencesCartDirectOrder.edit();
                                    editorCartDirectOrder.clear();
                                    editorCartDirectOrder.apply();

                                    dialog.setOnCancelListener(dialog -> {
                                        activity.startActivity(new Intent(activity, MainActivity.class));
                                        activity.finish();
                                    });

                                } else {
                                    linearLayout.setVisibility(View.GONE);
                                    lottieAnimationView.setVisibility(View.GONE);
                                    textView.setVisibility(View.VISIBLE);
                                    textView.setText(activity.getResources().getString(R.string.error_forgot));
                                }
                            } else {
                                linearLayout.setVisibility(View.GONE);
                                lottieAnimationView.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);
                                textView.setText(activity.getResources().getString(R.string.error_forgot));
                            }
                        } else {
                            linearLayout.setVisibility(View.GONE);
                            lottieAnimationView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(activity.getResources().getString(R.string.error_forgot));
                        }

                    } else {
                        linearLayout.setVisibility(View.GONE);
                        lottieAnimationView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(activity.getResources().getString(R.string.error_forgot));
                    }

                }

                @Override
                public void onFailure(Call<TransactionModel> call, Throwable t) {
                    linearLayout.setVisibility(View.GONE);
                    lottieAnimationView.setVisibility(View.GONE);
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(activity.getResources().getString(R.string.error_forgot));
                }
            });
            Logger.addLogAdapter(new AndroidLogAdapter());
            //Logger.d("In Dialog"+getProducts());
        });
        dialog.show();
    }

    private String getProducts(boolean isDirectOrder){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = activity.getSharedPreferences(CART, Context.MODE_PRIVATE);
        String json = "";
        if (isDirectOrder){
            json = sharedPreferences.getString(CART_DIRECT_ORDER, "");
        }
        else{
            json = sharedPreferences.getString(CART, "");
        }
        Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
        ArrayList<Cart> carts = gson.fromJson(json, type);
        ArrayList<ProductOrderModels> productOrderModels = new ArrayList<>();

        for (Cart cart: carts){
            productOrderModels.add(new ProductOrderModels(cart.getProductId(), cart.getSize(), "", cart.getQuantity()));
        }
        return gson.toJson(productOrderModels);
    }
}
