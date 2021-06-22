package com.indiancosmeticsbd.app.GlobalValue;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.indiancosmeticsbd.app.Model.ForgotPassword.ForgotPasswordModel;
import com.indiancosmeticsbd.app.Network.ForgotPassword.ForgotPasswordApi;
import com.indiancosmeticsbd.app.R;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.API_TOKEN;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.BASE_URL;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.FORGOT_PASSWORD;

public class ForgotPasswordDialog {
    final private Activity activity;
    final private Dialog dialog;

    public ForgotPasswordDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    public void showDialog() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_forgot_password);
        LinearLayout linearLayout = dialog.findViewById(R.id.forgot_email_layout);
        TextInputEditText textInputEditText = dialog.findViewById(R.id.forgot_email);
        MaterialButton button = dialog.findViewById(R.id.forgot_submit);

        LottieAnimationView lottieAnimationView = dialog.findViewById(R.id.forgot_loading);

        TextView textView = dialog.findViewById(R.id.sent_status_forgot_password);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        ForgotPasswordApi forgotPasswordApi = retrofit.create(ForgotPasswordApi.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.setVisibility(View.GONE);
                lottieAnimationView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);
                Call<ForgotPasswordModel> call = forgotPasswordApi.getForgotPassword(API_TOKEN, FORGOT_PASSWORD, Objects.requireNonNull(textInputEditText.getText()).toString());
                call.enqueue(new Callback<ForgotPasswordModel>() {
                    @Override
                    public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                        if (response.isSuccessful()) {
                            ForgotPasswordModel forgotPasswordModel = response.body();
                            if (forgotPasswordModel != null) {
                                if (forgotPasswordModel.getStatus().equals("SUCCESS")) {
                                    if (forgotPasswordModel.getContent().getSuccess()) {
                                        linearLayout.setVisibility(View.GONE);
                                        lottieAnimationView.setVisibility(View.GONE);
                                        textView.setVisibility(View.VISIBLE);
                                        textView.setText(activity.getResources().getString(R.string.confirmation_message));
                                    } else {
                                        linearLayout.setVisibility(View.GONE);
                                        lottieAnimationView.setVisibility(View.GONE);
                                        textView.setVisibility(View.VISIBLE);
                                        textView.setText(activity.getResources().getString(R.string.error_forgot));
                                    }
                                } else {
                                    linearLayout.setVisibility(View.GONE);
                                    lottieAnimationView.setVisibility(View.GONE);
                                    textView.setVisibility(View.VISIBLE);
                                    textView.setText(activity.getResources().getString(R.string.error_forgot));
                                }
                            } else {
                                linearLayout.setVisibility(View.GONE);
                                lottieAnimationView.setVisibility(View.GONE);
                                textView.setVisibility(View.VISIBLE);
                                textView.setText(activity.getResources().getString(R.string.error_forgot));
                            }

                        } else {
                            linearLayout.setVisibility(View.GONE);
                            lottieAnimationView.setVisibility(View.GONE);
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(activity.getResources().getString(R.string.error_forgot));
                        }
                    }

                    @Override
                    public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                        linearLayout.setVisibility(View.GONE);
                        lottieAnimationView.setVisibility(View.GONE);
                        textView.setVisibility(View.VISIBLE);
                        textView.setText(activity.getResources().getString(R.string.error_forgot));
                    }
                });
            }
        });
        dialog.show();
    }
}
