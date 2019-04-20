package com.blucode.mhmd.timeline.data.model;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToMany;

@Entity
public class AlbumMessage extends BasicMessage {

    @Id
    public long id;
    private ToMany<UriAddress> imagesListUri;

    public AlbumMessage() {
        messageType = MessageType.ALBUM_MESSAGE;
    }

    public ToMany<UriAddress> getImagesListUri() {
        return imagesListUri;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
