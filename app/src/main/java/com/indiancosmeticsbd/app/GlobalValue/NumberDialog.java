package com.indiancosmeticsbd.app.GlobalValue;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.indiancosmeticsbd.app.R;

import es.dmoral.toasty.Toasty;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_MOBILE_1;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_MOBILE_2;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_MOBILE_3;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class NumberDialog implements ActivityCompat.OnRequestPermissionsResultCallback{

    private Activity activity;
    private Dialog dialog;
    private String defaultValue = "No number available";
    private static final int REQUEST_CALL = 1;

    private MaterialButton button1;
    private MaterialButton button2;
    private MaterialButton button3;

    private String number1;
    private String number2;
    private String number3;

    public NumberDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    public void showDialog() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_call_number);
        button1 = dialog.findViewById(R.id.number_1);
        button2 = dialog.findViewById(R.id.number_2);
        button3 = dialog.findViewById(R.id.number_3);

        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        number1 = sharedPreferences.getString(COMPANY_MOBILE_1, defaultValue);
        number2 = sharedPreferences.getString(COMPANY_MOBILE_2, defaultValue);
        number3 = sharedPreferences.getString(COMPANY_MOBILE_3, defaultValue);


        button1.setText(number1);
        button2.setText(number2);
        button3.setText(number3);
        makePhoneCall();
        dialog.show();
    }

    private void callNumber(MaterialButton materialButton, String number){
        if (!number.equals(defaultValue)){
            materialButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+number));
                    activity.startActivity(callIntent);
                    makePhoneCall();
                }
            });
        }
        else{
            materialButton.setVisibility(View.GONE);
        }
    }

    private void makePhoneCall() {

        if (ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            callNumber(button1, number1);
            callNumber(button2, number2);
            callNumber(button3, number3);
            dismissDialog();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                //Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                Toasty.error(activity, "Permission Denied. Please accept permissions to call.", Toasty.LENGTH_SHORT, true).show();
            }
        }
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
