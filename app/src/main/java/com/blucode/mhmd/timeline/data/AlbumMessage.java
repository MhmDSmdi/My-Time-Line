package com.blucode.mhmd.timeline.data;

import java.util.Date;
import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class AlbumMessage {

    @Id
    public long id;

    private List<UriAddress> imagesListUri;
    private Date time;

    public AlbumMessage() {
    }

    public void setImagesListUri(List<UriAddress> imagesListUri) {
        this.imagesListUri = imagesListUri;
    }

    public List<UriAddress> getImagesListUri() {
        return imagesListUri;
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
