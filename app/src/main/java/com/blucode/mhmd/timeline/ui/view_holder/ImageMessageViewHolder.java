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

public class ImageMessageViewHolder extends RecyclerView.ViewHolder {

    private ImageView img, avatar;
    public TimelineView mTimelineView;
    private TimeLineType timeLineType;
    private TextView date;
    private RatingBar ratingBar;
    private RelativeLayout layoutDetail;

    public ImageMessageViewHolder(@NonNull View itemView, int viewType) {
        super(itemView);
        img = itemView.findViewById(R.id.img_image_message_card_drawable);
        mTimelineView = itemView.findViewById(R.id.image_timeline);
        ratingBar = itemView.findViewById(R.id.rating_image_message);
        date = itemView.findViewById(R.id.txt_card_messages_image_date);
        layoutDetail = itemView.findViewById(R.id.layout_details_image_message);
    }

    public void setTimeLineType(TimeLineType timeLineType) {
        this.timeLineType = timeLineType;
        mTimelineView.initLine(timeLineType.type);
    }

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
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

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }

    public ImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(ImageView avatar) {
        this.avatar = avatar;
    }
}
