package com.indiancosmeticsbd.app.Views.Activity.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.indiancosmeticsbd.app.GlobalValue.LoadingDialog;
import com.indiancosmeticsbd.app.Model.SignIn.UserInfo;
import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.ViewModel.UserInfoViewModel;
import com.indiancosmeticsbd.app.Views.Activity.MainActivity;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_address;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_city;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_country;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_district;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_email;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_first_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_id;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_last_name;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_mobile_number;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_postalcode;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_token;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.user_username;


public class SignInActivity extends AppCompatActivity {

    private ImageButton closeButton;
    private MaterialButton gotoRegisterButton;
    private MaterialButton signInButton;
    private TextInputEditText emailEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findViewById();
        buttonClicks();

    }

    private void setSignInButton(){
        String emailAddress = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        UserInfoViewModel userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        userInfoViewModel.init();
        userInfoViewModel.getUserInfo(emailAddress, password, this).observe(this, userInfo -> {
            UserInfo.Content value = userInfo.getContent();
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(user_id, value.getId());
            editor.putString(user_username, value.getUsername());
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
            editor.apply();
        });
    }

    private void buttonClicks(){
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                overridePendingTransition(0, 0);
                finish();
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
                setSignInButton();
                Log.d("LOGININFO", "onClick: Clicked Hereee");
            }
        });
    }

    private void findViewById(){
        closeButton = findViewById(R.id.sign_in_close);
        gotoRegisterButton = findViewById(R.id.gotoRegister);
        signInButton = findViewById(R.id.sign_in_button);
        emailEditText = findViewById(R.id.sign_in_email);
        passwordEditText = findViewById(R.id.sign_in_password);
    }
}