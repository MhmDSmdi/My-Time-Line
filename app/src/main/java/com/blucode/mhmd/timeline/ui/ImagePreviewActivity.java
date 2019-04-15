package com.blucode.mhmd.timeline.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.util.AppConst;
import com.bumptech.glide.Glide;

public class ImagePreviewActivity extends AppCompatActivity {

    private ImageView image;
    private ImageView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        image = findViewById(R.id.img_image_preview);
        back= findViewById(R.id.btn_image_preview_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        String uriString = getIntent().getStringExtra(AppConst.EXTRA_IMAGE_URI);
        Uri imageUri = Uri.parse(uriString);
        Glide.with(this).load(imageUri).into(image);
    }
}
