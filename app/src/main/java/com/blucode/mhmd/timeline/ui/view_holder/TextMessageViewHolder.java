package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blucode.mhmd.timeline.R;

public class TextMessageViewHolder extends RecyclerView.ViewHolder {

    private TextView time, bodyMessage;
    private ImageView avatar;
    public TextMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        time = itemView.findViewById(R.id.txt_card_messages_text_time);
        bodyMessage = itemView.findViewById(R.id.txt_card_messages_text_body);
        avatar = itemView.findViewById(R.id.img_card_text_message_bullet);
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public TextView getBodyMessage() {
        return bodyMessage;
    }

    public void setBodyMessage(TextView bodyMessage) {
        this.bodyMessage = bodyMessage;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }
}
