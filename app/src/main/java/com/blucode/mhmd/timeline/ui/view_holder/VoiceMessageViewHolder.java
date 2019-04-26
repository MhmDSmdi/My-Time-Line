package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blucode.mhmd.timeline.R;
import com.github.vipulasri.timelineview.TimelineView;
import com.ohoussein.playpause.PlayPauseView;

public class VoiceMessageViewHolder extends RecyclerView.ViewHolder {
    private final TimelineView mTimelineView;
    private TextView time, body, duration;
    private PlayPauseView play;
    private RatingBar ratingBar;
    private RelativeLayout layoutDetail;
    private ImageView avatar;
    private TimeLineType timeLineType;

    public VoiceMessageViewHolder(@NonNull View itemView, int viewType) {
        super(itemView);
        body = itemView.findViewById(R.id.txt_card_messages_voice_body);
        duration = itemView.findViewById(R.id.txt_card_messages_voice_duration);
        play = itemView.findViewById(R.id.btn_card_messages_voice_play);
        mTimelineView = itemView.findViewById(R.id.voice_timeline);
        mTimelineView.initLine(viewType);
        ratingBar = itemView.findViewById(R.id.rating_voice_message);
        layoutDetail = itemView.findViewById(R.id.layout_details_voice_message);
    }

    public void setTimeLineType(TimeLineType timeLineType) {
        this.timeLineType = timeLineType;
        mTimelineView.initLine(timeLineType.type);
    }

    public RatingBar getRatingBar() {
        return ratingBar;
    }

    public void setRatingBar(RatingBar ratingBar) {
        this.ratingBar = ratingBar;
    }

    public RelativeLayout getLayoutDetail() {
        return layoutDetail;
    }

    public void setLayoutDetail(RelativeLayout layoutDetail) {
        this.layoutDetail = layoutDetail;
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

    public PlayPauseView getPlay() {
        return play;
    }

    public void setPlay(PlayPauseView play) {
        this.play = play;
    }

    public TextView getDuration() {
        return duration;
    }

    public void setDuration(TextView duration) {
        this.duration = duration;
    }
}
