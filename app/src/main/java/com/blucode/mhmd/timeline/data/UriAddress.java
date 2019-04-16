package com.blucode.mhmd.timeline.data;

import android.net.Uri;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class UriAddress {

    @Id
    public long id;
    private String address;

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
}