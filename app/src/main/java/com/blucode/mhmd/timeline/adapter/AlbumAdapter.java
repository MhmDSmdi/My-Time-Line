package com.blucode.mhmd.timeline.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.data.model.UriAddress;
import com.blucode.mhmd.timeline.ui.ImagePreviewActivity;
import com.blucode.mhmd.timeline.util.AppConst;
import com.bumptech.glide.Glide;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ImageViewHolder> {
    private Context mContext;
    private List<UriAddress> imageAddresses;

    public AlbumAdapter(Context mContext, List<UriAddress> imageAddresses) {
        this.mContext = mContext;
        this.imageAddresses = imageAddresses;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        final Uri imagePath = Uri.parse(imageAddresses.get(position).getAddress());
//        Glide.with(mContext).load(R.drawable.placeholder).into(holder.drawable);
        Glide.with(mContext).load(imagePath).into(holder.drawable);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                intent.putExtra(AppConst.EXTRA_IMAGE_URI, imagePath.toString());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageAddresses.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView drawable;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            drawable = itemView.findViewById(R.id.img_image_drawable);
        }
    }
}
