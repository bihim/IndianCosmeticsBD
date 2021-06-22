package com.indiancosmeticsbd.app.GlobalValue;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import com.google.android.material.button.MaterialButton;
import com.indiancosmeticsbd.app.R;

public class ExitDialog {

    final private Activity activity;
    final private Dialog dialog;

    public ExitDialog(Activity activity) {
        this.activity = activity;
        dialog = new Dialog(activity);
    }

    public void showDialog() {
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_exit);
        MaterialButton buttonYes = dialog.findViewById(R.id.yes_dialog);
        MaterialButton buttonNo = dialog.findViewById(R.id.no_dialog);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finishAffinity();
            }
        });
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
