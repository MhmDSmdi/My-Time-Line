package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blucode.mhmd.timeline.R;
import com.github.vipulasri.timelineview.TimelineView;

public class ImageMessageViewHolder extends RecyclerView.ViewHolder {

    private ImageView img, avatar;
    public TimelineView mTimelineView;
    private TimeLineType timeLineType;

    public ImageMessageViewHolder(@NonNull View itemView, int viewType) {
        super(itemView);
        img = itemView.findViewById(R.id.img_image_message_card_drawable);
        mTimelineView = itemView.findViewById(R.id.image_timeline);
    }

    public void setTimeLineType(TimeLineType timeLineType) {
        this.timeLineType = timeLineType;
        mTimelineView.initLine(timeLineType.type);
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
