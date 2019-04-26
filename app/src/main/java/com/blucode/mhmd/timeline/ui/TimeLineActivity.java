package com.blucode.mhmd.timeline.ui;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blucode.mhmd.timeline.App;
import com.blucode.mhmd.timeline.ObjectBox;
import com.blucode.mhmd.timeline.R;
import com.blucode.mhmd.timeline.adapter.ShareContentAdapter;
import com.blucode.mhmd.timeline.data.AppDataManager;
import com.blucode.mhmd.timeline.data.model.AlbumMessage;
import com.blucode.mhmd.timeline.data.model.BasicMessage;
import com.blucode.mhmd.timeline.data.model.ImageMessage;
import com.blucode.mhmd.timeline.data.model.MyObjectBox;
import com.blucode.mhmd.timeline.data.model.TextMessage;
import com.blucode.mhmd.timeline.data.model.UriAddress;
import com.blucode.mhmd.timeline.data.model.VoiceMessage;
import com.blucode.mhmd.timeline.util.AppConst;
import com.blucode.mhmd.timeline.util.TimerRecording;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.blucode.mhmd.timeline.util.AppConst.EDIT_REQUEST_CODE;

public class TimeLineActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 720;
    private ImageView btnSend, btnCamera, btnVoice, btnAttach;
    private EditText messageEditText;
    private MediaRecorder myAudioRecorder;
    private String voiceOutputFile;
    private LinearLayout panel, timelineEmpty;
    private String currentPhotoPath;
    private Animation mScaleAnimation;
    private TimerRecording timerRecording;
    private TextView timer;

    private RecyclerView recyclerView;
    private ShareContentAdapter adapter;
    private List<BasicMessage> items;
    private AppDataManager dataManager;
    private long[] mVibratePattern = new long[]{0,30};
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGES_PICKER = 2;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_content);
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();
        messageEditText = findViewById(R.id.edit_text_home_message);
        timelineEmpty = findViewById(R.id.note_empty);
        panel = findViewById(R.id.layout_share_content_items);
        timer = findViewById(R.id.txt_timer_share_content);
        recyclerView = findViewById(R.id.recycler_sharedContent);
        items = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShareContentAdapter(this, items);
        recyclerView.setAdapter(adapter);
        dataManager = new AppDataManager();
        fetchCacheMessages();
        if (items.size() == 0)
            timelineEmpty.setVisibility(View.VISIBLE);
        getPermission();
        btnCamera = findViewById(R.id.img_home_camera);
        btnVoice = findViewById(R.id.img_home_voice);
        btnAttach = findViewById(R.id.img_home_attach);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               takePictureCamera();
            }
        });
        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               attachPictures();
            }
        });
        messageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("")) {
                    panel.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.INVISIBLE);
                }
                else {
                    panel.setVisibility(View.INVISIBLE);
                    btnSend.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSend = findViewById(R.id.img_home_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextMessage message = new TextMessage(messageEditText.getText().toString());
                addTextMessage(message, 0);
            }
        });
        timerRecording = new TimerRecording(new Handler() {
            @Override
            public void handleMessage(Message msg) {
                timer.setText(timerRecording.getTickString());
            }
        });
        btnVoice.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               switch (event.getAction()) {
                   case MotionEvent.ACTION_DOWN:
                        messageEditText.setVisibility(View.INVISIBLE);
                        timer.setVisibility(View.VISIBLE);
                        startRecording();
                        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        vibrator.vibrate(mVibratePattern, -1);
                        timerRecording.startTimer();
                    return true;

                   case MotionEvent.ACTION_UP:
                        messageEditText.setVisibility(View.VISIBLE);
                        timer.setVisibility(View.INVISIBLE);
                        stopRecording();
                        VoiceMessage voiceMessage = new VoiceMessage(messageEditText.getText().toString(), voiceOutputFile);
                        voiceMessage.setDuration(timerRecording.getTick());
                        voiceMessage.setBodyMessage(voiceOutputFile);
                        addVoiceMessage(voiceMessage, 0);
                    return true;
               }
               return false;
           }
       });
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent);
            } else if (type.startsWith("image/")) {
                handleSendImage(intent);
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent);
            }
        }
    }

    private void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            List<UriAddress> mArrayUri = new ArrayList<>();
            for (Uri uri : imageUris) {
                mArrayUri.add(new UriAddress(uri));
            }
            AlbumMessage albumMessage = new AlbumMessage();
            albumMessage.getImagesListUri().addAll(mArrayUri);
            addAlbumMessage(albumMessage, 0);
        }
    }

    private void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            ImageMessage imageMessage = new ImageMessage();
            imageMessage.setImageAddress(imageUri);
            addImageMessage(imageMessage, 0);
        }
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            TextMessage message = new TextMessage();
            message.setText(sharedText);
            addTextMessage(message, 0);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED)
                    btnVoice.setEnabled(false);
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    ImageMessage imageMessage = new ImageMessage(Uri.fromFile(new File(currentPhotoPath)));
                    addImageMessage(imageMessage, 0);
                    break;

                case REQUEST_IMAGES_PICKER:
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    List<UriAddress> mArrayUri = new ArrayList<>();
                    if(data.getData()!=null){
                        ImageMessage singleImageMessage = new ImageMessage(data.getData());
                        addImageMessage(singleImageMessage, 0);

                    } else if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(new UriAddress(uri));
                        }
                        AlbumMessage albumMessage = new AlbumMessage();
                        albumMessage.getImagesListUri().addAll(mArrayUri);
                        addAlbumMessage(albumMessage, 0);
                    }

                    break;
                case EDIT_REQUEST_CODE:
                    int pos = data.getIntExtra(AppConst.EXTRA_MESSAGE_POSITION, 0);
                    int type = data.getIntExtra(AppConst.EXTRA_MESSAGE_TYPE, 0);
                    String newBody = data.getStringExtra(AppConst.EXTRA_MESSAGE_BODY);
                    float newRate = data.getFloatExtra(AppConst.EXTRA_MESSAGE_RATING, 0);
                    BasicMessage message = items.get(pos);
                    message.setRate(newRate);
                    Toast.makeText(this, pos + "", Toast.LENGTH_LONG).show();
                    adapter.notifyItemChanged(pos);
                    switch (type) {
                        case 1:
                            TextMessage textMessage = (TextMessage) message;
                            textMessage.setText(newBody);
                            dataManager.insertTextMessage(textMessage);
                            break;
                        case 3:
                            VoiceMessage voiceMessage = (VoiceMessage) message;
                            voiceMessage.setBodyMessage(newBody);
                            dataManager.insertVoiceMessage(voiceMessage);
                            break;
                    }
                    break;
            }
        }
    }

    private void addAlbumMessage(AlbumMessage albumMessage, int position) {
        items.add(position, albumMessage);
        adapter.notifyItemInserted(position);
        adapter.notifyItemChanged(position + 1);
        recyclerView.smoothScrollToPosition(position);
        dataManager.insertAlbumMessage(albumMessage);
        emptyLayoutGone();
    }

    private void addImageMessage(ImageMessage imageMessage, int position) {
        items.add(position, imageMessage);
        recyclerView.smoothScrollToPosition(position);
        adapter.notifyItemInserted(position);
        adapter.notifyItemChanged(position + 1);
        dataManager.insertImageMessage(imageMessage);
        emptyLayoutGone();
    }

    private void addVoiceMessage(VoiceMessage voiceMessage, int position) {
        items.add(position, voiceMessage);
        recyclerView.smoothScrollToPosition(position);
        timerRecording.cancelTimer();
        adapter.notifyItemInserted(position);
        adapter.notifyItemChanged(position + 1);
        dataManager.insertVoiceMessage(voiceMessage);
        emptyLayoutGone();
    }

    private void addTextMessage(TextMessage message, int position) {
        items.add(position, message);
        messageEditText.setText("");
        adapter.notifyItemInserted(position);
        adapter.notifyItemChanged(position + 1);
        recyclerView.smoothScrollToPosition(position);
        dataManager.insertTextMessage(message);
        emptyLayoutGone();
    }

    private void takePictureCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ignored) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(TimeLineActivity.this, "com.mhmd.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            }
        }
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    private void attachPictures() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), REQUEST_IMAGES_PICKER);
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
        }
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,".jpg", storageDir);
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void startRecording() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        voiceOutputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Voice_" + timeStamp + ".3gp";
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(voiceOutputFile);
        try {
            myAudioRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAudioRecorder.start();
        Toast.makeText(TimeLineActivity.this, "Recording started", Toast.LENGTH_LONG).show();
    }

    private void stopRecording() {
        myAudioRecorder.stop();
        myAudioRecorder.release();
        myAudioRecorder = null;
        Toast.makeText(getApplicationContext(), "Audio Recorder successfully", Toast.LENGTH_LONG).show();
    }

    private void playingVoice() {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(voiceOutputFile);
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(getApplicationContext(), "Playing Audio", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchCacheMessages() {
        items.addAll(dataManager.getAllAlbumMessage());
        items.addAll(dataManager.getAllImageMessage());
        items.addAll(dataManager.getAllTextMessage());
        items.addAll(dataManager.getAllVoiceMessage());
        Collections.sort(items, new BasicMessage());
        adapter.notifyDataSetChanged();
    }

    private void emptyLayoutGone() {
        if (items.size() > 0)
            timelineEmpty.animate()
                    .scaleX(0)
                    .scaleY(0)
                    .alpha(0.0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            timelineEmpty.setVisibility(View.GONE);
                        }
                    });
    }

    private void emptyLayoutVisible() {
        if (items.size() == 0)
            timelineEmpty.animate()
                    .scaleX(timelineEmpty.getScaleX())
                    .scaleY(timelineEmpty.getScaleY())
                    .alpha(1.0f)
                    .setDuration(500)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            timelineEmpty.setVisibility(View.VISIBLE);
                        }
                    });
    }

    private void shareTextMessage() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, messageEditText.getText().toString().trim());
        sendIntent.setType("text/plain");
        if (sendIntent.resolveActivity(getPackageManager()) != null)
            startActivity(sendIntent);
    }
}
