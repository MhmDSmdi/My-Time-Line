package com.blucode.mhmd.timeline.data;

import java.util.Date;

public class TextMessage {
    private String text;
    private Date time;

    public TextMessage(String text, Date time) {
        this.text = text;
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
