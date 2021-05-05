package com.indiancosmeticsbd.app.Views.Activity.Account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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
        /*signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadingDialog loadingDialog = new LoadingDialog(SignInActivity.this);
                loadingDialog.showDialog();
            }
        });*/

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSignInButton();
            }
        });
    }

    private void setSignInButton(){
        String emailAddress = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        UserInfoViewModel userInfoViewModel = new ViewModelProvider(this).get(UserInfoViewModel.class);
        userInfoViewModel.init(emailAddress, password, this);
        userInfoViewModel.getUserInfo().observe(this, userInfo -> {
            UserInfo.Content userInfoContent = userInfo.getContent();
            Log.d("LOGININFO", "setSignInButton: "+userInfoContent.getFirstName());
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
    }

    private void findViewById(){
        closeButton = findViewById(R.id.sign_in_close);
        gotoRegisterButton = findViewById(R.id.gotoRegister);
        signInButton = findViewById(R.id.sign_in_button);
        emailEditText = findViewById(R.id.sign_in_email);
        passwordEditText = findViewById(R.id.sign_in_password);
    }
}