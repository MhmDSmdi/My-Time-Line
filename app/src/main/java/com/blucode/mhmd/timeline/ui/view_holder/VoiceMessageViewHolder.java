package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blucode.mhmd.timeline.R;
import com.github.vipulasri.timelineview.TimelineView;

public class VoiceMessageViewHolder extends RecyclerView.ViewHolder {
    private final TimelineView mTimelineView;
    private TextView time, body, duration;
    private Button play;
    private ImageView avatar;
    private TimeLineType timeLineType;

    public VoiceMessageViewHolder(@NonNull View itemView, int viewType) {
        super(itemView);
        time = itemView.findViewById(R.id.txt_card_messages_voice_time);
        body = itemView.findViewById(R.id.txt_card_messages_voice_body);
        duration = itemView.findViewById(R.id.txt_card_messages_voice_duration);
        play = itemView.findViewById(R.id.btn_card_messages_voice_play);
        mTimelineView = itemView.findViewById(R.id.voice_timeline);
        mTimelineView.initLine(viewType);
    }

    public void setTimeLineType(TimeLineType timeLineType) {
        this.timeLineType = timeLineType;
        mTimelineView.initLine(timeLineType.type);
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
