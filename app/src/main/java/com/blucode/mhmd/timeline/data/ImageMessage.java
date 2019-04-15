package com.blucode.mhmd.timeline.data;

import android.net.Uri;

import java.util.Date;

public class ImageMessage {
    private Uri imageAddress;
    private Date time;

    public ImageMessage(Uri imageAddress, Date time) {
        this.time = time;
        this.imageAddress = imageAddress;
    }

    public Uri getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(Uri imageAddress) {
        this.imageAddress = imageAddress;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
