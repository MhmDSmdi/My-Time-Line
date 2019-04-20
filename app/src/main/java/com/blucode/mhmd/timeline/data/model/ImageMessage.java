package com.blucode.mhmd.timeline.data.model;

import android.net.Uri;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class ImageMessage extends BasicMessage {

    @Id
    public long id;
    private ToOne<UriAddress> imageAddress;

    public ImageMessage() {
        messageType = MessageType.IMAGE_MESSAGE;
    }

    public ImageMessage(Uri imageAddress) {
        this.imageAddress.setTarget(new UriAddress(imageAddress));
        messageType = MessageType.IMAGE_MESSAGE;
    }

    public Uri getUri() {
        return Uri.parse(imageAddress.getTarget().getAddress());
    }

    public ToOne<UriAddress> getImageAddress() {
        return imageAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setImageAddress(ToOne<UriAddress> imageAddress) {
        this.imageAddress = imageAddress;
    }

    public void setImageAddress(Uri imageAddress) {
        this.imageAddress.setTarget(new UriAddress(imageAddress));
    }

}
