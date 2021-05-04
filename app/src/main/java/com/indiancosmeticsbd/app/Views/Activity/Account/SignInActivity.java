package com.indiancosmeticsbd.app.Views.Activity.Account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.indiancosmeticsbd.app.R;
import com.indiancosmeticsbd.app.Views.Activity.MainActivity;

public class SignInActivity extends AppCompatActivity {

    private ImageButton closeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findViewById();
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
            }
        });
    }

    private void findViewById(){
        closeButton = findViewById(R.id.sign_in_close);
    }
}