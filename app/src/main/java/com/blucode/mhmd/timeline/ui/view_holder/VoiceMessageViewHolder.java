package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blucode.mhmd.timeline.R;

public class VoiceMessageViewHolder extends RecyclerView.ViewHolder {
    private TextView time, body, duration;
    private Button play;
    private ImageView avatar;

    public VoiceMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        avatar = itemView.findViewById(R.id.img_card_voice_message_bullet);
        time = itemView.findViewById(R.id.txt_card_messages_voice_time);
        body = itemView.findViewById(R.id.txt_card_messages_voice_body);
        duration = itemView.findViewById(R.id.txt_card_messages_voice_duration);
        play = itemView.findViewById(R.id.btn_card_messages_voice_play);
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    public TextView getBody() {
        return body;
    }

    public void setBody(TextView body) {
        this.body = body;
    }

    public Button getPlay() {
        return play;
    }

    public void setPlay(Button play) {
        this.play = play;
    }

    public TextView getDuration() {
        return duration;
    }

    public void setDuration(TextView duration) {
        this.duration = duration;
    }
}
