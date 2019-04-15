package com.blucode.mhmd.timeline.data;

import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlbumMessage {

    private List<Uri> imagesListAddress;
    private Date time;

    public AlbumMessage() {
        imagesListAddress = new ArrayList<>();
    }

    public List<Uri> getImagesListAddress() {
        return imagesListAddress;
    }

    public void setImagesListAddress(List<Uri> imagesListAddress) {
        this.imagesListAddress = imagesListAddress;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
