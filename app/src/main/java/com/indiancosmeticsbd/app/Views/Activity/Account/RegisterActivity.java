package com.indiancosmeticsbd.app.Views.Activity.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.indiancosmeticsbd.app.Model.ForgotPassword.ForgotPasswordModel;
import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.Network.User.RegisterUserApi;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.UserInfoViewModel;
import com.indiancosmeticsbd.app.Views.Activity.BottomNavActivities.MainActivity;
import com.indiancosmeticsbd.app.Views.Dialogs.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.BASE_URL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.REGISTER_USER;
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

public class RegisterActivity extends AppCompatActivity {

    private MaterialButton gotoSignInButton;
    private ImageButton closeButton;
    private TextInputEditText firstName, lastName, email, address, city, district, postalCode, mobileNumber, password;
    private MaterialButton materialButtonSignIn;
    private Retrofit retrofit;
    private RegisterUserApi registerUserApi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViewById();
        setButtons();
    }

    private void setEditTexts(){
        LoadingDialog loadingDialog = new LoadingDialog(this);
        if (setValidations("First Name", firstName) && setValidations("Last Name", lastName) && setValidations("Email", email) && setValidations("Address", address) && setValidations("City", city) && setValidations("District", district) && setValidations("Postal Code", postalCode) && setValidations("Mobile Number", mobileNumber) && setValidations("Password", password)){
            loadingDialog.showDialog();
            Call<ForgotPasswordModel> call = registerUserApi.getRegisterUser(API_TOKEN, REGISTER_USER, email.getText().toString(), password.getText().toString(), firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), address.getText().toString(), district.getText().toString(), city.getText().toString(), postalCode.getText().toString(), mobileNumber.getText().toString());
            call.enqueue(new Callback<ForgotPasswordModel>() {
                @Override
                public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                    Log.d("REGISTERUSER", "onResponse: "+response.body().getContent().getSuccess());
                    Log.d("REGISTERUSER", "onResponse: "+response.body().getStatus());
                    loadingDialog.dismissDialog();
                    if (response.isSuccessful()){
                        ForgotPasswordModel forgotPasswordModel = response.body();
                        if (forgotPasswordModel!=null){
                            if (forgotPasswordModel.getContent().getSuccess()){
                                Log.d("REGISTERUSER", "onResponse: "+forgotPasswordModel.getContent().getSuccess());
                                setSignInButton();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                    loadingDialog.dismissDialog();
                }
            });
        }
    }

    private void setSignInButton() {
        Gson gson = new Gson();
        List<UserInfo.Notification> notifications = new ArrayList<>();
        List<UserInfo.CustomerOrder> customerOrders = new ArrayList<>();
        String emailAddress = email.getText().toString();
        String userPassword = password.getText().toString();
        UserInfoViewModel userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        userInfoViewModel.init();
        userInfoViewModel.getUserInfo(emailAddress, userPassword, this, true, false).observe(this, userInfo -> {
            UserInfo.Content value = userInfo.getContent();
            if (value!=null){
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
                editor.putString(user_password, userPassword);
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
            }
            else{
                setSignInButton();
            }
        });
    }

    private boolean setValidations(String name, TextInputEditText textInputEditText){
        if (textInputEditText.getText()!=null && textInputEditText.getText().toString().equals("")){
            Toasty.error(this, "Enter "+name, Toasty.LENGTH_SHORT, true).show();
            return false;
        }
        else{
            return true;
        }
    }

    private void setButtons(){
        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        registerUserApi = retrofit.create(RegisterUserApi.class);
        materialButtonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEditTexts();
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
        gotoSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, SignInActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
    }
    private void setTheme(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String theme = sharedPreferences.getString("theme", "light");
        setTheme(theme.equals("light") ? R.style.Theme_IndianCosmeticsBD_Light : R.style.Theme_IndianCosmeticsBD_Dark);
    }

    private void findViewById(){
        closeButton = findViewById(R.id.register_close);
        gotoSignInButton = findViewById(R.id.gotoSignIn);
        materialButtonSignIn = findViewById(R.id.sign_in);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        district = findViewById(R.id.district);
        postalCode = findViewById(R.id.postalCode);
        mobileNumber = findViewById(R.id.mobileNumber);
        password = findViewById(R.id.password_input);
    }
}