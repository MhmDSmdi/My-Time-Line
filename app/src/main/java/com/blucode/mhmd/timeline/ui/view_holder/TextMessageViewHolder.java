package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blucode.mhmd.timeline.R;
import com.github.vipulasri.timelineview.TimelineView;

public class TextMessageViewHolder extends RecyclerView.ViewHolder {

    private TextView date, bodyMessage;
    private ImageView avatar;
    private RatingBar ratingBar;
    public TimelineView mTimelineView;
    private TimeLineType timeLineType;
    private RelativeLayout layoutDetail;

    public TextMessageViewHolder(@NonNull View itemView, int viewType) {
        super(itemView);
        bodyMessage = itemView.findViewById(R.id.txt_card_messages_text_body);
        mTimelineView = itemView.findViewById(R.id.text_timeline);
        ratingBar = itemView.findViewById(R.id.rating_text_message);
        date = itemView.findViewById(R.id.txt_card_messages_text_date);
        layoutDetail = itemView.findViewById(R.id.layout_details_text_message);
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

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
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
