package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.adapter.AlbumAdapter;
import com.github.vipulasri.timelineview.TimelineView;

public class AlbumMessageViewHolder extends RecyclerView.ViewHolder {
    private RecyclerView imagesRecyclerview;
    private ImageView avatar;
    public TimelineView mTimelineView;
    private TimeLineType timeLineType;
    public AlbumMessageViewHolder(@NonNull View itemView, int viewType) {
        super(itemView);
        imagesRecyclerview = itemView.findViewById(R.id.recycler_album_card);
        mTimelineView = itemView.findViewById(R.id.album_timeline);
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

    public RecyclerView getImagesRecyclerview() {
        return imagesRecyclerview;
    }

    public void setImagesRecyclerview(RecyclerView imagesRecyclerview) {
        this.imagesRecyclerview = imagesRecyclerview;
    }
}
