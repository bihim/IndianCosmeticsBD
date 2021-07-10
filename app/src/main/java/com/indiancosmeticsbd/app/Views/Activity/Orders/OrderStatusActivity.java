package com.indiancosmeticsbd.app.Views.Activity.Orders;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.indiancosmeticsbd.app.Adapter.OrderSubmitProductAdapter;
import com.indiancosmeticsbd.app.Model.Orders.OrderStatusModel;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.OrderStatusViewModel;
import com.indiancosmeticsbd.app.ViewModel.UserInfoViewModel;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class OrderStatusActivity extends AppCompatActivity {

    private TextView textViewName, textViewPhone, textViewDeliveryAddress, textViewDeliveryDate, textViewTotalAmount, textViewDeliveryCost;
    private NestedScrollView nestedScrollView;
    private LinearLayout linearLayoutLoading;
    private LinearLayout noOrderShow;

    private ImageView imageViewPaymentInfo;
    private TextView textViewPaymentType;
    private TextView textViewTrnxId;
    private TextView textViewTrnxIdText;
    private LinearLayout linearLayoutVerification;
    private TextView textViewVerification;
    private TextView textViewOrderId;
    private TextView textViewStatus;
    private TextView textViewDescription;
    private ExtendedFloatingActionButton extendedFloatingActionButton;

    private RecyclerView recyclerView;
    private ArrayList<Cart> carts;
    private OrderSubmitProductAdapter orderSubmitProductAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        String id = getIntent().getStringExtra("id");
        String regex = "\\d+";
        if (id.matches(regex)){
            setOrderStatus(id);
        }
        else{
            onBackPressed();
        }

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

                if (content.getOrderNo() == 0){
                    nestedScrollView.setVisibility(View.GONE);
                    linearLayoutLoading.setVisibility(View.GONE);
                    noOrderShow.setVisibility(View.VISIBLE);
                }
                else{
                    nestedScrollView.setVisibility(View.VISIBLE);
                    linearLayoutLoading.setVisibility(View.GONE);
                    noOrderShow.setVisibility(View.GONE);

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

                    //Payment info
                    String paymentType = content.getPaymentInfo().getPaymentType();
                    String paymentTransactionId = content.getPaymentInfo().getPaymentTrxnId();
                    String paymentStatus = content.getPaymentInfo().getPaymentStatus();

                    if(paymentType.equals("bkash")){
                        imageViewPaymentInfo.setImageResource(R.drawable.ic_bkash);
                        textViewPaymentType.setText("Bkash");
                        if (paymentTransactionId == null || paymentTransactionId.equals("null")){
                            textViewTrnxId.setText("No Transaction ID given");
                        }else{
                            textViewTrnxId.setText(paymentTransactionId);
                        }
                    }
                    else{
                        imageViewPaymentInfo.setImageResource(R.drawable.ic_cash_on_delivery);
                        textViewPaymentType.setText("Cash On Delivery");
                        textViewTrnxId.setVisibility(View.GONE);
                        textViewTrnxIdText.setVisibility(View.GONE);
                    }

                    if (paymentStatus.equals("Unverified")){
                        textViewVerification.setText("Unverified");
                        linearLayoutVerification.setBackgroundColor(getResources().getColor(R.color.forgotPassDark));
                    }
                    else{
                        textViewVerification.setText("Verified");
                        linearLayoutVerification.setBackgroundColor(Color.GREEN);
                    }

                    textViewOrderId.setText("Order no: "+content.getOrderNo());

                    //order status
                    textViewStatus.setText(content.getOrderStatus().getStatus());
                    textViewDescription.setText(content.getOrderStatus().getDescription());

                    extendedFloatingActionButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(content.getInvoiceUrl()));
                            startActivity(browserIntent);
                        }
                    });

                    List<OrderStatusModel.Product> productList = content.getProducts();
                    carts = new ArrayList<>();
                    for (OrderStatusModel.Product product: productList){
                        String id = String.valueOf(product.getProductId());
                        String productImage = product.getProductImage().replace("thumb", "1");
                        String price = String.valueOf(product.getProductPrice());
                        String quantity = String.valueOf(product.getProductQuantity());
                        String size = String.valueOf(product.getProductSize());
                        String name = product.getProductName();

                        carts.add(new Cart(id, "Ordered Product", name, price, quantity, "0", size, productImage, "0"));
                    }
                    orderSubmitProductAdapter = new OrderSubmitProductAdapter(carts, this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(this));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(orderSubmitProductAdapter);
                }

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
        textViewOrderId = findViewById(R.id.order_id);
        textViewStatus = findViewById(R.id.order_status_status);
        textViewDescription = findViewById(R.id.order_status_description);
        extendedFloatingActionButton = findViewById(R.id.print_invoice);
        recyclerView = findViewById(R.id.order_status_list_product_recyclerview);
        noOrderShow = findViewById(R.id.no_order);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}