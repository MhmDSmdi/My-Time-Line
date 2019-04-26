package com.blucode.mhmd.timeline.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.util.AppConst;

public class EditDialogActivity extends Activity {
    private EditText editMessage;
    private TextView txtDate, txtRate;
    private RatingBar ratingBar;
    private Button btnSave;
    private int position, messageType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_message);
        editMessage = findViewById(R.id.txt_dialog_message_body);
        txtDate = findViewById(R.id.txt_dialog_date);
        txtRate = findViewById(R.id.txt_dialog_rate);
        ratingBar = findViewById(R.id.rating_dialog_message);
        btnSave = findViewById(R.id.btn_dialog_save);

        Intent intent = getIntent();
        String message = intent.getStringExtra(AppConst.EXTRA_MESSAGE_BODY);
        String date = intent.getStringExtra(AppConst.EXTRA_MESSAGE_DATE);
        float rate = intent.getFloatExtra(AppConst.EXTRA_MESSAGE_RATING, 0);
        messageType = intent.getIntExtra(AppConst.EXTRA_MESSAGE_TYPE, 0);
        position = intent.getIntExtra(AppConst.EXTRA_MESSAGE_POSITION, 0);

        editMessage.setText(message);
        txtDate.setText(date);
        txtRate.setText("Rate: " + rate);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                txtRate.setText("Rate: " + rating);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });

    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra(AppConst.EXTRA_MESSAGE_BODY, editMessage.getText().toString());
        data.putExtra(AppConst.EXTRA_MESSAGE_RATING, ratingBar.getRating());
        data.putExtra(AppConst.EXTRA_MESSAGE_TYPE, messageType);
        data.putExtra(AppConst.EXTRA_MESSAGE_POSITION, position);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
