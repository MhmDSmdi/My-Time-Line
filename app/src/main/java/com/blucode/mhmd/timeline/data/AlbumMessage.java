package com.blucode.mhmd.timeline.data;

import android.net.Uri;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class AlbumMessage {

    @Id
    public long id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
