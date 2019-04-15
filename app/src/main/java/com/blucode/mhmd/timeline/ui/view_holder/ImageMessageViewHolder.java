package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blucode.mhmd.timeline.R;

public class ImageMessageViewHolder extends RecyclerView.ViewHolder {

    private ImageView img;

    public ImageMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        img = itemView.findViewById(R.id.img_image_message_card_drawable);
    }

    public ImageView getImg() {
        return img;
    }

    public void setImg(ImageView img) {
        this.img = img;
    }
}
