package com.blucode.mhmd.timeline.data;

import java.net.URI;
import java.util.Date;

public class ImageMessage {
    private URI imageURI;
    private Date time;

    public ImageMessage(URI imageURI, Date time) {
        this.imageURI = imageURI;
        this.time = time;
    }

    public URI getImageURI() {
        return imageURI;
    }

    public void setImageURI(URI imageURI) {
        this.imageURI = imageURI;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
