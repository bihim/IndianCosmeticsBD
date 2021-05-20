package com.indiancosmeticsbd.app.Views.Activity.ProductDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.indiancosmeticsbd.app.R;

public class PhotoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        String getImage = getIntent().getStringExtra("image_url");
        PhotoView photoView =  findViewById(R.id.photoview);
        Glide.with(this).load(getImage).into(photoView);
        //photoView.setImageResource(R.drawable.image);
    }
}