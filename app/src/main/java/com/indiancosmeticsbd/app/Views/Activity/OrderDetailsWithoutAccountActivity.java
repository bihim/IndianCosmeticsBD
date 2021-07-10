package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiancosmeticsbd.app.Adapter.OrderSubmitProductAdapter;
import com.indiancosmeticsbd.app.Model.ProductDetails.Cart;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.Orders.OrderSubmitActivity;
import com.indiancosmeticsbd.app.Views.Dialogs.BkashOrderSubmitDialog;
import com.indiancosmeticsbd.app.Views.Dialogs.CODOrderSubmitDialog;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.lang.reflect.Type;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART_DIRECT_ORDER;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class OrderDetailsWithoutAccountActivity extends AppCompatActivity {
    private TextView textViewTotal;
    private TextView textViewDelivery;
    private RecyclerView recyclerView;
    private MaterialButton buttonSubmitCOD;
    private MaterialButton buttonSubmitBkash;
    private OrderSubmitProductAdapter orderSubmitProductAdapter;
    private ArrayList<Cart> cartArrayList;
    private TextInputEditText textInputEditTextPhone, textInputEditTextAddress;
    private RadioGroup radioGroup;
    private String deliveryLocation = "Inside Dhaka City";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_without_account);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        setRecyclerview();

        int selectedRadioButton = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButton == R.id.order_submit_without_account_delivery_address_inside){
            deliveryLocation = "Inside Dhaka City";
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.order_submit_without_account_delivery_address_inside){
                    deliveryLocation = "Inside Dhaka City";
                    textViewDelivery.setText("৳70");
                }
                else{
                    deliveryLocation = "Outside Dhaka City";
                    textViewDelivery.setText("৳150");
                }
            }
        });

        buttonSubmitCOD.setOnClickListener(v -> {
            boolean isDirectOrder = getIntent().getBooleanExtra("directOrder", false);
            if(textInputEditTextPhone.getText() == null || TextUtils.isEmpty(textInputEditTextPhone.getText()) || textInputEditTextPhone.getText().toString().equals("")){
                Toasty.error(OrderDetailsWithoutAccountActivity.this, "Enter Phone Number", Toasty.LENGTH_SHORT, true).show();
            }
            else if(textInputEditTextAddress.getText() == null || TextUtils.isEmpty(textInputEditTextAddress.getText()) || textInputEditTextAddress.getText().toString().equals("")){
                Toasty.error(OrderDetailsWithoutAccountActivity.this, "Enter Address", Toasty.LENGTH_SHORT, true).show();
            }
            else{
                String phone = textInputEditTextPhone.getText().toString();
                String address = textInputEditTextAddress.getText().toString();
                CODOrderSubmitDialog codOrderSubmitDialog = new CODOrderSubmitDialog(OrderDetailsWithoutAccountActivity.this);
                codOrderSubmitDialog.showDialog(isDirectOrder, false, phone, deliveryLocation, address);
            }

        });

        buttonSubmitBkash.setOnClickListener(v -> {
            boolean isDirectOrder = getIntent().getBooleanExtra("directOrder", false);
            if(textInputEditTextPhone.getText() == null || TextUtils.isEmpty(textInputEditTextPhone.getText()) || textInputEditTextPhone.getText().toString().equals("")){
                Toasty.error(OrderDetailsWithoutAccountActivity.this, "Enter Phone Number", Toasty.LENGTH_SHORT, true).show();
            }
            else if(textInputEditTextAddress.getText() == null || TextUtils.isEmpty(textInputEditTextAddress.getText()) || textInputEditTextAddress.getText().toString().equals("")){
                Toasty.error(OrderDetailsWithoutAccountActivity.this, "Enter Address", Toasty.LENGTH_SHORT, true).show();
            }
            else{
                String phone = textInputEditTextPhone.getText().toString();
                String address = textInputEditTextAddress.getText().toString();
                BkashOrderSubmitDialog bkashOrderSubmitDialog = new BkashOrderSubmitDialog(OrderDetailsWithoutAccountActivity.this);
                bkashOrderSubmitDialog.showDialog(totalPrice(), isDirectOrder, false, phone, deliveryLocation, address);
            }

        });
    }

    private void setRecyclerview(){
        cartArrayList = viewCart();
        orderSubmitProductAdapter = new OrderSubmitProductAdapter(cartArrayList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(orderSubmitProductAdapter);
        textViewTotal.setText("৳"+totalPrice());
        textViewDelivery.setText("৳70");
    }

    private int totalPrice(){
        int totalCount = 0;
        for (Cart cart: cartArrayList){
            int price = Integer.parseInt(cart.getPrice());
            int quantity = Integer.parseInt(cart.getQuantity());
            totalCount = totalCount+(price*quantity);
        }
        return totalCount;
    }

    private ArrayList<Cart> viewCart() {

        Logger.addLogAdapter(new AndroidLogAdapter());
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = getSharedPreferences(CART, MODE_PRIVATE);
        boolean isDirectOrder = getIntent().getBooleanExtra("directOrder", false);
        String json = "";
        if (isDirectOrder){
            json = sharedPreferences.getString(CART_DIRECT_ORDER, "");
        }
        else{
            json = sharedPreferences.getString(CART, "");
        }
        //String json = sharedPreferences.getString(CART, "");
        Logger.d("String: "+json);
        Type type = new TypeToken<ArrayList<Cart>>() {}.getType();
        ArrayList<Cart> carts = gson.fromJson(json, type);
        Logger.d("ArrayList: "+carts);
        if (json.equals(""))
        {
            return new ArrayList<>();
        }
        else{
            return carts;
        }
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
        backButton.setOnClickListener(v -> onBackPressed());
    }

    private void findViewById() {
        recyclerView = findViewById(R.id.order_submit_without_account_list_product_recyclerview);
        textViewDelivery = findViewById(R.id.order_submit_without_account_delivery_cost);
        textViewTotal = findViewById(R.id.order_submit_without_account_total_amount);
        buttonSubmitCOD = findViewById(R.id.submit_without_account_order_cod);
        buttonSubmitBkash = findViewById(R.id.submit_order_without_account_bkash);
        textInputEditTextPhone = findViewById(R.id.order_without_account_phone);
        textInputEditTextAddress = findViewById(R.id.order_without_account_address);
        radioGroup = findViewById(R.id.order_submit_without_account_delivery_address_group);
    }
}