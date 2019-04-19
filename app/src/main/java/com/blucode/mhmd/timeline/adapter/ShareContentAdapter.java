package com.blucode.mhmd.timeline.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.data.model.AlbumMessage;
import com.blucode.mhmd.timeline.data.model.ImageMessage;
import com.blucode.mhmd.timeline.data.model.TextMessage;
import com.blucode.mhmd.timeline.data.model.VoiceMessage;
import com.blucode.mhmd.timeline.ui.AlertRemoveDialog;
import com.blucode.mhmd.timeline.ui.ImagePreviewActivity;
import com.blucode.mhmd.timeline.ui.view_holder.AlbumMessageViewHolder;
import com.blucode.mhmd.timeline.ui.view_holder.ImageMessageViewHolder;
import com.blucode.mhmd.timeline.ui.view_holder.TextMessageViewHolder;
import com.blucode.mhmd.timeline.ui.view_holder.TimeLineType;
import com.blucode.mhmd.timeline.ui.view_holder.VoiceMessageViewHolder;
import com.blucode.mhmd.timeline.util.AppConst;
import com.bumptech.glide.Glide;
import com.github.vipulasri.timelineview.TimelineView;

import java.text.SimpleDateFormat;
import java.util.List;

public class ShareContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Object> itemList;
    private Context mContext;
    private final int TEXT_MESSAGE = 0, VOICE_MESSAGE = 1, DATE = 2, IMAGE = 3, IMAGE_ALBUM = 4;
    private long mVibratePattern[] = new long[]{0, 30};
    private Vibrator vibrator;

    public ShareContentAdapter(Context mContext, List<Object> itemList) {
        this.itemList = itemList;
        this.mContext = mContext;
        vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case TEXT_MESSAGE:
                View textMessageView = inflater.inflate(R.layout.card_text_message, parent, false);
                viewHolder = new TextMessageViewHolder(textMessageView, viewType);
                break;
            case VOICE_MESSAGE:
                View voiceMessageView = inflater.inflate(R.layout.card_voice_message, parent, false);
                viewHolder = new VoiceMessageViewHolder(voiceMessageView, viewType);
                break;
            case IMAGE:
                View imageMessageView = inflater.inflate(R.layout.card_image_message, parent, false);
                viewHolder = new ImageMessageViewHolder(imageMessageView, viewType);
                break;
            case IMAGE_ALBUM:
                View albumMessageView = inflater.inflate(R.layout.card_album_message, parent, false);
                viewHolder = new AlbumMessageViewHolder(albumMessageView, viewType);
                break;
            case DATE:

                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, h:mm a");
        switch (holder.getItemViewType()) {
            case TEXT_MESSAGE:
                final TextMessage textMessage = (TextMessage) itemList.get(position);
                TextMessageViewHolder textMessageViewHolder = (TextMessageViewHolder)(holder);
                textMessageViewHolder.setTimeLineType(TimeLineType.NORMAL);
                if (itemList.size() == 1)
                    textMessageViewHolder.setTimeLineType(TimeLineType.ONLYONE);
                else if (position == itemList.size() - 1)
                    textMessageViewHolder.setTimeLineType(TimeLineType.END);
                else if (position  == 0)
                    textMessageViewHolder.setTimeLineType(TimeLineType.START);
                else
                    textMessageViewHolder.setTimeLineType(TimeLineType.NORMAL);
//                textMessageViewHolder.getTime().setText(dateFormat.format(textMessage.getTime()));
                textMessageViewHolder.getBodyMessage().setText(textMessage.getText());
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        vibrator.vibrate(mVibratePattern, -1);
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Are you sure remove this message?")
                                .setTitle("Warning!");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                itemList.remove(position);
                                notifyItemRemoved(position);
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return true;
                    }
                });
                break;
            case VOICE_MESSAGE:
                final VoiceMessage voiceMessage = (VoiceMessage) itemList.get(position);
                VoiceMessageViewHolder voiceMessageViewHolder = (VoiceMessageViewHolder) (holder);
                if (itemList.size() == 1)
                    voiceMessageViewHolder .setTimeLineType(TimeLineType.ONLYONE);
                else if (position == itemList.size() - 1)
                    voiceMessageViewHolder.setTimeLineType(TimeLineType.END);
                else if (position  == 0)
                    voiceMessageViewHolder.setTimeLineType(TimeLineType.START);
                else
                    voiceMessageViewHolder.setTimeLineType(TimeLineType.NORMAL);
                voiceMessageViewHolder.getTime().setText(dateFormat.format(voiceMessage.getTime()));
                voiceMessageViewHolder.getBody().setText(voiceMessage.getBodyMessage());
                voiceMessageViewHolder.getDuration().setText(voiceMessage.getDurationFormative());
                voiceMessageViewHolder.getPlay().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MediaPlayer mediaPlayer = new MediaPlayer();
                        try {
                            mediaPlayer.setDataSource(voiceMessage.getPath());
                            mediaPlayer.prepare();
                            mediaPlayer.start();
                            Toast.makeText(mContext, "Playing Audio", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        vibrator.vibrate(mVibratePattern, -1);
                        Intent intent = new Intent(mContext, AlertRemoveDialog.class);
                        mContext.startActivity(intent);
                        return true;
                    }
                });
                break;
            case IMAGE:
                final ImageMessage imageMessage = (ImageMessage) itemList.get(position);
                ImageMessageViewHolder imageMessageViewHolder= (ImageMessageViewHolder) (holder);
                if (itemList.size() == 1)
                    imageMessageViewHolder .setTimeLineType(TimeLineType.ONLYONE);
                else if (position == itemList.size() - 1)
                    imageMessageViewHolder.setTimeLineType(TimeLineType.END);
                else if (position  == 0)
                    imageMessageViewHolder.setTimeLineType(TimeLineType.START);
                else
                    imageMessageViewHolder.setTimeLineType(TimeLineType.NORMAL);
                Glide.with(mContext).load(imageMessage.getUri()).into(imageMessageViewHolder.getImg());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                        intent.putExtra(AppConst.EXTRA_IMAGE_URI, imageMessage.getImageAddress().toString());
                        mContext.startActivity(intent);
                    }
                });
                break;
            case IMAGE_ALBUM:
                final AlbumMessage albumMessage = (AlbumMessage) itemList.get(position);
                AlbumMessageViewHolder albumMessageViewHolder= (AlbumMessageViewHolder) (holder);
                if (itemList.size() == 1)
                    albumMessageViewHolder .setTimeLineType(TimeLineType.ONLYONE);
                else if (position == itemList.size() - 1)
                    albumMessageViewHolder.setTimeLineType(TimeLineType.END);
                else if (position  == 0)
                    albumMessageViewHolder.setTimeLineType(TimeLineType.START);
                else
                    albumMessageViewHolder.setTimeLineType(TimeLineType.NORMAL);
                RecyclerView imagesRecyclerview = albumMessageViewHolder.getImagesRecyclerview();
                if (albumMessage.getImagesListUri().size() / 3 >= 1)
                    imagesRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 3));
                else if (albumMessage.getImagesListUri().size() % 2 == 0)
                    imagesRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
                AlbumAdapter adapter = new AlbumAdapter(mContext, albumMessage.getImagesListUri());
                imagesRecyclerview.setAdapter(adapter);
                break;
            case DATE:

                break;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (itemList.get(position) instanceof TextMessage) {
            return TEXT_MESSAGE;
        }
        if (itemList.get(position) instanceof VoiceMessage) {
            return VOICE_MESSAGE;
        }
        if (itemList.get(position) instanceof ImageMessage) {
            return IMAGE;
        }
        if (itemList.get(position) instanceof AlbumMessage) {
            return IMAGE_ALBUM;
        }
        return -1;
    }
}
