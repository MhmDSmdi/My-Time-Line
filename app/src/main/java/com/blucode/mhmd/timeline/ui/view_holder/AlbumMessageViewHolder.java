package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.adapter.AlbumAdapter;
import com.github.vipulasri.timelineview.TimelineView;

public class AlbumMessageViewHolder extends RecyclerView.ViewHolder {
    private RecyclerView imagesRecyclerview;
    private ImageView avatar;
    private RatingBar ratingBar;
    private TextView date;
    public TimelineView mTimelineView;
    private TimeLineType timeLineType;
    private RelativeLayout layoutDetail;

    public AlbumMessageViewHolder(@NonNull View itemView, int viewType) {
        super(itemView);
        imagesRecyclerview = itemView.findViewById(R.id.recycler_album_card);
        mTimelineView = itemView.findViewById(R.id.album_timeline);
        ratingBar = itemView.findViewById(R.id.rating_album_message);
        date = itemView.findViewById(R.id.txt_card_messages_album_date);
        layoutDetail = itemView.findViewById(R.id.layout_details_album_message);
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

    public TextView getDate() {
        return date;
    }

    public void setDate(TextView date) {
        this.date = date;
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

    public RecyclerView getImagesRecyclerview() {
        return imagesRecyclerview;
    }

    public void setImagesRecyclerview(RecyclerView imagesRecyclerview) {
        this.imagesRecyclerview = imagesRecyclerview;
    }
}
