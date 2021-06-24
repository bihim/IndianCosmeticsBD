package com.indiancosmeticsbd.app.Views.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;
import com.indiancosmeticsbd.app.R;

import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_MOBILE_1;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_MOBILE_2;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.COMPANY_MOBILE_3;
import static com.indiancosmeticsbd.app.GlobalValue.GlobalValue.SHARED_PREF_NAME;

public class NumberDialog{

    private final Activity activity;
    private final Dialog dialog;
    private final String defaultValue = "No number available";
    //private static final int REQUEST_CALL = 1;

    public NumberDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    public void showDialog() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_call_number);
        TextView textView1 = dialog.findViewById(R.id.number_1);
        TextView textView2 = dialog.findViewById(R.id.number_2);
        TextView textView3 = dialog.findViewById(R.id.number_3);

        MaterialCardView materialCardView1 = dialog.findViewById(R.id.card_number_1);
        MaterialCardView materialCardView2 = dialog.findViewById(R.id.card_number_2);
        MaterialCardView materialCardView3 = dialog.findViewById(R.id.card_number_3);

        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        String number1 = sharedPreferences.getString(COMPANY_MOBILE_1, defaultValue);
        String number2 = sharedPreferences.getString(COMPANY_MOBILE_2, defaultValue);
        String number3 = sharedPreferences.getString(COMPANY_MOBILE_3, defaultValue);


        /*textView1.setText(number1);
        textView2.setText(number2);
        textView3.setText(number3);*/

        setTextNumber(materialCardView1, textView1, number1);
        setTextNumber(materialCardView2, textView2, number2);
        setTextNumber(materialCardView3, textView3, number3);

        dialog.show();
    }

    private void setTextNumber(MaterialCardView materialCardView, TextView textView, String number){
        textView.setText(number);
        if (number.equals(defaultValue)){
            materialCardView.setVisibility(View.GONE);
        }

    }

    /*private void callNumber(MaterialButton materialButton, String number){
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
    }*/

   /* private void makePhoneCall() {

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
    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall();
            } else {
                //Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                Toasty.error(activity, "Permission Denied. Please accept permissions to call.", Toasty.LENGTH_SHORT, true).show();
            }
        }
    }*/

//    public void dismissDialog(){
//        dialog.dismiss();
//    }
}
