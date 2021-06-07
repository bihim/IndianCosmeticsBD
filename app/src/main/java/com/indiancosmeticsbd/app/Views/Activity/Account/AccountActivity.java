package com.indiancosmeticsbd.app.Views.Activity.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.CartActivity;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.MainActivity;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.WishListActivity;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_address;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_city;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_district;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_email;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_first_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_last_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_mobile_number;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_postalcode;

public class AccountActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView textViewName, textViewEmail, textViewMobileNumber, textViewAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        setBottomNavigation(R.id.bottom_navigation);
        findViewById();
        settingAccountInfo();
    }

    private void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }

    private void settingAccountInfo(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(user_first_name, "No Name")+" "+sharedPreferences.getString(user_last_name, "");
        String email = sharedPreferences.getString(user_email, "No Email");
        String mobileNumber = sharedPreferences.getString(user_mobile_number, "No Mobile Number");
        String address = sharedPreferences.getString(user_address, "No Address")+"\nCity: "+sharedPreferences.getString(user_city,"")+"\nDistrict: "+sharedPreferences.getString(user_district, "")+"\nPostal Code: "+sharedPreferences.getString(user_postalcode, "");
        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewMobileNumber.setText(mobileNumber);
        textViewAddress.setText(address);
    }

    private void findViewById(){
     textViewName = findViewById(R.id.account_name);
     textViewEmail = findViewById(R.id.account_email);
     textViewAddress = findViewById(R.id.account_address);
     textViewMobileNumber = findViewById(R.id.account_mobile);
    }

    private void setBottomNavigation(int bottomNavigationId) {
        bottomNavigationView = findViewById(bottomNavigationId);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_nav_home) {
                    /*startActivity(new Intent(AccountActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);*/
                    onBackPressed();
                } else if (item.getItemId() == R.id.bottom_nav_wishlist) {
                    startActivity(new Intent(AccountActivity.this, WishListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.bottom_nav_cart) {
                    startActivity(new Intent(AccountActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.bottom_nav_account) {
                   /* startActivity(new Intent(AccountActivity.this, SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    overridePendingTransition(0, 0);*/
                }
                return true;
            }
        });

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_account);
    }

}