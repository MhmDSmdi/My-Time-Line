package com.blucode.mhmd.timeline.ui;

import android.Manifest;
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
import com.blucode.mhmd.timeline.util.TimerRecording;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TimeLineActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 720;
    private ImageView btnSend, btnCamera, btnVoice, btnAttach;
    private EditText messageEditText;
    private MediaRecorder myAudioRecorder;
    private String voiceOutputFile;
    private LinearLayout panel;
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
        messageEditText = findViewById(R.id.edit_text_home_message);
        panel = findViewById(R.id.layout_share_content_items);
        timer = findViewById(R.id.txt_timer_share_content);
        recyclerView = findViewById(R.id.recycler_sharedContent);
        items = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShareContentAdapter(this, items);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);
        dataManager = new AppDataManager();
        fetchCacheMessages();
        getPermission();

        btnCamera = findViewById(R.id.img_home_camera);
        btnVoice = findViewById(R.id.img_home_voice);
        btnAttach = findViewById(R.id.img_home_attach);

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        btnAttach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), REQUEST_IMAGES_PICKER);
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
                items.add(0, message);
                messageEditText.setText("");
                adapter.notifyItemInserted(0);
                adapter.notifyItemChanged(1);
                recyclerView.smoothScrollToPosition(0);
                dataManager.insertTextMessage(message);



//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, messageEditText.getText().toString().trim());
//                sendIntent.setType("text/plain");
//                // Verify that the intent will resolve to an activity
//                if (sendIntent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(sendIntent);
//                }
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
                        items.add(0, voiceMessage);
                        recyclerView.smoothScrollToPosition(0);
                        timerRecording.cancelTimer();
                        adapter.notifyItemInserted(0);
                        adapter.notifyItemChanged(1);
                        dataManager.insertVoiceMessage(voiceMessage);
                    return true;
               }
               return false;
           }
       });
    }


    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            Log.d("TEEST", "dx= " + String.valueOf(dx));
            Log.d("TEEST", "dy= " + String.valueOf(dy));
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    btnVoice.setEnabled(false);
                }
                return;
            }

        }
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE:
                    ImageMessage imageMessage = new ImageMessage(Uri.fromFile(new File(currentPhotoPath)));
                    items.add(0, imageMessage);
                    recyclerView.smoothScrollToPosition(0);
                    adapter.notifyItemInserted(0);
                    adapter.notifyItemChanged(1);
                    dataManager.insertImageMessage(imageMessage);
                    break;

                case REQUEST_IMAGES_PICKER:
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    List<UriAddress> mArrayUri = new ArrayList<>();
                    if(data.getData()!=null){
                        ImageMessage singleImageMessage = new ImageMessage(data.getData());
                        items.add(0, singleImageMessage);
                        dataManager.insertImageMessage(singleImageMessage);
                        adapter.notifyItemInserted(0);
                        adapter.notifyItemChanged(1);
                        recyclerView.smoothScrollToPosition(0);

                    } else if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();
                        for (int i = 0; i < mClipData.getItemCount(); i++) {
                            ClipData.Item item = mClipData.getItemAt(i);
                            Uri uri = item.getUri();
                            mArrayUri.add(new UriAddress(uri));
                        }
                        AlbumMessage albumMessage = new AlbumMessage();
                        albumMessage.setImagesListUri(mArrayUri);
                        items.add(0, albumMessage);
                        adapter.notifyItemInserted(0);
                        adapter.notifyItemChanged(1);
                        recyclerView.smoothScrollToPosition(0);
                        dataManager.insertAlbumMessage(albumMessage);
                    }

                    break;
            }
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
}
