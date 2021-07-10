package com.indiancosmeticsbd.app.Views.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.Orders.OrderStatusActivity;

import es.dmoral.toasty.Toasty;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class OrderInfoActivity extends AppCompatActivity {

    private MaterialButton materialButtonSubmit;
    private TextInputEditText textInputEditTextOrderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        findViewById();
        materialButtonSubmit.setOnClickListener(v -> {
            if (textInputEditTextOrderNo.getText().toString().equals("")){
                Toasty.error(this, "Enter Order No", Toasty.LENGTH_SHORT).show();
            }
            else{
                Intent intent = new Intent(this, OrderStatusActivity.class);
                intent.putExtra("id", textInputEditTextOrderNo.getText().toString());
                startActivity(intent);
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
        backButton.setOnClickListener(v -> onBackPressed());
    }
    private void findViewById() {
        materialButtonSubmit = findViewById(R.id.order_info_submit);
        textInputEditTextOrderNo = findViewById(R.id.order_without_account_order_no);
    }
}