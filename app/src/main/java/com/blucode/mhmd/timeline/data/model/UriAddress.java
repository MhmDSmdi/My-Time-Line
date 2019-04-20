package com.blucode.mhmd.timeline.data.model;

import android.net.Uri;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class UriAddress {

    @Id
    public long id;
    private String address;
    private ToOne<AlbumMessage> albumMessage;

    public UriAddress() {
    }

    public UriAddress(String address) {
        this.address = address;
    }

    public UriAddress(Uri address) {
        this.address = address.toString();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ToOne<AlbumMessage> getAlbumMessage() {
        return albumMessage;
    }

    public void setAlbumMessage(ToOne<AlbumMessage> albumMessage) {
        this.albumMessage = albumMessage;
    }
}
