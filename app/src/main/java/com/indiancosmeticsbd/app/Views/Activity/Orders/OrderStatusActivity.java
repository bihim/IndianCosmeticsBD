package com.indiancosmeticsbd.app.Views.Activity.Orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.indiancosmeticsbd.app.Model.Orders.OrderStatusModel;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.OrderStatusViewModel;
import com.indiancosmeticsbd.app.ViewModel.UserInfoViewModel;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class OrderStatusActivity extends AppCompatActivity {

    private TextView textViewName, textViewPhone, textViewDeliveryAddress, textViewDeliveryDate, textViewTotalAmount, textViewDeliveryCost;
    private NestedScrollView nestedScrollView;
    private LinearLayout linearLayoutLoading;

    private ImageView imageViewPaymentInfo;
    private TextView textViewPaymentType;
    private TextView textViewTrnxId;
    private TextView textViewTrnxIdText;
    private LinearLayout linearLayoutVerification;
    private TextView textViewVerification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        String id = getIntent().getStringExtra("id");
        setOrderStatus(id);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private void setOrderStatus(String orderId){
        nestedScrollView.setVisibility(View.GONE);
        linearLayoutLoading.setVisibility(View.VISIBLE);
        OrderStatusViewModel orderStatusViewModel = new ViewModelProvider(this).get(OrderStatusViewModel.class);
        orderStatusViewModel.init(orderId);
        orderStatusViewModel.getOrderStatus().observe(this, orderStatusModel -> {
            OrderStatusModel.Content content = orderStatusModel.getContent();
            if (content!=null){
                nestedScrollView.setVisibility(View.VISIBLE);
                linearLayoutLoading.setVisibility(View.GONE);
                textViewName.setText(content.getShippingInfo().getShippingName());
                textViewDeliveryAddress.setText(content.getShippingInfo().getShippingAddress());
                textViewPhone.setText(content.getShippingInfo().getShippingPhone());
                textViewDeliveryCost.setText("৳"+content.getOrderSummery().getDeliveryCost());
                double actualPrice = content.getOrderSummery().getDiscountTotal();
                double discount = content.getOrderSummery().getTotalCostWithoutDiscount()*(actualPrice/100);
                double discountPrice = content.getOrderSummery().getTotalCostWithoutDiscount()-discount;
                textViewTotalAmount.setText("৳"+discountPrice);
                try {
                    DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    DateFormat outputFormat = new SimpleDateFormat("dd MMM, yyyy (hh:mm aa)", Locale.ENGLISH);
                    Date date = inputFormat.parse(content.getOrderDate());
                    String outputDateStr = outputFormat.format(date);
                    textViewDeliveryDate.setText(outputDateStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //Payment Status
                //product details
                //order status

            }
            else{
                String id = getIntent().getStringExtra("id");
                setOrderStatus(id);
                Toasty.error(this, "Something is wrong, retrying...", Toasty.LENGTH_SHORT, true).show();
            }
        });
    }


    private void setTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }
    private void setToolbar(int toolbarId, int backButtonId) {
        MaterialToolbar toolbar = findViewById(toolbarId);
        setSupportActionBar(toolbar);
        ImageButton backButton = findViewById(backButtonId);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void findViewById() {
        textViewName = findViewById(R.id.order_status_name);
        textViewPhone = findViewById(R.id.order_status_phone_number);
        textViewDeliveryAddress = findViewById(R.id.order_status_address);
        textViewDeliveryDate = findViewById(R.id.order_status_delivery_date);
        textViewTotalAmount = findViewById(R.id.order_status_total_amount);
        textViewDeliveryCost = findViewById(R.id.order_status_delivery_cost);

        nestedScrollView = findViewById(R.id.order_status_scrollview);
        linearLayoutLoading = findViewById(R.id.order_status_loading);

        imageViewPaymentInfo = findViewById(R.id.order_status_payment_logo);
        textViewPaymentType = findViewById(R.id.order_status_payment_type);
        textViewTrnxId = findViewById(R.id.order_status_trnx_id);
        textViewTrnxIdText = findViewById(R.id.order_status_trnx_id_text);
        linearLayoutVerification = findViewById(R.id.verification_background);
        textViewVerification = findViewById(R.id.verification_text);
    }
}