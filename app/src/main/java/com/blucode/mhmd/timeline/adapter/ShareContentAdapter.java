package com.blucode.mhmd.timeline.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.data.model.AlbumMessage;
import com.blucode.mhmd.timeline.data.model.BasicMessage;
import com.blucode.mhmd.timeline.data.model.ImageMessage;
import com.blucode.mhmd.timeline.data.model.TextMessage;
import com.blucode.mhmd.timeline.data.model.VoiceMessage;
import com.blucode.mhmd.timeline.ui.EditDialogActivity;
import com.blucode.mhmd.timeline.ui.ImagePreviewActivity;
import com.blucode.mhmd.timeline.ui.view_holder.AlbumMessageViewHolder;
import com.blucode.mhmd.timeline.ui.view_holder.ImageMessageViewHolder;
import com.blucode.mhmd.timeline.ui.view_holder.TextMessageViewHolder;
import com.blucode.mhmd.timeline.ui.view_holder.TimeLineType;
import com.blucode.mhmd.timeline.ui.view_holder.VoiceMessageViewHolder;
import com.blucode.mhmd.timeline.util.AppConst;
import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.List;

public class ShareContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BasicMessage> itemList;
    private Context mContext;
    private final int TEXT_MESSAGE = 1, VOICE_MESSAGE = 3, DATE = 4, IMAGE = 0, IMAGE_ALBUM = 2;
    private long mVibratePattern[] = new long[]{0, 30};
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;

    public ShareContentAdapter(Context mContext, List<BasicMessage> itemList) {
        this.itemList = itemList;
        this.mContext = mContext;
        mediaPlayer = new MediaPlayer();
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
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, h:mm a");
        switch (holder.getItemViewType()) {
            case TEXT_MESSAGE:
                final TextMessage textMessage = (TextMessage) itemList.get(position);
                TextMessageViewHolder textMessageViewHolder = (TextMessageViewHolder)(holder);
                textMessageViewHolder.setTimeLineType(TimeLineType.getTimelineType(itemList.size(), position));
                textMessageViewHolder.getBodyMessage().setText(textMessage.getText());
                textMessageViewHolder.getDate().setText(dateFormat.format(textMessage.getDate()));
                textMessageViewHolder.getLayoutDetail().setVisibility(textMessage.isExpanded() ? View.VISIBLE : View.GONE);
                textMessageViewHolder.getRatingBar().setRating(textMessage.getRate());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textMessage.setExpanded(!textMessage.isExpanded());
                        notifyItemChanged(position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        vibrator.vibrate(mVibratePattern, -1);
                        Intent intent = new Intent(mContext, EditDialogActivity.class);
                        intent.putExtra(AppConst.EXTRA_MESSAGE_BODY, textMessage.getText());
                        intent.putExtra(AppConst.EXTRA_MESSAGE_DATE, dateFormat.format(textMessage.getDate()));
                        intent.putExtra(AppConst.EXTRA_MESSAGE_RATING, textMessage.getRate());
                        intent.putExtra(AppConst.EXTRA_MESSAGE_POSITION, position);
                        intent.putExtra(AppConst.EXTRA_MESSAGE_TYPE, TEXT_MESSAGE);
                        ((Activity)mContext).startActivityForResult(intent, AppConst.EDIT_REQUEST_CODE);
                        return true;
                    }
                });
                break;
            case VOICE_MESSAGE:
                final VoiceMessage voiceMessage = (VoiceMessage) itemList.get(position);
                final VoiceMessageViewHolder voiceMessageViewHolder = (VoiceMessageViewHolder) (holder);
                voiceMessageViewHolder.setTimeLineType(TimeLineType.getTimelineType(itemList.size(), position));
                voiceMessageViewHolder.getBody().setText(voiceMessage.getBodyMessage());
                voiceMessageViewHolder.getDuration().setText(voiceMessage.getDurationFormative());
                voiceMessageViewHolder.getRatingBar().setRating(voiceMessage.getRate());
                voiceMessageViewHolder.getPlay().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        voiceMessage.setPlay(!voiceMessage.isPlay());
                        voiceMessageViewHolder.getPlay().toggle();
                        if (voiceMessage.isPlay()) {
                            try {
                                mediaPlayer.setDataSource(voiceMessage.getPath());
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                            mediaPlayer = new MediaPlayer();
                        }
                    }
                });
                voiceMessageViewHolder.getLayoutDetail().setVisibility(voiceMessage.isExpanded() ? View.VISIBLE : View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        voiceMessage.setExpanded(!voiceMessage.isExpanded());
                        notifyItemChanged(position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        vibrator.vibrate(mVibratePattern, -1);
                        Intent intent = new Intent(mContext, EditDialogActivity.class);
                        intent.putExtra(AppConst.EXTRA_MESSAGE_BODY, voiceMessage.getBodyMessage());
                        intent.putExtra(AppConst.EXTRA_MESSAGE_DATE, dateFormat.format(voiceMessage.getDate()));
                        intent.putExtra(AppConst.EXTRA_MESSAGE_RATING, voiceMessage.getRate());
                        intent.putExtra(AppConst.EXTRA_MESSAGE_POSITION, position);
                        intent.putExtra(AppConst.EXTRA_MESSAGE_TYPE, VOICE_MESSAGE);
                        ((Activity)mContext).startActivityForResult(intent, AppConst.EDIT_REQUEST_CODE);
                        return true;
                    }
                });

                break;
            case IMAGE:
                final ImageMessage imageMessage = (ImageMessage) itemList.get(position);
                ImageMessageViewHolder imageMessageViewHolder= (ImageMessageViewHolder) (holder);
                imageMessageViewHolder.setTimeLineType(TimeLineType.getTimelineType(itemList.size(), position));
                Glide.with(mContext).load(imageMessage.getUri()).into(imageMessageViewHolder.getImg());
                imageMessageViewHolder.getRatingBar().setRating(imageMessage.getRate());
                imageMessageViewHolder.getImg().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                        intent.putExtra(AppConst.EXTRA_IMAGE_URI, imageMessage.getUri().toString());
                        mContext.startActivity(intent);
                    }
                });
                imageMessageViewHolder.getLayoutDetail().setVisibility(imageMessage.isExpanded() ? View.VISIBLE : View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageMessage.setExpanded(!imageMessage.isExpanded());
                        notifyItemChanged(position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        vibrator.vibrate(mVibratePattern, -1);
                        Intent intent = new Intent(mContext, EditDialogActivity.class);
                        intent.putExtra(AppConst.EXTRA_MESSAGE_DATE, dateFormat.format(imageMessage.getDate()));
                        intent.putExtra(AppConst.EXTRA_MESSAGE_RATING, imageMessage.getRate());
                        intent.putExtra(AppConst.EXTRA_MESSAGE_POSITION, position);
                        intent.putExtra(AppConst.EXTRA_MESSAGE_TYPE, IMAGE);
                        intent.putExtra(AppConst.EXTRA_MESSAGE_IMAGE_URI, imageMessage.getUri().toString());
                        ((Activity)mContext).startActivityForResult(intent, AppConst.EDIT_REQUEST_CODE);
                        return true;
                    }
                });
                break;
            case IMAGE_ALBUM:
                final AlbumMessage albumMessage = (AlbumMessage) itemList.get(position);
                AlbumMessageViewHolder albumMessageViewHolder= (AlbumMessageViewHolder) (holder);
                albumMessageViewHolder.setTimeLineType(TimeLineType.getTimelineType(itemList.size(), position));
                albumMessageViewHolder.getRatingBar().setRating(albumMessage.getRate());
                RecyclerView imagesRecyclerview = albumMessageViewHolder.getImagesRecyclerview();
                if (albumMessage.getImagesListUri().size() / 3 >= 1)
                    imagesRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 3));
                else if (albumMessage.getImagesListUri().size() % 2 == 0)
                    imagesRecyclerview.setLayoutManager(new GridLayoutManager(mContext, 2));
                AlbumAdapter adapter = new AlbumAdapter(mContext, albumMessage.getImagesListUri());
                imagesRecyclerview.setAdapter(adapter);
                albumMessageViewHolder.getLayoutDetail().setVisibility(albumMessage.isExpanded() ? View.VISIBLE : View.GONE);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        albumMessage.setExpanded(!albumMessage.isExpanded());
                        notifyItemChanged(position);
                    }
                });
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        vibrator.vibrate(mVibratePattern, -1);
                        Intent intent = new Intent(mContext, EditDialogActivity.class);
                        intent.putExtra(AppConst.EXTRA_MESSAGE_DATE, dateFormat.format(albumMessage.getDate()));
                        intent.putExtra(AppConst.EXTRA_MESSAGE_RATING, albumMessage.getRate());
                        intent.putExtra(AppConst.EXTRA_MESSAGE_POSITION, position);
                        intent.putExtra(AppConst.EXTRA_MESSAGE_TYPE, IMAGE_ALBUM);
                        ((Activity)mContext).startActivityForResult(intent, AppConst.EDIT_REQUEST_CODE);
                        return true;
                    }
                });
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
