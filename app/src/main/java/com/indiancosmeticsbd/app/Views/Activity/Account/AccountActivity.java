package com.indiancosmeticsbd.app.Views.Activity.Account;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.CartActivity;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.MainActivity;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.WishListActivity;
import com.indiancosmeticsbd.app.Views.Activity.NotificationActivity;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.CART;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.NOTIFICATION_SHOW;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHOWBADGE;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.WISHLIST;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_address;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_after_notification_size;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_city;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_district;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_email;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_first_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_last_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_mobile_number;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_postalcode;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_previous_notification_size;

public class AccountActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private TextView textViewName, textViewEmail, textViewMobileNumber, textViewAddress;
    private ImageButton imageButtonNotification;
    private LinearLayout imageButtonBadge;
    private MaterialButton buttonLogOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setToolbar(R.id.toolbar_top, R.id.back_button);
        setBottomNavigation(R.id.bottom_navigation);
        findViewById();
        settingAccountInfo();
        setLogOut();
        NOTIFICATION_SHOW(this, bottomNavigationView);
        SHOWBADGE(this, CART, R.id.bottom_nav_cart, bottomNavigationView);
        SHOWBADGE(this, WISHLIST, R.id.bottom_nav_wishlist, bottomNavigationView);
        showNotification();
    }

    private void setLogOut(){
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearSharedPref(SHARED_PREF_NAME);
                clearSharedPref(CART);
                clearSharedPref(WISHLIST);
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void clearSharedPref(String name){
        SharedPreferences sharedPreferences = getSharedPreferences(name, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void showNotification() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        int previousNotificationSize = sharedPreferences.getInt(user_previous_notification_size, 0);
        int afterNotificationSize = sharedPreferences.getInt(user_after_notification_size, 0);
        if (previousNotificationSize < afterNotificationSize) {
            imageButtonBadge.setVisibility(View.VISIBLE);
        } else {
            imageButtonBadge.setVisibility(View.GONE);
        }
        imageButtonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, NotificationActivity.class));
            }
        });
    }

    private void setTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }

    private void settingAccountInfo() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String name = sharedPreferences.getString(user_first_name, "No Name") + " " + sharedPreferences.getString(user_last_name, "");
        String email = sharedPreferences.getString(user_email, "No Email");
        String mobileNumber = sharedPreferences.getString(user_mobile_number, "No Mobile Number");
        String address = sharedPreferences.getString(user_address, "No Address") + "\nCity: " + sharedPreferences.getString(user_city, "") + "\nDistrict: " + sharedPreferences.getString(user_district, "") + (sharedPreferences.getString(user_postalcode, "").equals("") ? "" : "\nPostal Code: " + sharedPreferences.getString(user_postalcode, ""));
        textViewName.setText(name);
        textViewEmail.setText(email);
        textViewMobileNumber.setText(mobileNumber);
        textViewAddress.setText(address);
    }

    private void findViewById() {
        textViewName = findViewById(R.id.account_name);
        textViewEmail = findViewById(R.id.account_email);
        textViewAddress = findViewById(R.id.account_address);
        textViewMobileNumber = findViewById(R.id.account_mobile);
        imageButtonNotification = findViewById(R.id.notification_notification);
        imageButtonBadge = findViewById(R.id.notification_badge);
        buttonLogOut = findViewById(R.id.logout);
    }

    private void setBottomNavigation(int bottomNavigationId) {
        bottomNavigationView = findViewById(bottomNavigationId);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.bottom_nav_home) {
                    startActivity(new Intent(AccountActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    overridePendingTransition(0, 0);
                    //onBackPressed();
                } else if (item.getItemId() == R.id.bottom_nav_wishlist) {
                    startActivity(new Intent(AccountActivity.this, WishListActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
                    overridePendingTransition(0, 0);
                } else if (item.getItemId() == R.id.bottom_nav_cart) {
                    startActivity(new Intent(AccountActivity.this, CartActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    finish();
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
        NOTIFICATION_SHOW(this, bottomNavigationView);
        showNotification();
    }
}