package com.blucode.mhmd.timeline.data.model;

import java.util.List;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class AlbumMessage extends BasicMessage{

    @Id
    public long id;
    private List<UriAddress> imagesListUri;

    public AlbumMessage() {
        messageType = MessageType.ALBUM_MESSAGE;
    }

    public void setImagesListUri(List<UriAddress> imagesListUri) {
        this.imagesListUri = imagesListUri;
    }

    public List<UriAddress> getImagesListUri() {
        return imagesListUri;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
