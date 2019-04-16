package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.adapter.AlbumAdapter;

public class AlbumMessageViewHolder extends RecyclerView.ViewHolder {
    private RecyclerView imagesRecyclerview;
    private ImageView avatar;

    public AlbumMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        imagesRecyclerview = itemView.findViewById(R.id.recycler_album_card);
        avatar = itemView.findViewById(R.id.img_card_album_message_bullet);
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
