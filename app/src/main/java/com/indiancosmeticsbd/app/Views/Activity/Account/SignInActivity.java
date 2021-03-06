package com.indiancosmeticsbd.app.Views.Activity.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.indiancosmeticsbd.app.Views.Dialogs.ForgotPasswordDialog;
import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.UserInfoViewModel;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.MainActivity;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_address;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_after_notification_size;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_city;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_country;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_date;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_district;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_email;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_first_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_id;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_last_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_mobile_number;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_notification;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_orders;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_password;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_postalcode;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_previous_notification_size;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_token;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_username;


public class SignInActivity extends AppCompatActivity {

    private ImageButton closeButton;
    private MaterialButton gotoRegisterButton;
    private MaterialButton signInButton;
    private MaterialButton forgotButton;
    private TextInputEditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findViewById();
        buttonClicks();
    }

    private void setTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }

    private void setSignInButton() {
        Gson gson = new Gson();
        List<UserInfo.Notification> notifications = new ArrayList<>();
        List<UserInfo.CustomerOrder> customerOrders = new ArrayList<>();

        String emailAddress = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (emailAddress.equals("")){
            Toasty.error(this, "Enter email or phone number", Toasty.LENGTH_SHORT, true).show();
        }
        else if(password.equals("")){
            Toasty.error(this, "Enter password", Toasty.LENGTH_SHORT, true).show();
        }
        else{
            UserInfoViewModel userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
            userInfoViewModel.init();
            userInfoViewModel.getUserInfo(emailAddress, password, this, true, true).observe(this, userInfo -> {
                UserInfo.Content value = userInfo.getContent();
                StringBuilder listCustomerOrders = new StringBuilder();
                StringBuilder listCustomerDate = new StringBuilder();
                if (value.getCustomerOrders().size() != 0) {
                    notifications.addAll(value.getNotifications());
                    for (UserInfo.CustomerOrder customerOrder : value.getCustomerOrders()) {
                        listCustomerOrders.append(customerOrder.getOrderNo()).append(",");
                        listCustomerDate.append(customerOrder.getDate()).append(",");
                    }
                }

                int notificationSize = notifications.size();
                String notification_json = gson.toJson(notifications);

                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(user_id, value.getId());
                editor.putString(user_username, value.getUsername());
                editor.putString(user_password, password);
                editor.putString(user_token, value.getToken());
                editor.putString(user_first_name, value.getFirstName());
                editor.putString(user_last_name, value.getLastName());
                editor.putString(user_email, value.getEmail());
                editor.putString(user_address, value.getAddress());
                editor.putString(user_city, value.getCity());
                editor.putString(user_district, value.getDistrict());
                editor.putString(user_country, value.getCountry());
                editor.putString(user_postalcode, value.getPostalcode());
                editor.putString(user_mobile_number, value.getMobileNumber());

                /*Notification Part
                 * First time previous notification size will be 0 and new will assign to it.
                 * If it matches then no badge will show */

                editor.putInt(user_previous_notification_size, 0);
                editor.putInt(user_after_notification_size, notificationSize);
                editor.putString(user_notification, notification_json);
                editor.putString(user_orders, listCustomerOrders.toString());
                editor.putString(user_date, listCustomerDate.toString());
                editor.apply();
            });
        }
    }

    private void buttonClicks() {
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*startActivity(new Intent(SignInActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(0, 0);
                finish();*/
                onBackPressed();
            }
        });
        gotoRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, RegisterActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailAddress = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (emailAddress.equals("") || password.equals("")) {
                    Toasty.error(SignInActivity.this, "Enter Fields", Toasty.LENGTH_SHORT, true).show();
                } else {
                    setSignInButton();
                }

                Log.d("LOGININFO", "onClick: Clicked Hereee");
            }
        });

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgotPasswordDialog forgotPasswordDialog = new ForgotPasswordDialog(SignInActivity.this);
                forgotPasswordDialog.showDialog();
            }
        });
    }

    private void findViewById() {
        closeButton = findViewById(R.id.sign_in_close);
        gotoRegisterButton = findViewById(R.id.gotoRegister);
        signInButton = findViewById(R.id.sign_in_button);
        emailEditText = findViewById(R.id.sign_in_email);
        passwordEditText = findViewById(R.id.sign_in_password);
        forgotButton = findViewById(R.id.sign_in_forgot_pass);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}