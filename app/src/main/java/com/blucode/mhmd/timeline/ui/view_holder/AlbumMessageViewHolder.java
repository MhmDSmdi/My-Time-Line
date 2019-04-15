package com.blucode.mhmd.timeline.ui.view_holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.adapter.AlbumAdapter;

public class AlbumMessageViewHolder extends RecyclerView.ViewHolder {
    private RecyclerView imagesRecyclerview;
    public AlbumMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        imagesRecyclerview = itemView.findViewById(R.id.recycler_album_card);
    }

    public RecyclerView getImagesRecyclerview() {
        return imagesRecyclerview;
    }

    public void setImagesRecyclerview(RecyclerView imagesRecyclerview) {
        this.imagesRecyclerview = imagesRecyclerview;
    }
}
