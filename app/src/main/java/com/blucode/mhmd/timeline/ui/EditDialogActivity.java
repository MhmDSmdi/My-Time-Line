package com.blucode.mhmd.timeline.ui;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.util.AppConst;
import com.bumptech.glide.Glide;

import java.io.File;

public class EditDialogActivity extends Activity {
    private EditText editMessage;
    private TextView txtDate, txtRate;
    private RatingBar ratingBar;
    private Button btnSave;
    private ImageView image;
    private CardView imagePlaceholder;
    private String message;
    private float rate;
    private int position, messageType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_message);
        editMessage = findViewById(R.id.txt_edit_dialog_message_body);
        txtDate = findViewById(R.id.txt_edit_dialog_date);
        txtRate = findViewById(R.id.txt_edit_dialog_rate);
        ratingBar = findViewById(R.id.rating_edit_dialog_message);
        btnSave = findViewById(R.id.btn_edit_dialog_save);
        image = findViewById(R.id.img_edit_dialog_image);
        imagePlaceholder = findViewById(R.id.card_edit_dialog_image);
        Intent intent = getIntent();
        message = intent.getStringExtra(AppConst.EXTRA_MESSAGE_BODY);
        String date = intent.getStringExtra(AppConst.EXTRA_MESSAGE_DATE);
        rate = intent.getFloatExtra(AppConst.EXTRA_MESSAGE_RATING, 0);
        messageType = intent.getIntExtra(AppConst.EXTRA_MESSAGE_TYPE, 0);
        position = intent.getIntExtra(AppConst.EXTRA_MESSAGE_POSITION, 0);
        if (messageType == 0) {
            imagePlaceholder.setVisibility(View.VISIBLE);
            Uri uri = Uri.parse(intent.getStringExtra(AppConst.EXTRA_MESSAGE_IMAGE_URI));
            Glide.with(this).load(uri).into(image);
        } else if (messageType == 1 || messageType == 3) {
            editMessage.setVisibility(View.VISIBLE);
            editMessage.setText(message);
        }
        txtDate.setText(date);
        txtRate.setText("Rate: " + rate);
        ratingBar.setRating(rate);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtRate.setText("Rate: " + rating);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate = ratingBar.getRating();
                message = editMessage.getText().toString();
                finish();
            }
        });

    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra(AppConst.EXTRA_MESSAGE_BODY, messageType);
        data.putExtra(AppConst.EXTRA_MESSAGE_RATING, rate);
        data.putExtra(AppConst.EXTRA_MESSAGE_TYPE, messageType);
        data.putExtra(AppConst.EXTRA_MESSAGE_POSITION, position);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
